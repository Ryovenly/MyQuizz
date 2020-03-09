package com.example.myquizz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizz.R;
import com.example.myquizz.model.Question;
import com.example.myquizz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView gQuestionTextView;
    private Button gAnswer1Button;
    private Button gAnswer2Button;
    private Button gAnswer3Button;
    private Button gAnswer4Button;

    private QuestionBank questionBank;
    private Question currentQuestion;

    private int numberOfQueestions;
    private int score;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private boolean enableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Bundle extras = getIntent().getExtras();
        final String firstName = extras.getString("firstName");

        questionBank = this.generateQuestions();

        numberOfQueestions = 10;
        score = 0;
        enableTouchEvents = true;

        // Widgets
        gQuestionTextView = findViewById(R.id.activity_game_question_text);
        gAnswer1Button = findViewById(R.id.activity_game_answer1_btn);
        gAnswer2Button = findViewById(R.id.activity_game_answer2_btn);
        gAnswer3Button = findViewById(R.id.activity_game_answer3_btn);
        gAnswer4Button = findViewById(R.id.activity_game_answer4_btn);

        // tag button

        gAnswer1Button.setTag(0);
        gAnswer2Button.setTag(1);
        gAnswer3Button.setTag(2);
        gAnswer4Button.setTag(3);

        gAnswer1Button.setOnClickListener(this);
        gAnswer2Button.setOnClickListener(this);
        gAnswer3Button.setOnClickListener(this);
        gAnswer4Button.setOnClickListener(this);


        currentQuestion = questionBank.getQuestion();
        this.displayQuestion(currentQuestion);
    }


    private void displayQuestion(final Question question) {
        gQuestionTextView.setText(question.getQuestion());
        gAnswer1Button.setText(question.getChoiceList().get(0));
        gAnswer2Button.setText(question.getChoiceList().get(1));
        gAnswer3Button.setText(question.getChoiceList().get(2));
        gAnswer4Button.setText(question.getChoiceList().get(3));
    }


    private QuestionBank generateQuestions(){


        Question question1 = new Question("Quel est la date d'anniversaire du créateur?",
                Arrays.asList("12 Avril", "5 Mai", "10 Mars", "30 Février"), 2);

        Question question2 = new Question("Comment s'appelle le créateur?",
                Arrays.asList("Jean", "Steven", "Alexander", "Paul"), 1);

        Question question3 = new Question("Dans quel domaine le créateur étudie t-il?",
                Arrays.asList("Informatique", "Cuisine", "Science", "Finance"), 0);

        Question question4 = new Question("Dans quel pays séjourne le créateur?",
                Arrays.asList("Angleterre", "Espagne", "Etats-Unis", "France"), 3);

        Question question5 = new Question("Quel est le le péché mignon du créateur?",
                Arrays.asList("Bonbon","Barbe à papa","Chocolat", "Banane"),2);

        Question question6 = new Question("Quel est l'animé préféré du créateur?",
                Arrays.asList("Black Clover", "Dragon Ball", "Naruto", "L'auteur n'a pas de préférence"),3);

        Question question7 = new Question("Quel est le manga préféré du créateur quand il était enfant",
                Arrays.asList("Olivier et Tom", "Shin Chan", "City Hunter", "Ken le survivant"), 1);

        Question question8 = new Question("De quel origine est le créateur?",
                Arrays.asList("Hmong","Français","Thailandais","Martien"),0);

        Question question9= new Question("Quel est le jeu de l'enfance du créateur?",
                Arrays.asList("Tekken","Dynasty Warrior", "Kingdom Hearts", "Spyro"),2);

        Question question10 = new Question("Dans quelle ville le créateur a t-il le plus vécu?",
                Arrays.asList("Argent sur Sauldre", "Paris", "Belleville sur Saône", "Nîmes"),2);


        return new QuestionBank(Arrays.asList(

                question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10
        ));

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return enableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == currentQuestion.getAnswerIndex()) {
            // Good Answer
            Toast.makeText(this,"Bonne réponse !",Toast.LENGTH_SHORT).show();
            score++;
        } else{
            // Bad Answer
            Toast.makeText(this,"Mauvaise réponse !",Toast.LENGTH_SHORT).show();
        }
        enableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enableTouchEvents = true;
                // If this is the last question, end the game.
                // Else, display the next question
                if (--numberOfQueestions == 0) {
                    // No questions left so end the game
                    endGame();
                } else {
                    currentQuestion = questionBank.getQuestion();
                    displayQuestion(currentQuestion);
                }
            }
        }, 2000); // delay


    }
    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Bundle extras = getIntent().getExtras();
        final String firstName = extras.getString("name");


        if (score <= 3) {

            builder.setTitle("Tu ne connais vraiment pas le créateur :( essaye encore")
                    .setMessage(firstName + ", ton score est de " + score + " points, ce n'est pas terrible...")
                    .setPositiveButton("Fin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, score);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else if (score > 3 && score <= 5) {
            builder.setTitle("Mouais mais peut mieux faire")
                    .setMessage(firstName + ", ton score est de " + score + " points, c'est juste mouais'..")
                    .setPositiveButton("Fin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, score);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else if (score > 5 && score <= 8) {
            builder.setTitle("Pas mal tu connais un peu le créateur !")
                    .setMessage(firstName + " Ton score est de " + score + " points, c'est pas mal !")
                    .setPositiveButton("Fin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, score);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else {
            builder.setTitle("Tu connais vraiment bien le créateur super !")
                    .setMessage(firstName + ", ton score est de " + score + " points, c'est très bien !")
                    .setPositiveButton("Fin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, score);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }

    }
}
