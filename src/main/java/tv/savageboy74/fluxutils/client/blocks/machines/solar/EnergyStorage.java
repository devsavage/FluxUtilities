package tv.savageboy74.fluxutils.client.blocks.machines.solar;

/*
 * EnergyStorage.java
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

import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import com.google.common.base.Objects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import tv.savageboy74.fluxutils.util.Strings;

public class EnergyStorage implements IEnergyStorage
{
    protected int maxEnergy;
    protected int maxCapacity;
    protected int maxEnergyReceive;
    protected int maxEnergyExtract;

    public EnergyStorage(int energyCapacity)
    {
        this(energyCapacity, energyCapacity, energyCapacity);
    }

    public EnergyStorage(int energyCapacity, int energyReceive)
    {
        this(energyCapacity, energyReceive, energyReceive);
    }

    public EnergyStorage(int energyCapacity, int energyReceive, int energyExtract)
    {
        maxCapacity = energyCapacity;
        maxEnergyReceive = energyReceive;
        maxEnergyExtract = energyExtract;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        setMaxEnergyStored(nbt.getInteger(Strings.NBT.CAPACITY));
        setEnergyStored(nbt.getInteger(Strings.NBT.ENERGY));
        setMaxEnergyReceived(nbt.getInteger(Strings.NBT.MAX_TRANSFER_RECEIVE));
        setMaxEnergyExtracted(nbt.getInteger(Strings.NBT.MAX_TRANSFER_EXTRACT));
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger(Strings.NBT.CAPACITY, getMaxEnergyStored());
        nbt.setInteger(Strings.NBT.ENERGY, getEnergyStored());
        nbt.setInteger(Strings.NBT.MAX_TRANSFER_RECEIVE, getMaxEnergyReceived());
        nbt.setInteger(Strings.NBT.MAX_TRANSFER_EXTRACT, getMaxEnergyExtracted());
    }

    public int sendMaxEnergyTo(IEnergyReceiver energyReceiver, ForgeDirection direction)
    {
        return extractEnergy(energyReceiver.receiveEnergy(direction, getMaxEnergyExtract(), false), false);
    }

    public int balanceStoredEnergy(EnergyStorage energyStorage) {
        int delta = getEnergyStored() - energyStorage.getEnergyStored();
        if (delta < 0)
        {
            return energyStorage.balanceStoredEnergy(this);
        }

        else if (delta > 0 && !energyStorage.isFull())
        {
            return extractEnergy(energyStorage.receiveEnergy(delta / 2, false), false);
        }

        return 0;
    }

    public int balanceStoredEnergy(EnergyStorage energyStorage, int transferSpeed)
    {
        maxEnergyExtract *= transferSpeed;
        maxEnergyReceive *= transferSpeed;
        energyStorage.maxEnergyExtract *= transferSpeed;
        energyStorage.maxEnergyReceive *= transferSpeed;
        int result = balanceStoredEnergy(energyStorage);
        maxEnergyExtract /= transferSpeed;
        maxEnergyReceive /= transferSpeed;
        energyStorage.maxEnergyExtract /= transferSpeed;
        energyStorage.maxEnergyReceive /= transferSpeed;
        return result;
    }

    public int getMaxEnergyReceive()
    {
        return Math.min(maxCapacity - maxEnergy, maxEnergyReceive);
    }

    public int getMaxEnergyExtract()
    {
        return Math.min(maxEnergy, maxEnergyExtract);
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int energyReceived = Math.min(getMaxEnergyReceived(), Math.max(maxReceive, 0));
        if (!simulate)
        {
            maxEnergy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int energyExtracted = Math.min(getMaxEnergyExtract(), Math.max(maxExtract, 0));
        if (!simulate)
        {
            maxEnergy -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored()
    {
        return maxEnergy;
    }

    public void setEnergyStored(int energy)
    {
        maxEnergy = energy;
        if (maxEnergy > maxCapacity)
        {
            maxEnergy = maxCapacity;
        }

        else if (maxEnergy < 0)
        {
            maxEnergy = 0;
        }
    }

    @Override
    public int getMaxEnergyStored()
    {
        return maxCapacity;
    }

    public void setMaxEnergyStored(int capacity)
    {
        maxCapacity = capacity;
        if (maxEnergy > maxCapacity)
        {
            maxEnergy = maxCapacity;
        }
    }

    public boolean isFull()
    {
        return getEnergyStored() == getMaxEnergyStored();
    }

    public int getMaxEnergyReceived()
    {
        return maxEnergyReceive;
    }

    public void setMaxEnergyReceived(int energyToReceive)
    {
        maxEnergyReceive = energyToReceive;
    }

    public int getMaxEnergyExtracted()
    {
        return maxEnergyExtract;
    }

    public void setMaxEnergyExtracted(int energyToExtract)
    {
        maxEnergyExtract = energyToExtract;
    }

    public void setMaxEnergyToTransfer(int transferAmount)
    {
        setMaxEnergyReceived(transferAmount);
        setMaxEnergyExtracted(transferAmount);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("Energy", getEnergyStored()).add("Capacity", getMaxEnergyStored()).add("MaxEnergyReceived", getMaxEnergyReceived()).add("MaxEnergyExtracted", getMaxEnergyExtracted()).toString();
    }
}
