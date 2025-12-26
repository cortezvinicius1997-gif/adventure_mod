package com.cortez.adventuremod.block.entity.custom;

import com.cortez.adventuremod.block.entity.ImplementedInventory;
import com.cortez.adventuremod.block.entity.ModBlockEntities;
import com.cortez.adventuremod.items.ModItems;
import com.cortez.adventuremod.screen.custom.ProgrammingScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class ProgrammingBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public ProgrammingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PROGRAMMING_BE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.adventuremod.programming_block");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ProgrammingScreenHandler(syncId, playerInventory, this);
    }

    // Sobrescrever canInsert para aceitar apenas MINICOMPUTER e verificar se já tem item
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        // Apenas aceita MINICOMPUTER no slot 0
        if (slot != 0 || stack.getItem() != ModItems.MINICOMPUTER) {
            return false;
        }

        // Verifica se o slot já está ocupado ou se já tem 1 item
        ItemStack currentStack = inventory.get(slot);
        if (!currentStack.isEmpty()) {
            // Se já tem um item diferente, não aceita
            if (!ItemStack.areItemsEqual(currentStack, stack)) {
                return false;
            }
            // Se já tem 1 item (máximo), não aceita mais
            if (currentStack.getCount() >= 1) {
                return false;
            }
        }

        return true;
    }

    // Sobrescrever canExtract para permitir remoção
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    // Sobrescrever getAvailableSlots
    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0};
    }

    // Salvar e carregar o inventário
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null) {
            world.markDirty(pos);
        }
    }

    // Sobrescrever setStack para garantir que nunca tenha mais de 1 item
    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0 && !stack.isEmpty()) {
            // Limita a quantidade máxima a 1
            if (stack.getCount() > 1) {
                stack.setCount(1);
            }
        }
        inventory.set(slot, stack);
        markDirty();
    }
}