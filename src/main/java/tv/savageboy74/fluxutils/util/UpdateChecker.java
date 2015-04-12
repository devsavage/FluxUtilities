package tv.savageboy74.fluxutils.util;

/*
 * UpdateChecker.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    public static void checkForUpdates() throws IOException
    {
        int currentVersion = Reference.update_number;
        int nextVersion = getNewest();

        if (currentVersion < nextVersion)
        {
            Reference.new_version = updatedVersion(nextVersion);
            Reference.isOutdated = true;
        }
        else
        {
            Reference.isOutdated = false;
        }
    }

    //Update Number, Each Update Requires A New Number. PasteBin: Reference: UPDATE_NUMBER
    public static int getNewest() throws IOException
    {
        URL url = new URL("https://raw.githubusercontent.com/savageboy74/FluxUtilities/master/UpdateNumber.txt");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = br.readLine()) != null)
        {
            response.append(line);

        }
        br.close();

        return Integer.parseInt(response.toString());
    }

    //This is the newest version. PasteBin: Reference: NEW_VERSION Keep Updated With The Current Version
    private static String updatedVersion(int version) throws IOException
    {
        URL url = new URL("https://raw.githubusercontent.com/savageboy74/FluxUtilities/master/NewestVersion.txt");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = br.readLine()) != null)
        {
            response.append(line);

        }
        br.close();

        return response.toString();
    }
}
