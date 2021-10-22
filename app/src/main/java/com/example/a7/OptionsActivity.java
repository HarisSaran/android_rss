package com.example.a7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class OptionsActivity extends AppCompatActivity {

    // ------------------------------------------------------
    private View parentView;
    private SwitchMaterial themeSwitch;
    private TextView themeTV, titleTV;

    SharedPreferences sharedPreferences;
//    TextView myAge;
//    TextView myName;


    private UserSettings settings;
    // -------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // -------------------------------------------------------
        settings = (UserSettings) getApplication();
        initWidgets();
        loadSharedPreferences();
        initSwitchListener();
        // -------------------------------------------------------
//        myAge = findViewById(R.id.tv_world_news);
//        myName = findViewById(R.id.tv_sports);
        // -------------------------------------------------------

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        // This should happend when the switch is changed
//        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            String age;
//            String name;
//
//            // TRY CREATING ANOTHER EDITOR
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//           myName = findViewById(R.id.tv_world_news);
//           myAge = findViewById(R.id.tv_sports);
//            try {
//                age = "THIS IS A TEST for sports";
//                name = "THIS IS A TEST for world news";
//            } catch(NumberFormatException nfe){
//                age = "0";
//               name = "You Need a Name";
//            }
//                editor.putString("THIS IS A TEST for sports", age);
//                editor.putString("THIS IS A TEST for world news", name);
//            editor.apply();
//
//            String myAge = sharedPreferences.getString("THIS IS A TEST for sports", "10");
//            String myName = sharedPreferences.getString("THIS IS A TEST for world news","Haris");
//            Toast.makeText(OptionsActivity.this, "Saved age: "+myAge+" "+ "Saved name: "+myName , Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
//                startActivity(intent);
//        }
//            });
    };

    // -------------------------------------------------------
    private void initWidgets(){
        themeTV = findViewById(R.id.themeTV);
        titleTV = findViewById(R.id.titleTV);
        themeSwitch = findViewById(R.id.themeSwitch);
        parentView = findViewById(R.id.parentView);
    }

    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME, UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
    }

    private void initSwitchListener(){
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    settings.setCustomTheme(UserSettings.DARK_THEME);
                else
                    settings.setCustomTheme(UserSettings.LIGHT_THEME);

                SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(UserSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                updateView();

            }
        });
    }

    private void updateView()
    {
        final int black = ContextCompat.getColor(this, R.color.cardview_dark_background);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(UserSettings.DARK_THEME))
        {
            titleTV.setTextColor(white);
            themeTV.setTextColor(white);
            themeTV.setText("Dark");
            parentView.setBackgroundColor(black);
            themeSwitch.setChecked(true);
        }
        else
        {
            titleTV.setTextColor(black);
            themeTV.setTextColor(black);
            themeTV.setText("Light");
            parentView.setBackgroundColor(white);
            themeSwitch.setChecked(false);
        }

    }

    // -------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Up Button (Back)
//        getSupportActionBar().setTitle("ListActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return super.onCreateOptionsMenu(menu);
    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.rocket){
//            // do something   go to options menu
//            Toast.makeText(this, "Keep going", Toast.LENGTH_SHORT).show();
//        } else if (item.getItemId() == R.id.to_options){
//            Toast.makeText(this, "Go to options menu", Toast.LENGTH_SHORT).show();
//            // THIS SHOULD BE DONE WITH SHARED PREFERENCES (3 or 4 different changes should be able to be made..)
//            startActivity(new Intent(this, OptionsActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }
}