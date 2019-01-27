/*Written by Vamseekrishna Kattika for CS6326.001, assignment 5,starting October 27, 2018
 * Net ID: vxk165930
 * This handles the file input and output
 */

package com.example.vamse.highscores;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileIO {

    Context context;
    /*Name of the text file to store the scores*/
    private static final String FILE_NAME="scores.txt";
    public FileIO(Context context) {
        this.context = context;
    }
    /*THis method is to read the file line by line and return array list of strings */
    public ArrayList<String> readFile(){
            ArrayList<String> data=new ArrayList<String>();

        FileInputStream is;
        BufferedReader reader;
        String path = context.getFilesDir()+"/"+FILE_NAME;
        final File file = new File(path);
        /*Try catch for checking whether the file exits*/
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                /*Read a line and add each line of the file to an array list*/
                String line = reader.readLine();
                while (line != null) {
                    data.add(line);
                    line = reader.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    /*This method takes a String of the total lines and writes to a file every time*/
    public boolean writeFile(StringBuilder data){
        String path = context.getFilesDir()+"/"+FILE_NAME;
        File file = new File(path);
        /*If file doesn't exist create one*/
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*Write the entire file every time*/
        try {
            FileWriter fw = new FileWriter(file);
            fw.append(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
