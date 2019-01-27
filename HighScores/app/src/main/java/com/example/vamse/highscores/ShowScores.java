/*Written by Vamseekrishna Kattika for CS6326.001, assignment 5,starting October 27, 2018
 * Net ID: vxk165930
 * This class is the activity for showing scores
 * When the Add button is pressed it goes to another activity to save a score and based the result from the other class it displays the results
 */


package com.example.vamse.highscores;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowScores extends AppCompatActivity {
    ListView listView;
    private Button add_button;
    FileIO io;
    static final int rCode = 1;
    /*On creation of the activity display the results from the text file into a list view*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Title of the activity */
        setTitle("High Scores");
        setContentView(R.layout.activity_show_scores);
        listView =  findViewById(R.id.listView);
        add_button = findViewById(R.id.button_add);
        displayResults();
        /*When add button is clicked open save scores activity*/
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSaveActivity();
            }
        });

    }

    /*This method is to fetch the scores from a text file and display them in a listview */
    public void displayResults(){

        io = new FileIO(this);
        ArrayList<String> data = new ArrayList<>(io.readFile());
        ArrayList<Score> scoreArrayList = new ArrayList<Score>();

        for(String dataItem: data){
            String[] values = dataItem.split("\t");
            Score newScore = new Score(values[0],values[1],values[2]);
            scoreArrayList.add(newScore);
        }
        ScoreSorter scoreSorter = new ScoreSorter(scoreArrayList);
        ArrayList<Score> scoreArrayListSorted  = scoreSorter.getSortedOutput();
        StableArrayAdapter scoreListAdapter = new StableArrayAdapter(this,R.layout.list_adapter_view,scoreArrayListSorted);
        listView.setAdapter(scoreListAdapter);

    }
    /*Method for starting save score activity*/
    public void openSaveActivity(){

       Intent intent = new Intent(this,SaveScores.class);
       startActivityForResult(intent, rCode);
    }
    /*Based on the request code and result from the save score activity perform related operation*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==rCode){
            /*If there is any new score added then update the listview with the new scores*/
            if(resultCode==RESULT_OK){
                displayResults();
            }
            if(resultCode==RESULT_CANCELED){
                /*If nothing is added in save score activity then display a message that nothing was added*/
                String message = "Nothing added";
                Toast.makeText(ShowScores.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
