package com.farcr.swampexpansion.common.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlabfishInventory extends Container {
   private final IInventory slabfishInventory;
   private final SlabfishEntity slabfish;

   public SlabfishInventory(int p_i50077_1_, PlayerInventory playerInv, IInventory backpack, final SlabfishEntity slabfish) {
      super((ContainerType<?>)null, p_i50077_1_);
      this.slabfishInventory = backpack;
      this.slabfish = slabfish;
      backpack.openInventory(playerInv.player);
      if (slabfish instanceof SlabfishEntity && ((SlabfishEntity)slabfish).getBackpack()) {
         for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < ((SlabfishEntity)slabfish).getInventoryColumns(); ++l) {
               this.addSlot(new Slot(backpack, 2 + l + k * ((SlabfishEntity)slabfish).getInventoryColumns(), 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.addSlot(new Slot(playerInv, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.addSlot(new Slot(playerInv, j1, 8 + j1 * 18, 142));
      }

   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean canInteractWith(PlayerEntity playerIn) {
      return this.slabfishInventory.isUsableByPlayer(playerIn) && this.slabfish.isAlive() && this.slabfish.getDistance(playerIn) < 8.0F;
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         int i = this.slabfishInventory.getSizeInventory();
         if (index < i) {
            if (!this.mergeItemStack(itemstack1, i, this.inventorySlots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
            if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (this.getSlot(0).isItemValid(itemstack1)) {
            if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (i <= 2 || !this.mergeItemStack(itemstack1, 2, i, false)) {
            int j = i + 27;
            int k = j + 9;
            if (index >= j && index < k) {
               if (!this.mergeItemStack(itemstack1, i, j, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= i && index < j) {
               if (!this.mergeItemStack(itemstack1, j, k, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.mergeItemStack(itemstack1, j, j, false)) {
               return ItemStack.EMPTY;
            }

            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         } else {
            slot.onSlotChanged();
         }
      }

      return itemstack;
   }

   /**
    * Called when the container is closed.
    */
   public void onContainerClosed(PlayerEntity playerIn) {
      super.onContainerClosed(playerIn);
      this.slabfishInventory.closeInventory(playerIn);
   }
}
