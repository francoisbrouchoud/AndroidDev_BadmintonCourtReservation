package com.example.androiddev_badmintoncourtreservation.ui.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androiddev_badmintoncourtreservation.R;

public class AboutActivity extends AppCompatActivity {

    private ImageView logo;
    private EditText feedback;
    protected SharedPreferences sharedPreferences;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        logo = findViewById(R.id.ivLogo);
        feedback = findViewById(R.id.input_feedback_text);

        //Check the mode (dark, white) to change the logo
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("pref_darkMode", false))
            logo.setImageResource(R.drawable.hesso_neg);
        else
            logo.setImageResource(R.drawable.hesso);

        submitButton = (Button) findViewById(R.id.submit_comment_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackByEmail();
            }
        });
    }

    private void sendFeedbackByEmail() {
        String feedbackText = feedback.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String[] TO = {"luca.delbuono@students.hevs.ch", "francois.brouchoud@hevs.ch" };
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, TO);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback : Court Reservation app");
        intent.putExtra(Intent.EXTRA_TEXT, feedbackText);

        try {
            startActivity(Intent.createChooser(intent, "Send email"));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AboutActivity.this, "No mail client installed !", Toast.LENGTH_SHORT).show();
        }
    }

}