package com.webpromo.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView post_by, post_title, post_date, post_content;
    ImageView post_image;
    String spost_by [], spost_title [], spost_date [], spost_content [],
            spost_image [];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        post_by=findViewById(R.id.post_by);
        post_content=findViewById(R.id.post_content);
        post_date=findViewById(R.id.post_date);
        post_title=findViewById(R.id.post_title);
        post_image=findViewById(R.id.post_image);
        //to get the data using intent
        /*Intent intent=getIntent();
        spost_by = intent.getStringArrayExtra("post_by");
        int position = intent.getIntExtra("position", 0);
        //spost_content = intent.getStringArrayExtra("post_content");
        spost_date = intent.getStringArrayExtra("post_date");
        spost_image = intent.getStringArrayExtra("post_image");
        spost_title = intent.getStringArrayExtra("post_title");*/

        //to get the large data using intent parceable
        Intent intent = getIntent();
        Mydata mydata = intent.getParcelableExtra("news data");
        Picasso.with(this).load(mydata.getImg_link()).into(post_image);
        post_title.setText(mydata.getPost_title());
        post_date.setText(mydata.getPost_date());
        post_content.setText(mydata.getPost_content());
        post_by.setText(mydata.getPost_author());

    }
}
