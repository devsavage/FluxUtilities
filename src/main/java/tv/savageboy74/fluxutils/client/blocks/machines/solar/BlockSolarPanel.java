package tv.savageboy74.fluxutils.client.blocks.machines.solar;

/*
 * BlockSolarPanel.java
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tv.savageboy74.fluxutils.FluxUtils;
import tv.savageboy74.fluxutils.common.block.FluxBlockContainer;
import tv.savageboy74.fluxutils.util.GUI;
import tv.savageboy74.fluxutils.util.Reference;
import tv.savageboy74.fluxutils.util.Strings;

public class BlockSolarPanel extends FluxBlockContainer
{
    protected final int maxEnergyGeneration;
    protected final int maxEnergyTransfer;
    protected final int maxEnergyCapacity;
    private final int type;

    @SideOnly(Side.CLIENT)
    private IIcon solarIconTop;

    @SideOnly(Side.CLIENT)
    private IIcon hardenedIconTop;

    @SideOnly(Side.CLIENT)
    private IIcon reinforcedIconTop;

    @SideOnly(Side.CLIENT)
    private IIcon solarIconBottom;

    @SideOnly(Side.CLIENT)
    private IIcon resonantIconBottom;

    @SideOnly(Side.CLIENT)
    private IIcon resonantIconTop;

    @SideOnly(Side.CLIENT)
    private IIcon resonantIconSide;


    public BlockSolarPanel(Material material, int energyGen, int energyCapacity, int type)
    {
        super(material);
        this.maxEnergyGeneration = energyGen;
        this.maxEnergyTransfer = maxEnergyGeneration * 4;
        this.maxEnergyCapacity = energyCapacity;
        this.type = type;
        setStepSound(soundTypeMetal);
    }

    public int getMaxEnergyGeneration()
    {
        return maxEnergyGeneration;
    }

    public int getMaxEnergyTransfer()
    {
        return maxEnergyTransfer;
    }

    public int getMaxEnergyCapacity()
    {
        return maxEnergyCapacity;
    }

    public int getType()
    {
        return this.type;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntitySolarPanel(maxEnergyGeneration, maxEnergyTransfer, maxEnergyCapacity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        //What a terrible way to do this.
        this.blockIcon = iconRegister.registerIcon(Reference.mod_id + ":" + "solar_panel");
        this.solarIconTop = iconRegister.registerIcon(Reference.mod_id + ":" + "solar_panel_top");
        this.solarIconBottom = iconRegister.registerIcon(Reference.mod_id + ":" + "solar_panel_bottom");
        this.hardenedIconTop = iconRegister.registerIcon(Reference.mod_id + ":" + "hardened_solar_panel_top");
        this.reinforcedIconTop = iconRegister.registerIcon(Reference.mod_id + ":" + "reinforced_solar_panel_top");
        this.resonantIconBottom = iconRegister.registerIcon(Reference.mod_id + ":" + "resonant_solar_panel_bottom");
        this.resonantIconSide = iconRegister.registerIcon(Reference.mod_id + ":" + "resonant_solar_panel");
        this.resonantIconTop = iconRegister.registerIcon(Reference.mod_id + ":" + "resonant_solar_panel_top");
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        //I could probably do this a little better
        switch (this.type)
        {
            case 4:
                return meta == 0 && side == 1 ? this.resonantIconTop : (side == 0 ? this.resonantIconBottom  : this.resonantIconSide);
            case 3:
                return meta == 0 && side == 1 ? this.reinforcedIconTop : (side == 0 ? this.solarIconBottom  : this.blockIcon);
            case 2:
                return meta == 0 && side == 1 ? this.hardenedIconTop : (side == 0 ? this.solarIconBottom  : this.blockIcon);
            case 1:
                default:
                    return meta == 0 && side == 1 ? this.solarIconTop : (side == 0 ? this.solarIconBottom  : this.blockIcon);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        if (itemStack.stackTagCompound != null)
        {
            TileEntitySolarPanel tileSolar = (TileEntitySolarPanel) world.getTileEntity(x, y, z);
            tileSolar.setEnergyStored(itemStack.stackTagCompound.getInteger(Strings.NBT.ENERGY));
        }

        super.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        //TODO Create the blocks via machine frames
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float a, float b, float c)
    {
        if (!world.isRemote)
        {
            if (world.getTileEntity(x, y, z) instanceof TileEntitySolarPanel)
            {
                player.openGui(FluxUtils.instance, GUI.IDs.SOLAR_PANEL.ordinal(), world, x, y, z);
                return true;
            }
        }


        return false;
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int i, int j)
    {
        super.onBlockEventReceived(world, x, y, z, i, j);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null)
        {
            return tileEntity.receiveClientEvent(i, j);
        }

        return false;
    }
}
