package tv.savageboy74.fluxutils.util;

/*
 * Textures.java
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

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.mod_id.toLowerCase() + ":";

    public static final class Gui
    {
        private static final String GUI_SHEET_LOCATION = "textures/gui/";
        public static final ResourceLocation SOLAR_PANEL = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "GuiSolarPanel.png");
    }

    public static class ResourceLocationHelper
    {
        public static ResourceLocation getResourceLocation(String modId, String path)
        {
            return new ResourceLocation(modId, path);
        }

        public static ResourceLocation getResourceLocation(String path)
        {
            return getResourceLocation(Reference.mod_id.toLowerCase(), path);
        }
    }
}
