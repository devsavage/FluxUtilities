package tv.savageboy74.fluxutils.common.block;

/*
 * FluxBlocks.java
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

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.BlockSolarPanel;
import tv.savageboy74.fluxutils.util.Strings;

public class FluxBlocks
{
    public static Block basicSolarPanel = new BlockSolarPanel(Material.redstoneLight, 256, 250000, 1).setHardness(0.5F).setBlockName(Strings.Blocks.BASIC_SOLAR);
    public static Block hardenedSolarPanel = new BlockSolarPanel(Material.iron, 512, 500000, 2).setHardness(3.5F).setBlockName(Strings.Blocks.HARDENED_SOLAR);
    public static Block reinforcedSolarPanel = new BlockSolarPanel(Material.coral,1024, 750000, 3).setHardness(2.0F).setBlockName(Strings.Blocks.REINFORCED_SOLAR);
    public static Block resonantSolarPanel = new BlockSolarPanel(Material.anvil, 4096, 1000000, 4).setHardness(7.0F).setBlockName(Strings.Blocks.RESONANT_SOLAR);

    public static void register()
    {
        GameRegistry.registerBlock(basicSolarPanel, Strings.Blocks.BASIC_SOLAR);
        GameRegistry.registerBlock(hardenedSolarPanel, Strings.Blocks.HARDENED_SOLAR);
        GameRegistry.registerBlock(reinforcedSolarPanel, Strings.Blocks.REINFORCED_SOLAR);
        GameRegistry.registerBlock(resonantSolarPanel, Strings.Blocks.RESONANT_SOLAR);
    }
}
