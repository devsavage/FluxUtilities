package tv.savageboy74.fluxutils.common.inventory.gui;

/*
 * GuiSolarPanel.java
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

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.BlockSolarPanel;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.ContainerSolarPanel;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.TileEntitySolarPanel;
import tv.savageboy74.fluxutils.util.Textures;

import java.text.NumberFormat;

public class GuiSolarPanel extends GuiContainer
{
    private TileEntitySolarPanel tileSolarPanel;
    private String displayName;
    public GuiSolarPanel(EntityPlayer player, TileEntitySolarPanel tileSolarPanel)
    {
        super(new ContainerSolarPanel(player, tileSolarPanel));
        this.tileSolarPanel = tileSolarPanel;
        this.xSize = 176;
        this.ySize = 166;
        initDisplay();
    }

    private void initDisplay()
    {
        //Neat way of doing this, huh?
        if(tileSolarPanel.getBlockType().getMaterial() == Material.redstoneLight) {
            displayName = "Basic Solar Panel";
        } else if (tileSolarPanel.getBlockType().getMaterial() == Material.iron) {
            displayName = "Hardened Solar Panel";
        } else if (tileSolarPanel.getBlockType().getMaterial() == Material.coral) {
            displayName = "Reinforced Solar Panel";
        } else if (tileSolarPanel.getBlockType().getMaterial() == Material.anvil) {
            displayName = "Resonant Solar Panel";
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        String storedEnergy = NumberFormat.getIntegerInstance().format(tileSolarPanel.getEnergyStored(null));
        String maxEnergy = NumberFormat.getIntegerInstance().format(tileSolarPanel.getMaxEnergyStored());
        String energyGenerated = NumberFormat.getIntegerInstance().format(tileSolarPanel.getEnergyProduced());
        this.fontRendererObj.drawString(displayName, this.xSize / 2 - this.fontRendererObj.getStringWidth(displayName) / 2, 6, 4210752);
        this.fontRendererObj.drawString("Current: " + storedEnergy + " RF", 6, 20, 4210752);
        this.fontRendererObj.drawString("Max: " + maxEnergy + " RF", 6, 40, 4210752);
        this.fontRendererObj.drawString("Rate: " + energyGenerated + " RF", 6, 60, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Textures.Gui.SOLAR_PANEL);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        int bufferScale = this.tileSolarPanel.getEnergyScaled(76);
        drawTexturedModalRect(xStart + 154, yStart + 80 - bufferScale + 1, 0, 242 - bufferScale, 12, bufferScale);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

}
