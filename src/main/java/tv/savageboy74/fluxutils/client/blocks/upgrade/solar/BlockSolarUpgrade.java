package tv.savageboy74.fluxutils.client.blocks.upgrade.solar;

/*
 * BlockSolarUpgrade.java
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tv.savageboy74.fluxutils.common.block.FluxBlockContainer;
import tv.savageboy74.fluxutils.common.block.FluxBlocks;
import tv.savageboy74.fluxutils.util.FluxHelper;
import tv.savageboy74.fluxutils.util.Reference;
import tv.savageboy74.fluxutils.util.Strings;

public class BlockSolarUpgrade extends FluxBlockContainer
{
    public BlockSolarUpgrade()
    {
        super(Material.redstoneLight);
        this.setStepSound(soundTypeStone);
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.35F, 1F);
        this.setHardness(2.0F);
        this.setResistance(1.5F);
        this.setBlockName(Strings.Blocks.SOLAR_UPGRADE);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        trySetBlockToSolarPanel(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemstack)
    {
        int l = MathHelper.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.F) + 0.5D) & 3;

        if(l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if(l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if(l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if(l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }


    @Override
    public TileEntity createNewTileEntity(World world, int side)
    {
        return new TileEntitySolarUpgrade();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(5));
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public void trySetBlockToSolarPanel(World world, int x, int y, int z)
    {
        boolean basic = world.getBlock(x, y -1, z) == FluxBlocks.basicSolarBase;
        boolean hardened = world.getBlock(x, y -1, z) == FluxBlocks.hardenedSolarBase;
        boolean reinforced = world.getBlock(x, y -1, z) == FluxBlocks.reinforcedSolarBase;
        boolean resonant = world.getBlock(x, y -1, z) == FluxBlocks.resonantSolarBase;

        if (FluxHelper.isServer(world))
        {
            if (basic)
            {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, FluxBlocks.basicSolarPanel);
                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
            }

            else if (hardened)
            {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, FluxBlocks.hardenedSolarPanel);
                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
            }

            else if (reinforced)
            {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, FluxBlocks.reinforcedSolarPanel);
                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
            }

            else if (resonant)
            {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, FluxBlocks.resonantSolarPanel);
                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
            }
        }
    }
}
