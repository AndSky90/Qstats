package com.i550.qstats;

import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/*
____________________________________________________________________________________________________
*/
 class NetQstatsWork {
    private static final String TAG = "qStats";





     void fetchDataGlobal(String path) {
         DataGlobal dataGlobal = new DataGlobal();
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            url.build();
            String jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            dataGlobal = gson.fromJson(jsonString,DataGlobal.class);
            Log.i(TAG, "Received Object: " + dataGlobal );
            Log.i(TAG, "String Object: " + dataGlobal.getTotal_championusage() +"\n" );
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
         MyViewModel.setDataGlobal(dataGlobal);
    }



  void fetchTDMLeads(String path) {
    }

  void fetchDuelLeads(String path) {
    }

  void fetchPlayerStats(String path) {
    }

  void fetchPlayerSummary(String path, String value) {
          DataGlobal dataGlobal = new DataGlobal();
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            if (value!=null) url.appendQueryParameter("name", value);
            url.build();

            String jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            dataGlobal = gson.fromJson(jsonString,DataGlobal.class);
            Log.i(TAG, "Received Object: " + dataGlobal );
            Log.i(TAG, "String Object: " + dataGlobal.getTotal_championusage() +"\n" );
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
         MyViewModel.setDataGlobal(dataGlobal);
    }



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
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            //  return out.toByteArray();
            return new String(out.toByteArray());
        } finally {
            connection.disconnect();
        }
    }



}
