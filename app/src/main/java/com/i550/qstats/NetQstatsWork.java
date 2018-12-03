package com.i550.qstats;

import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.i550.qstats.Model.DataGlobal;
import com.i550.qstats.Model.LeaderBoard;
import com.i550.qstats.Model.PlayerStats;
import com.i550.qstats.Model.PlayerSummary;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

 class NetQstatsWork {
     private static final String TAG = "qStats";

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

     void fetchDataGlobal(String path) {
         DataGlobal data = new DataGlobal();
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            url.build();
            String jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            data = gson.fromJson(jsonString,DataGlobal.class);
            Log.i(TAG, "String Object global: " + data.getTotal_championusage() +"\n" );
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
         MyViewModel.setDataGlobal(data);
    }

     void fetchLeaderBoard(String path, Boolean mode) {

        LeaderBoard data = new LeaderBoard();
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            url.build();
            String jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            data = gson.fromJson(jsonString,LeaderBoard.class);
            Log.i(TAG, "String Object tdm: " + data.toString() );
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        if(mode) MyViewModel.setDuelLeads(data);
          else   MyViewModel.setTDMLeads(data);
    }

     void fetchPlayerSummary(String path, String profileName) {
          PlayerSummary data = new PlayerSummary();
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            if (profileName!=null) url.appendQueryParameter("name", profileName);
            url.build();

            String jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            data = gson.fromJson(jsonString,PlayerSummary.class);
            Log.i(TAG, "String Object summary: "  + data.toString()  );
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
         MyViewModel.setPlayerSummary(data);
    }

     void fetchPlayerStats(String path, String profileName) {
        PlayerStats data = new PlayerStats();
        try {
            Uri.Builder url = Uri.parse(path).buildUpon();
            if (profileName!=null) url.appendQueryParameter("name", profileName);
            url.build();

            String jsonString = getUrlString(url.toString());
            Log.i(TAG, "Received JSON: " + jsonString);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            data = gson.fromJson(jsonString,PlayerStats.class);
            Log.i(TAG, "String Object stats: "  + data.toString()  );
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        MyViewModel.setPlayerStats(data);
    }

     // TODO void fetchNames(String path, String value) {}
}
