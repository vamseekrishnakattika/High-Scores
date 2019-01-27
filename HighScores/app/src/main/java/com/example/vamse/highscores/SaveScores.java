/*Written by Vamseekrishna Kattika for CS6326.001, assignment 5,starting October 27, 2018
 * Net ID: vxk165930
 * This class is for save score activity. It validates all the details entered by the user
 * and if all details are valid saves all the scores with the new score in the text file
 */

package com.example.vamse.highscores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SaveScores extends AppCompatActivity {
    private Button save_button;

    EditText editTextName;
    EditText editTextScore;
    EditText editTextDate;

    FileIO io;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_scores);
        /*Title of the activity*/
        setTitle("Save Score");
        io = new FileIO(this);
        editTextDate = findViewById(R.id.date);
        /*Populate the date filed with the current date and time*/
        SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
        editTextDate.setText( sdf.format( new Date() ));
        save_button = (Button) findViewById(R.id.button_save);
        /*Actions to perform when the save button is clicked*/
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                editTextName = findViewById(R.id.name);
                editTextScore = findViewById(R.id.score);
                String name = editTextName.getText().toString();
                String score = editTextScore.getText().toString();
                String date = editTextDate.getText().toString();
                /*If the validations are not correct do related operation*/
                if(!validate(name,score,date)){
                    System.out.println("Fields are not valid");
                }

                else{
                    /*Read the entire data in the file to a String to save the file if any updates are there later*/
                    final StringBuilder file = new StringBuilder();
                    ArrayList<String> totaldata = new ArrayList<>(io.readFile());
                    for(String dataItem: totaldata){
                        file.append(dataItem.toString());
                        file.append("\n");
                    }
                    /*If everything is correct then save the entire scores data into a file*/
                    message = "Successfully added";
                    Toast.makeText(SaveScores.this,message,Toast.LENGTH_SHORT).show();
                    String data = name+"\t"+score+"\t"+date+"\n";
                    file.append(data);
                    io.writeFile(file);
                    /*Give response to show scores avtivity*/
                    backToShowScoresActivity();
                }

            }
        });
    }
    /*This method perform all the validations */
    private boolean validate(String name, String score, String date){
        String message;
        /*Checking mandatory */
        if(name.isEmpty()|| score.isEmpty()|| date.isEmpty()){
            message = "All fields are mandatory";
            Toast.makeText(SaveScores.this,message,Toast.LENGTH_SHORT).show();
            return false;
        }
        /*Checking if the length of the name is more than 30 characters */
        if(name.length()<1 || name.length()>30){
            message = "The name should not be empty and not more than 30 characters";
            Toast.makeText(SaveScores.this,message,Toast.LENGTH_SHORT).show();
            return false;
        }
        /*Checking for a positive score*/
        if(!score.isEmpty()){
            int intScore = Integer.parseInt(score);
            if(intScore<=0){
                message = "Please enter valid score";
                Toast.makeText(SaveScores.this,message,Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        /*Check for the correctness of the date and also check if the date is future data or not*/
        if(!date.isEmpty()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateFormat.setLenient(false);
            Date current = new Date();
            try {
                Date newDate = dateFormat.parse(date.trim());
                int value = current.compareTo(newDate);
                if(value<0){
                    message = "Date and time cannot be future";
                    Toast.makeText(SaveScores.this,message,Toast.LENGTH_SHORT).show();
                    return false;
                }

            } catch (ParseException pe) {
                message = "Please enter valid date and time ";
                Toast.makeText(SaveScores.this,message,Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        return true;
    }
    /*This metod sends result back to the show scores activity*/
    private void backToShowScoresActivity(){
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
