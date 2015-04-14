package tv.savageboy74.fluxutils.util;

/*
 * Strings.java
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

public class Strings
{
    public static final class Blocks
    {
        public static final String BASIC_SOLAR = "BasicSolarPanel";
        public static final String HARDENED_SOLAR = "HardenedSolarPanel";
        public static final String REINFORCED_SOLAR = "ReinforcedSolarPanel";
        public static final String RESONANT_SOLAR = "ResonantSolarPanel";
    }

    public static final class NBT
    {
        public static final String ITEMS = "Items";
        public static final String UUID_MOST_SIG = "UUIDMostSig";
        public static final String UUID_LEAST_SIG = "UUIDLeastSig";
        public static final String OWNER = "Owner";
        public static final String OWNER_UUID_MOST_SIG = "OwnerUUIDMostSig";
        public static final String OWNER_UUID_LEAST_SIG = "OwnerUUIDLeastSig";
        public static final String CUSTOM_NAME = "CustomName";
        public static final String DIRECTION = "TEDirection";
        public static final String STATE = "TEState";
        public static final String ENERGY = "FluxEnergy";
        public static final String CAPACITY = "FluxCapacity";
        public static final String MAX_TRANSFER_RECEIVE = "FluxMaxTransferReceive";
        public static final String MAX_TRANSFER_EXTRACT = "FluxMaxTransferExtract";
        public static final String MAXIMUM_ENERGY_GENERATION = "FluxMaxEnergyGeneration";
    }

    public static final class Messages
    {
        public static final String NO_OWNER = "tooltip.fluxutils:none";
    }
}
