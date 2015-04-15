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
    protected int maxTransferredFromReceived;
    protected int maxTransferredFromExtracted;

    public EnergyStorage(int energyCapacity)
    {
        this(energyCapacity, energyCapacity, energyCapacity);
    }

    public EnergyStorage(int energyCapacity, int maxEnergyTransfer) {
        this(energyCapacity, maxEnergyTransfer, maxEnergyTransfer);
    }

    public EnergyStorage(int energyCapacity, int maxEnergyTransferReceive, int maxEnergyTransferExtract) {
        maxCapacity = energyCapacity;
        maxTransferredFromReceived = maxEnergyTransferReceive;
        maxTransferredFromExtracted = maxEnergyTransferExtract;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        setMaxEnergyStored(nbt.getInteger(Strings.NBT.CAPACITY));
        setEnergyStored(nbt.getInteger(Strings.NBT.ENERGY));
        setMaxTransferReceive(nbt.getInteger(Strings.NBT.MAX_TRANSFER_RECEIVE));
        setMaxTransferExtract(nbt.getInteger(Strings.NBT.MAX_TRANSFER_EXTRACT));
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger(Strings.NBT.CAPACITY, getMaxEnergyStored());
        nbt.setInteger(Strings.NBT.ENERGY, getEnergyStored());
        nbt.setInteger(Strings.NBT.MAX_TRANSFER_RECEIVE, getMaxTransferReceive());
        nbt.setInteger(Strings.NBT.MAX_TRANSFER_EXTRACT, getMaxTransferExtract());
    }

    public int getMaxReceive() {
        return Math.min(maxCapacity - maxEnergy, maxTransferredFromReceived);
    }

    public int getMaxExtract() {
        return Math.min(maxEnergy, maxTransferredFromExtracted);
    }

    @Override
    public int receiveEnergy(int maxRecieve, boolean simulate) {
        int energyReceived = Math.min(getMaxReceive(), Math.max(maxRecieve, 0));
        if (!simulate) {
            maxEnergy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(getMaxExtract(), Math.max(maxExtract, 0));
        if (!simulate) {
            maxEnergy -= energyExtracted;
        }
        return energyExtracted;
    }

    public int sendMaxEnergyTo(IEnergyReceiver energyReceiver, ForgeDirection from)
    {
        return extractEnergy(energyReceiver.receiveEnergy(from, getMaxExtract(), false), false);
    }
    
    public int balanceStoredEnergy(EnergyStorage energyStorage) {
        int delta = getEnergyStored() - energyStorage.getEnergyStored();
        if (delta < 0) {
            return energyStorage.balanceStoredEnergy(this);
        } else if (delta > 0 && !energyStorage.isFull()) {
            return extractEnergy(energyStorage.receiveEnergy(delta / 2, false), false);
        }
        return 0;
    }

    public int balanceStoredEnergy(EnergyStorage energyStorage, int transferRate) {
        maxTransferredFromExtracted *= transferRate;
        maxTransferredFromReceived *= transferRate;
        energyStorage.maxTransferredFromExtracted *= transferRate;
        energyStorage.maxTransferredFromReceived *= transferRate;
        int result = balanceStoredEnergy(energyStorage);
        maxTransferredFromExtracted /= transferRate;
        maxTransferredFromReceived /= transferRate;
        energyStorage.maxTransferredFromExtracted /= transferRate;
        energyStorage.maxTransferredFromReceived /= transferRate;
        return result;
    }

    @Override
    public int getEnergyStored() {
        return maxEnergy;
    }

    public void setEnergyStored(int energy) {
        maxEnergy = energy;
        if (maxEnergy > maxCapacity) {
            maxEnergy = maxCapacity;
        } else if (maxEnergy < 0) {
            maxEnergy = 0;
        }
    }

    @Override
    public int getMaxEnergyStored() {
        return maxCapacity;
    }

    public void setMaxEnergyStored(int energyCapacity) {
        maxCapacity = energyCapacity;
        if (maxEnergy > maxCapacity) {
            maxEnergy = maxCapacity;
        }
    }

    public boolean isFull() {
        return getEnergyStored() == getMaxEnergyStored();
    }

    public int getMaxTransferReceive() {
        return maxTransferredFromReceived;
    }

    public void setMaxTransferReceive(int maxEnergyTransferReceive) {
        maxTransferredFromReceived = maxEnergyTransferReceive;
    }

    public int getMaxTransferExtract() {
        return maxTransferredFromExtracted;
    }

    public void setMaxTransferExtract(int maxEnergyTransferExtract) {
        maxTransferredFromExtracted = maxEnergyTransferExtract;
    }

    public void setMaxTransfer(int maxEnergyTransfer) {
        setMaxTransferReceive(maxEnergyTransfer);
        setMaxTransferExtract(maxEnergyTransfer);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("Energy", getEnergyStored())
                .add("Capacity", getMaxEnergyStored())
                .add("MaxTransferReceive", getMaxTransferReceive())
                .add("MaxTransferExtract", getMaxTransferExtract())
                .toString();
    }
}
