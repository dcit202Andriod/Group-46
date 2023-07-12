package com.example.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// This class is the main activity of the quiz app.
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA,ansB,ansC,ansD;
    Button submitBtn;

    // These variables store the total number of questions, the current question index, and the selected answer.
    int score=0;
    int totalQuestion= QuestionAnswer.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer = "";

    // This method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Initialize the UI elements.
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        // Set the click listeners for the buttons.
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
       // Set the total number of questions text view.
        totalQuestionsTextView.setText("Total questions : "+totalQuestion);
        
         // Load the first question.
        loadNewQuestion();

    }

     // This method is called when a button is clicked.
    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        // Get the button that was clicked.
        Button clickedButton = (Button) view;

        // If the submit button was clicked, check the answer and load the next question.
        if (clickedButton.getId()==R.id.submit_btn){
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;  }
            currentQuestionIndex++;
            loadNewQuestion();


        }else{
          // The answer button was clicked.
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }
    }

    // This method loads the next question.
    void loadNewQuestion(){
 
        // If the current question index is the total number of questions, show the finish quiz dialog.
        if (currentQuestionIndex==totalQuestion){
            finishQuiz();
            return;
        }

        // Set the question and answer text views.
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }
    
    // This method shows the finish quiz dialog.
    void finishQuiz(){
        String passStatus = "";
        if (score> totalQuestion*0.6){
            passStatus = "Congratulations";
        }else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of" + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) ->restartQuiz())
                .setCancelable(false)
                .show();
    }
    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
