package com.example.myquizz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myquizz.R;
import com.example.myquizz.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGrettingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User user;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 10;
    private SharedPreferences preferences;

    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            preferences.edit().putInt(PREF_KEY_SCORE, score).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();

        preferences = getPreferences(MODE_PRIVATE);

        mGrettingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_imput);
        mPlayButton = findViewById(R.id.activity_main_play_btn);

        mPlayButton.setEnabled(false);

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = mNameInput.getText().toString();
                user.setFirstName(firstname);

                preferences.edit().putString(PREF_KEY_FIRSTNAME, user.getFirstName()).apply();

                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                // Retrieve a variable in the next activity
                Bundle bundle = new Bundle();
              //  bundle.putString("firstname",firstname);
                gameActivityIntent.putExtra("name",firstname);
                startActivityForResult(gameActivityIntent,GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }

}
