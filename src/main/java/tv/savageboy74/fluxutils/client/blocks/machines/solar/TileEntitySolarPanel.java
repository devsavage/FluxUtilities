package tv.savageboy74.fluxutils.client.blocks.machines.solar;

/*
 * TileEntitySolarPanel.java
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

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import com.google.common.base.Objects;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import tv.savageboy74.fluxutils.common.tileentity.FluxTileEntity;
import tv.savageboy74.fluxutils.util.Strings;

public class TileEntitySolarPanel extends FluxTileEntity implements IEnergyHandler
{
    private int maxEnergyGeneration;
    private EnergyStorage energyStorage;
    private static final int TRANSFER_TICK_RATE = 5 * 20;

    public TileEntitySolarPanel()
    {
        this(0, 0, 0);
    }

    public TileEntitySolarPanel(int energyGeneration, int energyTransfer, int energyCapacity)
    {
        maxEnergyGeneration = energyGeneration;
        energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (isServer()) {
            generateEnergy();
            if (shouldTransferEnergy()) {
                transferEnergy();
            }

            if (shouldBalanceEnergy()) {
                tryBalanceEnergyAt(x() + 1, y(), z());
                tryBalanceEnergyAt(x(), y(), z() + 1);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int scale)
    {
        double stored = getEnergyStored();
        double max = getMaxEnergyStored();
        double value = ((stored /max) * scale);
        return (int)value;
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        maxEnergyGeneration = nbt.getInteger(Strings.NBT.MAXIMUM_ENERGY_GENERATION);
        energyStorage.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger(Strings.NBT.MAXIMUM_ENERGY_GENERATION, maxEnergyGeneration);
        energyStorage.writeToNBT(nbt);
    }

    private void tryBalanceEnergyAt(int x, int y, int z) {
        TileEntity tile = getWorldObj().getTileEntity(x, y, z);
        if (tile instanceof TileEntitySolarPanel) {
            TileEntitySolarPanel neighbor = (TileEntitySolarPanel) tile;
            energyStorage.balanceStoredEnergy(neighbor.energyStorage, TRANSFER_TICK_RATE);
        }
    }

    private boolean shouldBalanceEnergy() {
        return getWorldObj().getTotalWorldTime() % TRANSFER_TICK_RATE == 0;
    }

    protected void transferEnergy() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = getWorldObj().getTileEntity(xCoord + direction.offsetX,
                    yCoord + direction.offsetY,
                    zCoord + direction.offsetZ);
            if (!(tile instanceof TileEntitySolarPanel))
            {
                if (tile instanceof IEnergyReceiver)
                {
                    IEnergyReceiver receiver = (IEnergyReceiver) tile;
                    energyStorage.sendMaxEnergyTo(receiver, direction.getOpposite());
                }
            }
        }
    }

    protected boolean shouldTransferEnergy() {
        return energyStorage.getEnergyStored() > 0;
    }

    private int getSunProduction() {
        float multiplication = 1.5F;
        float displacement = 1.2F;
        float celestialAngleRadians = worldObj.getCelestialAngleRadians(1.0f);

        if (celestialAngleRadians > Math.PI) {
            celestialAngleRadians = (2 * 3.141592f - celestialAngleRadians);
        }

        return Math.round(maxEnergyGeneration * multiplication * MathHelper.cos(celestialAngleRadians / displacement));
    }

    public int getEnergyProduced() {
        if (worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
            int sunProduction = getSunProduction();
            if (sunProduction > 0) {
                if (worldObj.isRaining()) {
                    sunProduction *= 0.2F;
                }
                if (worldObj.isThundering()) {
                    sunProduction *= 0.2F;
                }
                return Math.min(maxEnergyGeneration, sunProduction);
            }
        }
        return 0;
    }

    protected void generateEnergy()
    {
        int produced = getEnergyProduced();
        if (produced > 0 && !energyStorage.isFull())
        {
            energyStorage.receiveEnergy(produced, false);
        }
    }

    public void setEnergyStored(int energy)
    {
        this.markDirty();
        energyStorage.setEnergyStored(energy);
    }

    public int getPercentEnergyStored()
    {
        long v = getEnergyStored();
        return (int) (100 * v / getMaxEnergyStored());
    }

    public int getEnergyStored()
    {
        return getEnergyStored(ForgeDirection.DOWN);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return energyStorage.extractEnergy(energyStorage.getMaxEnergyExtract(), simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return getMaxEnergyStored(ForgeDirection.DOWN);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from != ForgeDirection.UP;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("Hash", hashCode()).add("MaxEnergyGeneration", maxEnergyGeneration).add("EnergyStorage", energyStorage).toString();
    }

    public EnergyStorage getEnergyStorage()
    {
        return energyStorage;
    }
}
