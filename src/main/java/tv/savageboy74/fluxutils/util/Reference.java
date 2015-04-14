package tv.savageboy74.fluxutils.util;

/*
 * Reference.java
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

public class Reference
{
    public static final String mod_id = "fluxutils";
    public static final String mod_name = "FluxUtilities";
    public static final String mod_version = "1.7.10-0.0.1";
    public static final String mc_version = "1.7.10";
    public static final String dependencies = "required-after:CoFHCore@[1.7.10R3.0.0RC7,)";

    public static final String clientProxy = "tv.savageboy74.fluxutils.common.proxy.ClientProxy";
    public static final String serverProxy = "tv.savageboy74.fluxutils.common.proxy.ServerProxy";

    //Update Checking
    public static final int update_number = 1;
    public static String current_version = mod_version;
    public static String new_version = "";
    public static String updates = "";
    public static boolean isOutdated = false;

}
