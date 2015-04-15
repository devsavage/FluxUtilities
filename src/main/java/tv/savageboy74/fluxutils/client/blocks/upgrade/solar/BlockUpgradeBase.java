package tv.savageboy74.fluxutils.client.blocks.upgrade.solar;

/*
 * BlockUpgradeBase.java
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import tv.savageboy74.fluxutils.common.block.FluxBlock;
import tv.savageboy74.fluxutils.util.Reference;

public class BlockUpgradeBase extends FluxBlock
{
    private int type;

    @SideOnly(Side.CLIENT)
    public IIcon basicSide;

    @SideOnly(Side.CLIENT)
    public IIcon resonantSide;

    @SideOnly(Side.CLIENT)
    public IIcon resonantTB;

    public BlockUpgradeBase(int type)
    {
        super(Material.iron);
        this.type = type;
        this.setHardness(3.5F);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(Reference.mod_id + ":" + "solar_panel_bottom");
        this.basicSide = iconRegister.registerIcon(Reference.mod_id + ":" + "solar_panel");
        this.resonantSide = iconRegister.registerIcon(Reference.mod_id + ":" + "resonant_solar_panel");
        this.resonantTB = iconRegister.registerIcon(Reference.mod_id + ":" + "resonant_solar_panel_bottom");
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        switch (this.type)
        {
            case 2:
                return meta == 0 && side == 1 ? this.resonantTB : (side == 0 ? this.resonantTB  : this.resonantSide);
            case 1:
                default:
                    return meta == 0 && side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon  : this.basicSide);
        }
    }
}
