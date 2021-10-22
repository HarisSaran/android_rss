package com.example.a7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsItem extends AppCompatActivity {
    String titleIn;
    String dateIn;
    String descriptionIn;
    Date myDate;
    String myString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);

        // get the intent and assign it to the wanted views..
        Intent in = getIntent();
        titleIn = in.getStringExtra("ttl");
        dateIn = in.getStringExtra("date");
        descriptionIn = in.getStringExtra("description");

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TextView title = findViewById(R.id.t_v_title);
        title.setText(titleIn);



        TextView date = findViewById(R.id.t_v_date);
        date.setText(dateIn);

        TextView description = findViewById(R.id.t_v_description);
//        description.setText(descriptionIn);
        description.setText(getDescription());

    }

    public String getDescription() {
        if (descriptionIn.startsWith("<img ")) {
            String cleanUrl = descriptionIn.substring(descriptionIn.indexOf("<p>") + 3, descriptionIn.indexOf("/p") - 2);
            return cleanUrl;
        } else {
            return descriptionIn;
        }
    }


//    September 23, 2021
//SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }
}