package com.example.a7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends AppCompatActivity {
    private TextView tvRandomNumber;
    private Button btnGenerateRandomNumber;
    private ProgressBar mProgressBar;
    public static final int REQUEST_CODE = 33;

//    SharedPreferences sharedPreferences;
//    private TextView nameout;
//    private TextView ageout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnSports = findViewById(R.id.ib_sports);
        btnSports.setOnClickListener(clickToSports);
        ImageButton btnWorldNews = findViewById(R.id.ib_worldNews);
        btnWorldNews.setOnClickListener(clickToWorldNews);
        ImageButton btnCanNews = findViewById(R.id.ib_canNews);
        btnCanNews.setOnClickListener(clickToCanNews);


        // views
        tvRandomNumber = findViewById(R.id.tv_random_number);
        btnGenerateRandomNumber = findViewById(R.id.btn_generate_random_number);
        mProgressBar = findViewById(R.id.pb_progress_loader);
        btnGenerateRandomNumber.setOnClickListener(generateRandomNumber);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    View.OnClickListener clickToSports = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            new DownloadAndParseSportsRSS().execute();
//            startActivity(new Intent(MainActivity.this, ListActivity.class));
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("url","https://www.cbc.ca/cmlink/rss-sports");
            startActivity(intent);
        }
    };
    View.OnClickListener clickToWorldNews = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            new DownloadAndParseWorldRSS().execute();
//            startActivity(new Intent(MainActivity.this, ListActivity.class));
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("url","https://www.cbc.ca/cmlink/rss-world");
            startActivity(intent);
        }
    };
    View.OnClickListener clickToCanNews = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            new DownloadAndParseCanadaRSS().execute();
//            startActivity(new Intent(MainActivity.this, ListActivity.class));
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("url","https://www.cbc.ca/cmlink/rss-canada");
            startActivity(intent);
        }
    };

    // random number onclick listener
    // we can pass perameters through here to run in the background..
    View.OnClickListener generateRandomNumber = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new RunbackgroundTask().execute();
        }
    };

    // async task   ( We can get away with just one, DO IN BACKGROUND)
    // also use  private class RunbackgroundTask extends AsyncTask<URL, Integer, Long>{
    private class RunbackgroundTask extends AsyncTask<Void, Integer, Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // is run before the backgroun/thread starts running
            // has access to main thread...
            tvRandomNumber.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            // executed AFTER our background task is run
            // we can recieve the result if needed.. going out to the network and getting data
            // we get the data here and we can do our stuff here.. also has access to the main thread
            tvRandomNumber.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            tvRandomNumber.setText(Integer.toString(integer));
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            // no run on the main thread and doesnt have access, dont touch UI app will break
            // this was in on click now we do it here
            for (int x = 1; x<=100; x++){
                SystemClock.sleep(5);
               publishProgress(x);
//                Log.d("JN", Integer.toString(x));
            }
            int numbers = (int) (Math.random() * 60);
           return numbers;
//            return (int) (Math.random() * 100);

        }

        // provide feed back to user
        // we have access to main thread and we can provide regular updates..
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            mProgressBar.setProgress(values[0],true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProgressBar.setProgress(values[0],true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.to_options){
            Toast.makeText(this, "Go to options menu", Toast.LENGTH_SHORT).show();
            // THIS SHOULD BE DONE WITH SHARED PREFERENCES (3 or 4 different changes should be able to be made..)
            // set the activity we are waiting to get a result for
            Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }


//
//    // Catch the intent coming back by listening to it
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
////            Toast.makeText(this, data.getStringExtra(MY_NAME), Toast.LENGTH_SHORT).show();
//            nameout =  findViewById(R.id.name);
//            ageout =  findViewById(R.id.age);
//
//            nameout.setText(data.getStringExtra(MY_NAME));
//            ageout.setText(data.getStringExtra(MY_AGE));
//            // do it all in the same if  with the same request code
//        }

@Override
protected void onStart() {
    super.onStart();
//    mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
//    Utils.printStatus(mStatusView, mStatusAllView);
    Log.d("HarisStartA","onStart()");
}

    @Override
    protected void onRestart() {
        super.onRestart();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_restart));
//        Utils.printStatus(mStatusView, mStatusAllView);
        Log.d("HarisRestartA","onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
//        Utils.printStatus(mStatusView, mStatusAllView);
        Log.d("HarisResumeA","onResume()");

//        String myAge = sharedPreferences.getString("THIS IS A TEST for sports", "10");
////            int myAge = sharedPreferences.getInt(getString(R.string.key_my_age), 10);
//        String myName = sharedPreferences.getString("THIS IS A TEST for world news","Haris");
////            Toast.makeText(SaveActivity.this, "Your age: "+Integer.toString(myAge)+" "+"Your name: "+ myName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(MainActivity.this, "Your age: "+myAge+" "+"Your name: "+ myName, Toast.LENGTH_SHORT).show();
//
//        nameout = (TextView) findViewById(R.id.tv_world_news);
//        nameout.setText(myName);
//
//        ageout = (TextView) findViewById(R.id.tv_sports);
//        ageout.setText(myAge);


    }

    @Override
    protected void onPause() {
        super.onPause();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
//        Utils.printStatus(mStatusView, mStatusAllView);
        Log.d("HarisPauseA","onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
        Log.d("HarisStopA","onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));
//        mStatusTracker.clear();
        Log.d("HarisDestroyA","onDestroy()");

    }




}