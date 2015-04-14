package tv.savageboy74.fluxutils.common.tileentity;

/*
 * FluxTileEntity.java
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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tv.savageboy74.fluxutils.common.network.message.MessageFluxTileEntity;
import tv.savageboy74.fluxutils.common.network.packet.PacketHandler;
import tv.savageboy74.fluxutils.util.Strings;

public class FluxTileEntity extends TileEntity
{
    protected ForgeDirection orientation;
    protected byte state;
    protected String customName;
    protected String owner;

    public FluxTileEntity()
    {
        orientation = ForgeDirection.SOUTH;
        state = 0;
        customName = "";
        owner = "";
    }

    public ForgeDirection getOrientation()
    {
        return orientation;
    }

    public void setOrientation(ForgeDirection orientation)
    {
        this.orientation = orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = ForgeDirection.getOrientation(orientation);
    }

    public short getState()
    {
        return state;
    }

    public void setState(byte state)
    {
        this.state = state;
    }

    public String getCustomName()
    {
        return customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey(Strings.NBT.DIRECTION))
        {
            this.orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte(Strings.NBT.DIRECTION));
        }

        if (nbtTagCompound.hasKey(Strings.NBT.STATE))
        {
            this.state = nbtTagCompound.getByte(Strings.NBT.STATE);
        }

        if (nbtTagCompound.hasKey(Strings.NBT.CUSTOM_NAME))
        {
            this.customName = nbtTagCompound.getString(Strings.NBT.CUSTOM_NAME);
        }

        if (nbtTagCompound.hasKey(Strings.NBT.OWNER))
        {
            this.owner = nbtTagCompound.getString(Strings.NBT.OWNER);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte(Strings.NBT.DIRECTION, (byte) orientation.ordinal());
        nbtTagCompound.setByte(Strings.NBT.STATE, state);

        if (this.hasCustomName())
        {
            nbtTagCompound.setString(Strings.NBT.CUSTOM_NAME, customName);
        }

        if (this.hasOwner())
        {
            nbtTagCompound.setString(Strings.NBT.OWNER, owner);
        }
    }

    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    public boolean hasOwner()
    {
        return owner != null && owner.length() > 0;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.INSTANCE.getPacketFrom(new MessageFluxTileEntity(this));
    }

    public int x() {
        return xCoord;
    }

    public int y() {
        return yCoord;
    }

    public int z() {
        return zCoord;
    }

    public boolean isClient() {
        return getWorldObj().isRemote;
    }

    public boolean isServer() {
        return !isClient();
    }
}
