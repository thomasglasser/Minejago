package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.util.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TeapotBlockEntity extends BlockEntity implements IItemHandler, Nameable
{
    public static final int[] SLOTS = new int[] {0};
    public static final int INPUT_SLOT = SLOTS[0];

    private ItemStack item = ItemStack.EMPTY;

    private short brewTime;

    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int index) {
            return TeapotBlockEntity.this.brewTime;
        }

        public void set(int index, int newVal) {
            TeapotBlockEntity.this.brewTime = (short) newVal;
            setChanged();

        }

        public int getCount() {
            return 1;
        }
    };

    private int cups = 0;

    public float temp;
    private boolean boiling;
    private boolean done;
    private boolean brewing;
    private boolean heating;
    private Potion potion;

    public TeapotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MinejagoBlockEntityTypes.TEAPOT.get(), pPos, pBlockState);
    }

    public int getContainerSize() {
        return 1;
    }

    public boolean isEmpty() {
        return item.isEmpty();
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, TeapotBlockEntity pBlockEntity)
    {
        if (!pLevel.getBlockState(pPos.above()).isFaceSturdy(pLevel, pPos.above(), Direction.DOWN, SupportType.CENTER) && (pLevel.getBlockState(pPos.below()).is(BlockTags.FIRE) || pLevel.getBlockState(pPos.below()).is(BlockTags.CAMPFIRES)))
        {
            pLevel.destroyBlock(pPos, true);
        }

        if (pBlockEntity.cups > 0)
        {
            pBlockEntity.cups = Math.min(pBlockEntity.cups, 6);

            if (pBlockEntity.brewing)
            {
                pBlockEntity.brewTime--;
                if (pBlockEntity.brewTime == 0)
                {
                    pBlockEntity.brewing = false;
                    pBlockEntity.done = true;
                    pLevel.playSound((Player) null, pPos, MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);

                    ItemStack potionStack = PotionUtils.setPotion(new ItemStack(Items.POTION), pBlockEntity.potion);
                    if (MinejagoPotionBrewing.hasTeaMix(PotionUtils.setPotion(new ItemStack(Items.POTION), pBlockEntity.potion), pBlockEntity.item))
                    {
                        pBlockEntity.potion = PotionUtils.getPotion(MinejagoPotionBrewing.mix(pBlockEntity.item, potionStack));
                    }
                    else if (PotionBrewing.hasPotionMix(PotionUtils.setPotion(new ItemStack(Items.POTION), pBlockEntity.potion), pBlockEntity.item))
                        pBlockEntity.potion = PotionUtils.getPotion(PotionBrewing.mix(pBlockEntity.item, potionStack));
                    pBlockEntity.item.shrink(1);
                } else if (pBlockEntity.item.isEmpty()) {
                    pBlockEntity.brewTime = 0;
                    pBlockEntity.brewing = false;
                    pBlockEntity.boiling = true;
                    pLevel.playSound((Player) null, pPos, MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (pBlockEntity.temp < 100) {
                    pBlockEntity.brewing = false;
                }
                setChanged(pLevel, pPos, pState);
            } else if (pBlockEntity.heating) {
                pBlockEntity.temp += 0.1;
                setChanged(pLevel, pPos, pState);
                if (pBlockEntity.temp >= 100) {
                    pBlockEntity.heating = false;
                    pBlockEntity.boiling = true;
                    pLevel.playSound((Player) null, pPos, MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    setChanged(pLevel, pPos, pState);
                }
            } else if (pBlockEntity.temp >= 100 && !pBlockEntity.item.isEmpty() && (PotionBrewing.hasPotionMix(PotionUtils.setPotion(new ItemStack(Items.POTION), pBlockEntity.potion), pBlockEntity.item) || MinejagoPotionBrewing.hasTeaMix(PotionUtils.setPotion(new ItemStack(Items.POTION), pBlockEntity.potion), pBlockEntity.item))) {
                pBlockEntity.brewTime = (short) RandomSource.create().nextIntBetweenInclusive(1200, 2400);
                pBlockEntity.brewing = true;
                pBlockEntity.boiling = false;
                pBlockEntity.done = false;
            }
            BlockState below = pLevel.getBlockState(pPos.below());
            if (pLevel.dimension() == Level.NETHER)
            {
                if (pBlockEntity.temp < 100)
                {
                    pBlockEntity.temp = 100;
                    pBlockEntity.heating = true;
                    setChanged(pLevel, pPos, pState);
                }
            }
            else if ((below.is(BlockTags.CAMPFIRES) || below.is(BlockTags.FIRE)))
            {
                if (!(pBlockEntity.brewing || pBlockEntity.boiling || pBlockEntity.done))
                {
                    pBlockEntity.heating = true;
                    setChanged(pLevel, pPos, pState);
                }
            }
            else if (pBlockEntity.temp > TeapotBlock.getBiomeTemperature(pLevel, pPos))
            {
                if (pLevel.getBiome(pPos).get().getBaseTemperature() < 2.0)
                    pBlockEntity.temp--;
                pBlockEntity.temp--;
                setChanged(pLevel, pPos, pState);
            }
        }
        else
        {
            pBlockEntity.potion = null;
            pBlockEntity.boiling = false;
            pBlockEntity.done = false;
            pBlockEntity.item = ItemStack.EMPTY;
            pBlockEntity.heating = false;
            pBlockEntity.brewing = false;
            pBlockEntity.brewTime = 0;
            pBlockEntity.temp = TeapotBlock.getBiomeTemperature(pLevel, pPos);
            setChanged(pLevel, pPos, pState);
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        NonNullList<ItemStack> itemList = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, itemList);
        item = itemList.get(0);
        this.potion = PotionUtils.getPotion(pTag);
        this.temp = pTag.getShort("Temperature");
        this.cups = pTag.getShort("Cups");
        if (this.level.isClientSide)
        {
            this.boiling = pTag.getBoolean("Boiling");
            this.done = pTag.getBoolean("Done");
        }
    }

    public void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, NonNullList.withSize(1, item));
        pTag.putString("Potion", ForgeRegistries.POTIONS.getKey(potion).toString());
        pTag.putBoolean("Boiling", boiling);
        pTag.putBoolean("Done", done);
        pTag.putShort("Temperature", (short) temp);
        pTag.putShort("Cups", (short) cups);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int pIndex, ItemStack pStack) {
        if (pIndex == 0)
        {
            ItemStack newStack = pStack.copy();
            newStack.setCount(1);
            item = newStack;
        }
        else
        {
            Minejago.LOGGER.error("Teapot index out of bounds!");
        }
    }

    /**
     * Returns {@code true} if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     * For guis use Slot.isItemValid
     */
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return BrewingRecipeRegistry.isValidIngredient(pStack);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        this.saveAdditional(compoundtag);
        return compoundtag;
    }

    public void take(int count)
    {
        this.cups -= count;
        setChanged();
    }

    public void setTemperature(int temp) {
        this.temp = temp;
        setChanged();
    }

    public boolean isBoiling() {
        return boiling;
    }

    public boolean isDone() {
        return done;
    }

    public Potion getPotion() {
        return potion;
    }

    public int getCups() {
        return cups;
    }

    public boolean tryFill(int cups, Potion potion)
    {
        if (this.cups >= 6 || potion == null)
        {
            return false;
        } else if (potion == this.potion)
        {
            this.cups += cups;
            setChanged();
            return true;
        } else if (this.potion == null) {
            this.cups = cups;
            this.potion = potion;
            setChanged();
            return true;
        }
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    protected static void setChanged(Level pLevel, BlockPos pPos, BlockState pState) {
        pLevel.blockEntityChanged(pPos);
        if (!pState.isAir()) {
            pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
        }
        pLevel.sendBlockUpdated(pPos, pLevel.getBlockState(pPos), pState, 3);
    }


    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    @Override
    public Component getName() {
        return this.hasCustomName() ? this.getCustomName() : Component.translatable("container.teapot");
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return slot == 0 ? item : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (slot == 0)
        {
            (item = stack.copy()).setCount(1);
            return stack.copy().split(1);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return slot == 0 && !item.isEmpty() && amount > 0 ? item.split(amount) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }
}
