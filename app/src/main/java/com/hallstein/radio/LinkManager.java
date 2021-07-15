package com.hallstein.radio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LinkManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText == null) return;
        Pattern p = Pattern.compile("http[s]?://.*\\.spotify\\.com/(track|artist|album)/.+\\?.*");
        if (!p.matcher(sharedText).matches()) {
            showError();
            return;
        }
        Uri url = Uri.parse(sharedText);
        Intent spotify = new Intent(Intent.ACTION_VIEW);
        spotify.setData(Uri.parse("spotify:station"+url.getPath().replace("/",":")));
        startActivity(spotify);
        System.exit(0);
    }

    void showError(){
        CharSequence error = "Input string error. Please contact the developer.";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, error, duration);
        toast.show();
    }
}