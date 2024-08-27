package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.level.block.entity.ItemHolder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TeapotBlockEntity extends BlockEntity implements ItemHolder, Nameable {
    private static final int MAX_CUPS = 6;

    private ItemStack item = ItemStack.EMPTY;

    private int brewTime;

    private int cups = 0;

    protected float temp;
    protected boolean boiling;
    protected boolean done;
    protected boolean brewing;
    protected boolean heating;
    protected Holder<Potion> potion = null;

    private float experiencePerCup = 0;
    private int experienceCups = MAX_CUPS;

    private final RecipeManager.CachedCheck<TeapotBrewingRecipe.TeapotBrewingRecipeInput, TeapotBrewingRecipe> quickCheck = RecipeManager.createCheck(MinejagoRecipeTypes.TEAPOT_BREWING.get());

    public TeapotBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MinejagoBlockEntityTypes.TEAPOT.get(), pPos, pBlockState);
    }

    public int getContainerSize() {
        return 1;
    }

    public boolean isEmpty() {
        return item.isEmpty();
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, TeapotBlockEntity pBlockEntity) {
        if (pBlockEntity.cups < 3)
            pLevel.setBlock(pPos, pState.setValue(TeapotBlock.FILLED, false), Block.UPDATE_ALL);
        else
            pLevel.setBlock(pPos, pState.setValue(TeapotBlock.FILLED, true), Block.UPDATE_ALL);

        if (pBlockEntity.cups > 0) {
            Optional<RecipeHolder<TeapotBrewingRecipe>> recipe = pBlockEntity.quickCheck.getRecipeFor(new TeapotBrewingRecipe.TeapotBrewingRecipeInput(pBlockEntity.potion, pBlockEntity.item), pLevel);

            pBlockEntity.cups = Math.min(pBlockEntity.cups, MAX_CUPS);

            if (pBlockEntity.brewing) {
                pBlockEntity.brewTime--;
                if (pBlockEntity.brewTime <= 0) {
                    pBlockEntity.brewing = false;
                    pBlockEntity.done = true;
                    pLevel.playSound(null, pPos, MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (recipe.isPresent()) {
                        pBlockEntity.potion = recipe.get().value().getResultItem(pLevel.registryAccess()).get(DataComponents.POTION_CONTENTS).potion().orElse(null);
                        pBlockEntity.experiencePerCup = recipe.get().value().experience() / pBlockEntity.cups;
                        pBlockEntity.experienceCups = pBlockEntity.cups;
                    }
                    pBlockEntity.item = ItemStack.EMPTY;
                    setChanged(pLevel, pPos, pState);
                } else if (pBlockEntity.item.isEmpty() && !pBlockEntity.done) {
                    pBlockEntity.brewTime = 0;
                    pBlockEntity.brewing = false;
                    pBlockEntity.boiling = true;
                    pLevel.playSound(null, pPos, MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                } else if (pBlockEntity.temp < 100) {
                    pBlockEntity.brewing = false;
                    pBlockEntity.boiling = false;
                }
                setChanged(pLevel, pPos, pState);
            } else if (pBlockEntity.heating) {
                pBlockEntity.temp += 0.1f;
                setChanged(pLevel, pPos, pState);
                if (pBlockEntity.temp >= 100.0) {
                    pBlockEntity.heating = false;
                    pBlockEntity.boiling = true;
                    pLevel.playSound(null, pPos, MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    setChanged(pLevel, pPos, pState);
                }
            } else if (pBlockEntity.temp >= 100 && recipe.isPresent()) {
                pBlockEntity.brewTime = (recipe.map(holder -> holder.value().brewingTime()).orElseGet(() -> UniformInt.of(1200, 2400))).sample(pLevel.random);
                pBlockEntity.brewing = true;
                pBlockEntity.boiling = false;
                pBlockEntity.done = false;
            }
            BlockState below = pLevel.getBlockState(pPos.below());
            if (pLevel.dimension() == Level.NETHER) {
                if (pBlockEntity.temp < 100 && !pBlockEntity.isBoiling()) {
                    pBlockEntity.temp = 100;
                    pBlockEntity.heating = true;
                    setChanged(pLevel, pPos, pState);
                }
            } else if ((below.is(BlockTags.CAMPFIRES) || below.is(BlockTags.FIRE))) {
                if (!(pBlockEntity.brewing || pBlockEntity.boiling || pBlockEntity.done)) {
                    pBlockEntity.heating = true;
                    setChanged(pLevel, pPos, pState);
                }
            } else if (pBlockEntity.temp > TeapotBlock.getBiomeTemperature(pLevel, pPos)) {
                if (pLevel.getBiome(pPos).value().getBaseTemperature() < 2.0)
                    pBlockEntity.temp--;
                pBlockEntity.temp--;
                setChanged(pLevel, pPos, pState);
            }
            if (pBlockEntity.temp < 100 && pBlockEntity.isBoiling()) {
                pBlockEntity.boiling = false;
                setChanged(pLevel, pPos, pState);
            }
        } else {
            pBlockEntity.potion = null;
            pBlockEntity.boiling = false;
            pBlockEntity.done = false;
            pBlockEntity.item = ItemStack.EMPTY;
            pBlockEntity.heating = false;
            pBlockEntity.brewing = false;
            pBlockEntity.brewTime = 0;
            pBlockEntity.temp = (float) TeapotBlock.getBiomeTemperature(pLevel, pPos) / 2;
            setChanged(pLevel, pPos, pState);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        NonNullList<ItemStack> itemList = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, itemList, provider);
        item = itemList.getFirst();
        Holder<Potion> newPotion = BuiltInRegistries.POTION.getHolder(ResourceLocation.parse(compoundTag.getString("Potion"))).orElse(null);
        if (newPotion != potion) {
            potion = newPotion;
            if (this.level != null && this.level.isClientSide)
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
        this.temp = compoundTag.getShort("Temperature");
        this.boiling = compoundTag.getBoolean("Boiling");
        this.done = compoundTag.getBoolean("Done");
        this.cups = compoundTag.getShort("Cups");
        this.brewTime = compoundTag.getShort("BrewTime");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, NonNullList.withSize(1, item), provider);
        if (this.potion != null) {
            compoundTag.putString("Potion", potion.getRegisteredName());
        }
        compoundTag.putFloat("Temperature", temp);
        compoundTag.putBoolean("Boiling", boiling);
        compoundTag.putBoolean("Done", done);
        compoundTag.putInt("Cups", cups);
        compoundTag.putInt("BrewTime", brewTime);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag updateTag = super.getUpdateTag(provider);
        saveAdditional(updateTag, provider);
        return updateTag;
    }

    public void take(int count) {
        this.cups -= count;
        setChanged(level, getBlockPos(), getBlockState());
    }

    public void setTemperature(int temp) {
        this.temp = temp;
        setChanged(level, getBlockPos(), getBlockState());
    }

    public float getTemperature() {
        return temp;
    }

    public int getBrewTime() {
        return brewTime;
    }

    public boolean isBoiling() {
        return boiling;
    }

    public boolean isDone() {
        return done || boiling;
    }

    public Holder<Potion> getPotion() {
        return potion;
    }

    public int getCups() {
        return cups;
    }

    public boolean tryFill(int cups, Holder<Potion> potion) {
        if (cups > MAX_CUPS - getCups() || potion == null) {
            return false;
        } else if (potion == this.potion) {
            this.cups += cups;
            setChanged(level, getBlockPos(), getBlockState());
            return true;
        } else if (this.potion == null) {
            this.cups = cups;
            this.potion = potion;
            setChanged(level, getBlockPos(), getBlockState());
            return true;
        }
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleTag(CompoundTag tag) {
        TommyLibServices.BLOCK_ENTITY.handleUpdateTag(this, tag, level.registryAccess());
        loadAdditional(tag, level.registryAccess());
    }

    protected static void setChanged(Level pLevel, BlockPos pPos, BlockState pState) {
        BlockEntity.setChanged(pLevel, pPos, pState);
        pLevel.sendBlockUpdated(pPos, pLevel.getBlockState(pPos), pState, Block.UPDATE_ALL);
    }

    public void setPotion(Holder<Potion> potion) {
        this.potion = potion;
    }

    @Override
    public Component getName() {
        return this.hasCustomName() ? this.getCustomName() : Component.translatable("container.teapot");
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public ItemStack getInSlot(int slot) {
        return slot == 0 ? item : ItemStack.EMPTY;
    }

    @Override
    public ItemStack insert(int slot, ItemStack stack) {
        if (slot == 0) {
            ItemStack newStack = stack.copy();
            newStack.setCount(1);
            item = newStack;
            return item;
        } else {
            Minejago.LOGGER.error("Teapot index out of bounds!");
            return stack;
        }
    }

    @Override
    public ItemStack extract(int slot, int amount) {
        return slot == 0 && !item.isEmpty() && amount > 0 ? item.split(amount) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotMax(int slot) {
        return 1;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return false;
    }

    public boolean hasRecipe(ItemStack item, Level level) {
        return quickCheck.getRecipeFor(new TeapotBrewingRecipe.TeapotBrewingRecipeInput(potion, item), level).isPresent();
    }

    public void giveExperienceForCup(ServerLevel serverLevel, Vec3 pos) {
        if (experienceCups > 0) {
            ExperienceOrb.award(serverLevel, pos, (int) experiencePerCup);
            experienceCups--;
        }
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        ItemContainerContents contents = componentInput.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (contents.getSlots() > 0) {
            item = contents.getStackInSlot(0);
            potion = contents.getStackInSlot(1).get(DataComponents.POTION_CONTENTS).potion().orElseThrow();
            cups = contents.getStackInSlot(1).getCount();
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        if (potion != null) {
            components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(List.of(item, MinejagoItemUtils.fillTeacup(potion).copyWithCount(cups))));
        }
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("Items");
    }
}
