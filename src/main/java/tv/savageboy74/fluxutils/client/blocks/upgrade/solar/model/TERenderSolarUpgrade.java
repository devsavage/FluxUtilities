package tv.savageboy74.fluxutils.client.blocks.upgrade.solar.model;

/*
 * TERenderSolarUpgrade.java
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

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tv.savageboy74.fluxutils.util.Reference;

public class TERenderSolarUpgrade extends TileEntitySpecialRenderer
{
    private static final ResourceLocation texture = new ResourceLocation(Reference.mod_id + ":" + "textures/blocks/SolarUpgrade.png");

    private ModelSolarUpgrade model;

    public TERenderSolarUpgrade()
    {
        this.model = new ModelSolarUpgrade();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        int i;

        if (tileEntity.getWorldObj() == null)
        {
            i = 0;
        }

        else
        {
            Block block = tileEntity.getBlockType();
            i = tileEntity.getBlockMetadata();
            if (block != null && i == 0)
            {
                i = tileEntity.getBlockMetadata();
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);

        int j = 0;

        if(i == 3)
        {
            j = 270;
        }
        if(i == 2)
        {
            j = 180;
        }
        if(i == 1)
        {
            j = 90;
        }
        if(i == 0)
        {
            j = 360;
        }

        GL11.glRotatef(j, 0.0F, 1.0F, 0F);
        this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
