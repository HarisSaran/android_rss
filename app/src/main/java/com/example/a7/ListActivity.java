package com.example.a7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ListActivity extends AppCompatActivity {
    List<News> newsItems = new ArrayList<>();
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("url");
        message = bundle.getString("url");



        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ListView mListView = findViewById(R.id.lv_main_list_view);

//        NewsListAdapter newsListAdapter = new NewsListAdapter(this, R.layout.news_layout, News.generateNews());
        NewsListAdapter newsListAdapter = new NewsListAdapter(this, R.layout.news_layout, newsItems);
        mListView.setAdapter(newsListAdapter);

        new DownloadAndParseSportsRSS().execute(message);
//        new DownloadAndParseSportsRSS().execute();

        //--------------------------------------------------------------------------------------------------
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Create and pass the INTENT
                Intent in = new Intent(getApplicationContext(), NewsItem.class);
                String title = ((TextView) view.findViewById(R.id.tv_title)).getText().toString().trim();
                String date = ((TextView) view.findViewById(R.id.tv_year)).getText().toString().trim();
                String description = ((TextView) view.findViewById(R.id.tv_description)).getText().toString().trim();

                in.putExtra("ttl", title);
                in.putExtra("date", date);
                in.putExtra("description", description);
                startActivity(in);
            }
        });

//        https://www.journaldev.com/20126/android-rss-feed-app
        //--------------------------------------------------------------------------------------------------


    }

    //     async task   ( We can get away with just one, DO IN BACKGROUND)
//     for assignment grab the RSS feed
//     THIS SHOULD BE DIFFERENT
    private class DownloadAndParseSportsRSS extends AsyncTask<String, Void, List<News>>{

        @Override
        protected List<News> doInBackground(String... urls) {
            // get the data here..
            // ON PRE EXECUTE AND ON POST EXECUTE may be needed...
//            String sportsRSS = "https://www.cbc.ca/cmlink/rss-sports";
            String sportsRSS = urls[0];
            URL rssSportsURL = null;
            HttpsURLConnection connectionSport = null;
            InputStream inputStream = null;
            try {
                rssSportsURL = new URL(sportsRSS);
                connectionSport = (HttpsURLConnection) rssSportsURL.openConnection();
                inputStream = connectionSport.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SAXParserFactory spf = SAXParserFactory.newInstance();
            try {
                SAXParser saxParser = spf.newSAXParser();
                RSSParseHandler rssParseHandler = new RSSParseHandler();
                saxParser.parse(inputStream, rssParseHandler);
//                ArrayList<News> newsItems = new ArrayList<>();
                newsItems = rssParseHandler.getItems();

                Log.d("NewsItem", newsItems.get(0).getTitle());

            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<News> news) {
            super.onPostExecute(news);
            // this is where we create a new activity and pass it data!
            // CREATE THE LIST VIEW HERE....
            ListView lvListView = findViewById(R.id.lv_main_list_view);
            NewsListAdapter adapter = new NewsListAdapter(ListActivity.this,R.layout.news_layout,newsItems);
            lvListView.setAdapter(adapter);
//            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);

        // Up Button (Back)
//        getSupportActionBar().setTitle("ListActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.to_refresh){
            // do something   go to options menu
            Toast.makeText(this, "You refreshed the page!", Toast.LENGTH_SHORT).show();
            new DownloadAndParseSportsRSS().execute(message);
        }

        return super.onOptionsItemSelected(item);
    }

// on click send the message and start the new NewsItem View



}