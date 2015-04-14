package tv.savageboy74.fluxutils.client.blocks.machines.solar;

/*
 * ContainerSolarPanel.java
 * Copyright (C) 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tv.savageboy74.fluxutils.client.inventory.container.FluxContainer;
import tv.savageboy74.fluxutils.common.network.message.MessageSolarPanel;
import tv.savageboy74.fluxutils.common.network.packet.PacketHandler;

public class ContainerSolarPanel extends FluxContainer
{
    private TileEntitySolarPanel tileSolar;
    private EntityPlayer entityPlayer;
    public int pastEnergyStored;

    public ContainerSolarPanel(EntityPlayer player, TileEntitySolarPanel solarPanel)
    {
        tileSolar = solarPanel;
        entityPlayer = player;
        addPlayerInventory(player.inventory);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        if (this.pastEnergyStored != this.tileSolar.getEnergyStored())
        {
            MessageSolarPanel message = new MessageSolarPanel(tileSolar.x(), tileSolar.y(), tileSolar.z(), tileSolar.getEnergyStored(null));
            PacketHandler.INSTANCE.sendTo(message, (EntityPlayerMP) entityPlayer);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();


            if (slotIndex < PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS)
            {
                if (!this.mergeItemStack(itemStack, PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS, inventorySlots.size(), false))
                {
                    return null;
                }
            }

            else if (!this.mergeItemStack(itemStack, 0, PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS, false))
            {
                return null;
            }


            if (itemStack.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }
}
