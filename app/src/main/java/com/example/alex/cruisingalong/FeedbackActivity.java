package com.example.alex.cruisingalong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;


public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void sendFeedback(View button) {

        final EditText nameField = (EditText) findViewById(R.id.EditTextName);
        String name = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
        String email = emailField.getText().toString();

        final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
        String feedback = feedbackField.getText().toString();

        final Spinner feedbackSpinner1 = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        String feedbackType = feedbackSpinner1.getSelectedItem().toString();

        final Spinner feedbackSpinner2 = (Spinner) findViewById(R.id.SpinnerFeedbackTour);
        String feedbackTour = feedbackSpinner2.getSelectedItem().toString();

        final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = responseCheckbox.isChecked();

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        float ratingTour = ratingBar.getRating();

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        String emailList[] = { "kurtsweeney7@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailList);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: "+name+"\n Email: "+email+"\n Tour Type: "+feedbackTour+"\n Tourist Type: "+feedbackType+"\n Feedback Details: "+feedback+"\n Tour Rating: "+ratingTour+"\n Do They Want An Email Response: "+bRequiresResponse);

        startActivity(emailIntent);
    }
}