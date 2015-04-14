package tv.savageboy74.fluxutils.common.network.message;

/*
 * MessageSolarPanel.java
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

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.TileEntitySolarPanel;

public class MessageSolarPanel implements IMessage, IMessageHandler<MessageSolarPanel, IMessage>
{
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private int energyStored;

    public MessageSolarPanel(){}

    public MessageSolarPanel(int x, int y, int z, int storedEnergy)
    {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        this.energyStored = storedEnergy;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.xCoord = buf.readInt();
        this.yCoord = buf.readInt();
        this.zCoord = buf.readInt();
        this.energyStored = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.xCoord);
        buf.writeInt(this.yCoord);
        buf.writeInt(this.zCoord);
        buf.writeInt(this.energyStored);
    }

    @Override
    public IMessage onMessage(MessageSolarPanel message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getWorldClient().getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tileEntity instanceof TileEntitySolarPanel)
        {
            ((TileEntitySolarPanel) tileEntity).setEnergyStored(message.energyStored);
        }

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageSolarPanel - x:%s, y:%s, z:%s, storedEnergy:%s", xCoord, yCoord, zCoord, energyStored);
    }
}
