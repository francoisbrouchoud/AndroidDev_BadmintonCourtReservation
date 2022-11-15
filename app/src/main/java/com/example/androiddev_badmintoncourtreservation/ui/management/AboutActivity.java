package com.example.androiddev_badmintoncourtreservation.ui.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.androiddev_badmintoncourtreservation.R;

public class AboutActivity extends AppCompatActivity {

    private ImageView logo;
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        logo = findViewById(R.id.ivLogo);

        //Check the mode (dark, white) to change the logo
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("pref_darkMode", false))
            logo.setImageResource(R.drawable.hesso_neg);
        else
            logo.setImageResource(R.drawable.hesso);
    }
}