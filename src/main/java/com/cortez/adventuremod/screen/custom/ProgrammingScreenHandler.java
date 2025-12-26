package com.cortez.adventuremod.screen.custom;

import com.cortez.adventuremod.items.ModItems;
import com.cortez.adventuremod.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class ProgrammingScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    // Construtor para o cliente (quando abre a GUI via rede)
    public ProgrammingScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos));
    }

    // Construtor principal
    public ProgrammingScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity)  {
        super(ModScreenHandlers.PROGRAMMING_SCREEN_HANDLER, syncId);

        checkSize((Inventory) blockEntity, 1);
        this.inventory = ((Inventory) blockEntity);
        this.inventory.onOpen(playerInventory.player);

        // Slot para o minicomputer (slot 0 do inventory do BLOCO, não do player!)
        this.addSlot(new Slot(inventory, 0, 116, 115){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == ModItems.MINICOMPUTER;
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });

        // Hotbar do jogador
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            // Se for do slot do bloco (slot 0)
            if (invSlot == 0) {
                // Tenta mover para o inventário do jogador
                if (!this.insertItem(originalStack, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // Se for do inventário do jogador
            else {
                // Tenta mover para o slot do bloco (apenas se for MINICOMPUTER)
                if (originalStack.getItem() == ModItems.MINICOMPUTER) {
                    if (!this.insertItem(originalStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }
}