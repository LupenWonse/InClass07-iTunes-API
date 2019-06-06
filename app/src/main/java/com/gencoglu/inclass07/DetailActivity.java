package com.gencoglu.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DetailActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView summaryText;
    private TextView dateText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Podcast podcast = (Podcast) getIntent().getSerializableExtra(MainActivity.PODCAST_KEY);


        titleText = (TextView) findViewById(R.id.textTitle);
        summaryText = (TextView) findViewById(R.id.textSummary);

        titleText.setText(podcast.title);
        summaryText.setText(podcast.summary);

        dateText = (TextView) findViewById(R.id.textUpdated);

        imageView = (ImageView) findViewById(R.id.imagePicture);
        Picasso.with(this).load(podcast.imageURL).into(imageView);


        //2016-09-28T17:29:00-07:00
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZZ").parse(podcast.dateReloaded);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
            String formattedDate = dateFormat.format(date);
            dateText.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
