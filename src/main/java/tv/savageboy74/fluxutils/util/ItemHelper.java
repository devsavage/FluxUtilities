package tv.savageboy74.fluxutils.util;

/*
 * ItemHelper.java
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.Comparator;
import java.util.UUID;

public class ItemHelper
{
    public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize)
    {
        ItemStack clonedItemStack = itemStack.copy();
        clonedItemStack.stackSize = stackSize;
        return clonedItemStack;
    }

    public static Comparator<ItemStack> comparator = new Comparator<ItemStack>()
    {
        public int compare(ItemStack itemStack1, ItemStack itemStack2)
        {
            if (itemStack1 != null && itemStack2 != null)
            {
                // Sort on itemID
                if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0)
                {
                    // Sort on item
                    if (itemStack1.getItem() == itemStack2.getItem())
                    {
                        // Then sort on meta
                        if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                        {
                            // Then sort on NBT
                            if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                            {
                                // Then sort on stack size
                                if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                                {
                                    return (itemStack1.stackSize - itemStack2.stackSize);
                                }
                                else
                                {
                                    return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                                }
                            }
                            else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound())
                            {
                                return -1;
                            }
                            else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound()))
                            {
                                return 1;
                            }
                            else
                            {
                                return (itemStack1.stackSize - itemStack2.stackSize);
                            }
                        }
                        else
                        {
                            return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                        }
                    }
                    else
                    {
                        return itemStack1.getItem().getUnlocalizedName(itemStack1).compareToIgnoreCase(itemStack2.getItem().getUnlocalizedName(itemStack2));
                    }
                }
                else
                {
                    return Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem());
                }
            }
            else if (itemStack1 != null)
            {
                return -1;
            }
            else if (itemStack2 != null)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    };

    /**
     * Compares two ItemStacks for equality, testing itemID, metaData, stackSize, and their NBTTagCompounds (if they are
     * present)
     *
     * @param first  The first ItemStack being tested for equality
     * @param second The second ItemStack being tested for equality
     * @return true if the two ItemStacks are equivalent, false otherwise
     */
    public static boolean equals(ItemStack first, ItemStack second)
    {
        return (comparator.compare(first, second) == 0);
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2)
    {
        if (itemStack1 != null && itemStack2 != null)
        {
            // Sort on itemID
            if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0)
            {
                // Sort on item
                if (itemStack1.getItem() == itemStack2.getItem())
                {
                    // Then sort on meta
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                    {
                        // Then sort on NBT
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                        {
                            // Then sort on stack size
                            if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                            {
                                return true;
                            }
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static int compare(ItemStack itemStack1, ItemStack itemStack2)
    {
        return comparator.compare(itemStack1, itemStack2);
    }

    public static String toString(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            return String.format("%sxitemStack[%s@%s]", itemStack.stackSize, itemStack.getUnlocalizedName(), itemStack.getItemDamage());
        }

        return "null";
    }

    public static boolean hasOwner(ItemStack itemStack)
    {
        return (NBTHelper.hasTag(itemStack, Strings.NBT.OWNER_UUID_MOST_SIG) && NBTHelper.hasTag(itemStack, Strings.NBT.OWNER_UUID_LEAST_SIG)) || NBTHelper.hasTag(itemStack, Strings.NBT.OWNER);
    }

    public static String getOwnerName(ItemStack itemStack)
    {
        if (NBTHelper.hasTag(itemStack, Strings.NBT.OWNER))
        {
            return NBTHelper.getString(itemStack, Strings.NBT.OWNER);
        }

        return StatCollector.translateToLocal(Strings.Messages.NO_OWNER);
    }

    public static UUID getOwnerUUID(ItemStack itemStack)
    {
        if (NBTHelper.hasTag(itemStack, Strings.NBT.OWNER_UUID_MOST_SIG) && NBTHelper.hasTag(itemStack, Strings.NBT.OWNER_UUID_LEAST_SIG))
        {
            return new UUID(NBTHelper.getLong(itemStack, Strings.NBT.OWNER_UUID_MOST_SIG), NBTHelper.getLong(itemStack, Strings.NBT.OWNER_UUID_LEAST_SIG));
        }

        return null;
    }

    public static void setOwner(ItemStack itemStack, EntityPlayer entityPlayer)
    {
        NBTHelper.setString(itemStack, Strings.NBT.OWNER, entityPlayer.getDisplayName());
        NBTHelper.setLong(itemStack, Strings.NBT.OWNER_UUID_MOST_SIG, entityPlayer.getUniqueID().getMostSignificantBits());
        NBTHelper.setLong(itemStack, Strings.NBT.OWNER_UUID_LEAST_SIG, entityPlayer.getUniqueID().getLeastSignificantBits());
    }
}
