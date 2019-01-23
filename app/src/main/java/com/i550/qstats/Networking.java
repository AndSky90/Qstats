package com.i550.qstats;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Networking {
    private static final String TAG = "qStatsNetwork";

    private String getUrlString(String urlQuake) throws IOException {
        URL url = new URL(urlQuake);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();                   //++++++++
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlQuake);
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) out.write(buffer, 0, bytesRead);
            out.close();
            return new String(out.toByteArray());
        } finally {
            connection.disconnect();
        }
    }

    public String fetchJSONFromURL(String path) { //получает URL, отдает JSON
        String jsonString = null;
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            url.build();
            jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch JSON from URL", ioe);
        }
        return jsonString;
    }

}

