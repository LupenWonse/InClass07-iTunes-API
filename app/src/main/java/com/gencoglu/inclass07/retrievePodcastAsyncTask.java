package com.gencoglu.inclass07;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ahmet on 03/10/2016.
 */

public class retrievePodcastAsyncTask extends AsyncTask<String, Void, ArrayList<Podcast>> {

    private IPodcastListHandler podcastListHandler;

    public retrievePodcastAsyncTask(IPodcastListHandler podcastListHandler) {
        this.podcastListHandler = podcastListHandler;
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {

        try {
            URL url=new URL(params[0]);
            HttpURLConnection connection= null;
            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode =connection.getResponseCode();
                if(statusCode==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=connection.getInputStream();
                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ( (line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line);
                    }

                    return parseJSON(stringBuilder.toString());



                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;

    }

    private ArrayList<Podcast> parseJSON(String s) {

        ArrayList<Podcast> podcastList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(s);
            JSONObject feed = json.getJSONObject("feed");
            JSONArray entries = feed.getJSONArray("entry");

            for (int i = 0; i < entries.length() ; i++) {
                JSONObject title = ((JSONObject) entries.get(i)).getJSONObject("title");
                String label = title.getString("label");

                JSONObject updated = ((JSONObject) entries.get(i)).getJSONObject("im:releaseDate");
                String date = updated.getString("label");

                JSONObject summary = ((JSONObject) entries.get(i)).getJSONObject("summary");
                String summaryString = summary.getString("label");


                JSONArray images = ((JSONObject) entries.get(i)).getJSONArray("im:image");

                int minSize = 0;
                int maxSize = 0;
                String thumbnail = null;
                String image = null;

                for (int j = 0; j < images.length() ; j++){
                    JSONObject imageAttributes =     ((JSONObject) images.get(j)).getJSONObject("attributes");
                    int imageSize = imageAttributes.getInt("height");

                    if (thumbnail == null || imageSize < minSize){
                        thumbnail =  ((JSONObject) images.get(j)).getString("label");
                        minSize = imageSize;
                    }

                    if (image == null || imageSize > maxSize){
                        image = ((JSONObject) images.get(j)).getString("label");
                        maxSize = imageSize;
                    }

                }

                podcastList.add( new Podcast(label,thumbnail,image,date,summaryString,i));

            }

            return podcastList;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Podcast> podcasts) {
        podcastListHandler.podcastListRetrieved(podcasts);
    }
}

interface IPodcastListHandler{
    void podcastListRetrieved(ArrayList<Podcast> podcastList);
}
