package ch.brouchoud.androiddev_badmintoncourtreservation.ui.management;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.FeedbackEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.ui.BaseActivity;

public class AboutActivity extends BaseActivity {

    private ImageView logo;
    private EditText feedback;
    protected SharedPreferences sharedPreferences;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about, frameLayout);

        setTitle(getString(R.string.action_about));

        logo = findViewById(R.id.ivLogo);
        feedback = findViewById(R.id.input_feedback_text);

        //Check the mode (dark, white) to change the logo
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("pref_darkMode", false)) {
            logo.setImageResource(R.drawable.hesso_neg);
        }
        else {
            logo.setImageResource(R.drawable.hesso);
        }

        submitButton = findViewById(R.id.submit_comment_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackOnFirebase();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onNavigationItemSelected(item);
    }

    /**
     * Here the feedback are added to the JSON Tree with info about the user device
     */
    private void sendFeedbackOnFirebase() {
        FeedbackEntity feedbackEntity = new FeedbackEntity(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date()), System.getProperty("os.version"), Build.DEVICE, Build.MODEL, feedback.getText().toString() );
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("feedbacks");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("feedbacks")
                .child(key)
                .setValue(feedbackEntity);
        feedback.setText("");
        Toast.makeText(AboutActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

    }

}