package tv.savageboy74.fluxutils.common.inventory;

/*
 * FluxRecipes.java
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

import cofh.thermalexpansion.block.simple.BlockFrame;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tv.savageboy74.fluxutils.common.block.FluxBlocks;

public class FluxRecipes
{
    public static void register()
    {
    }

    public static void registerThermalExpansionRecipes()
    {
        ItemStack machineBasic = new ItemStack(GameRegistry.findBlock("ThermalExpansion", "Frame"), 1, 0);
        ItemStack machineHardened = new ItemStack(GameRegistry.findBlock("ThermalExpansion", "Frame"), 1, 1);
        ItemStack machineReinforced = new ItemStack(GameRegistry.findBlock("ThermalExpansion", "Frame"), 1, 2);
        ItemStack machineResonant = new ItemStack(GameRegistry.findBlock("ThermalExpansion", "Frame"), 1, 3);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.basicSolarBase, 1), new Object[]{ "III", "IFI", "III", 'I', "blockTin", 'F', machineBasic}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.hardenedSolarBase, 1), new Object[]{ "III", "IFI", "III", 'I', "blockElectrum", 'F', machineHardened}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.reinforcedSolarBase, 1), new Object[]{ "III", "IFI", "III", 'I', "blockSignalum", 'F', machineReinforced}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FluxBlocks.resonantSolarBase, 1), new Object[]{ "III", "IFI", "III", 'I', "blockEnderium", 'F', machineResonant}));
    }
}
