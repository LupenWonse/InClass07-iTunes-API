package com.gencoglu.inclass07;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements IPodcastListHandler, Comparator<Podcast> {

    public static String PODCAST_KEY = "PODCAST";

    private EditText searchText;

    private ArrayList<Podcast> podcasts = new ArrayList<>();
    private ListView listView;
    private PodcastAdapter podcastAdapter;
    private ProgressDialog progressDialog;


    @Override
    public int compare(Podcast lhs, Podcast rhs) {
        if(lhs.isHit && rhs.isHit){
            return -1;
        }  else if(lhs.isHit) {
            return -1;
        } else if (rhs.isHit) {
            return 1;
        } else {
            if (lhs.position < rhs.position){
                return -1;
            } else {
                return 1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.LoadingProgressText));
        progressDialog.show();

        searchText = (EditText) findViewById(R.id.editTextSearch);
        listView = (ListView) findViewById(R.id.listView);

        new retrievePodcastAsyncTask(this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/json");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Podcast podcast = podcasts.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(PODCAST_KEY,podcast);

                startActivity(intent);

            }
        });
    }

    @Override
    public void podcastListRetrieved(ArrayList<Podcast> podcastList) {
        progressDialog.dismiss();

        this.podcasts = podcastList;
        podcastAdapter = new PodcastAdapter(this, R.layout.podcast_row_layout, podcasts);
        listView.setAdapter(podcastAdapter);
    }

    public void searchForText(View view){
        String textToSearch = searchText.getText().toString();
        resetSearch(null);
        for (Podcast currentPodcast : podcasts) {
            if( currentPodcast.title.matches(".*"+textToSearch+".*")){
                currentPodcast.setHit(true);
            }
        }

        Collections.sort(podcasts,this);
        podcastAdapter.notifyDataSetChanged();
    }

    public void resetSearch(View view){
        for (Podcast currentPodcast : podcasts) {
            currentPodcast.setHit(false);
        }
        searchText.setText(null);
        Collections.sort(podcasts,this);
        podcastAdapter.notifyDataSetChanged();
    }

}
