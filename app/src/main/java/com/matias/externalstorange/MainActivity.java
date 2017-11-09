package com.matias.externalstorange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etMessage, etFilename;
    Button btnSaveInternalCache, btnSaveExternalCache, btnSaveExternalStorage, btnSaveExternalPublic;
    String filename;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMessage  = (EditText) findViewById(R.id.et_msg);
        etFilename = (EditText) findViewById(R.id.et_filename);
        preference = getSharedPreferences("test", Context.MODE_WORLD_READABLE);
    }

    public void saveSharedPref(View view) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("message",etMessage.getText().toString());
        editor.commit();
        Toast.makeText(this, "Saved in shared preferences",Toast.LENGTH_LONG).show();
    }

    public void saveInternalCache(View view) {
        File folder = getCacheDir();
        filename = etFilename.getText().toString() + ".txt";
        //File file = new File(folder, "data1.txt");
        File file = new File(folder, filename);
        String message = etMessage.getText().toString();
        String toastText = "Saved as " + filename + " to internal cache";
        writeData(file, message, toastText);
    }

    public void saveInternalStorage(View view) {
        File folder = getFilesDir();
        filename = etFilename.getText().toString() + ".txt";
        //File file = new File(folder, "data1.txt");
        File file = new File(folder, filename);
        String message = etMessage.getText().toString();
        String toastText = "Saved as " + filename + " to internal storage";
        writeData(file, message, toastText);
    }

    public void saveExternalCache(View view) {
        File folder = getExternalCacheDir();
        filename = etFilename.getText().toString() + ".txt";
        //File file = new File(folder, "data2.txt");
        File file = new File(folder, filename);
        String message = etMessage.getText().toString();
        String toastText = "Saved as " + filename + " to external cache";
        writeData(file, message, toastText);
    }

    public void saveExternalStorage(View view) {
        File folder = getExternalFilesDir("Temp");
        filename = etFilename.getText().toString() + ".txt";
        //File file = new File(folder, "data3.txt");
        File file = new File(folder, filename);
        String message = etMessage.getText().toString();
        String toastText = "Saved as " + filename + " to external storage";
        writeData(file, message, toastText);
    }

    public void saveExternalPublic(View view) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        filename = etFilename.getText().toString() + ".txt";
        //File file = new File(folder, "data4.txt");
        File file = new File(folder, filename);
        String message = etMessage.getText().toString();
        String toastText = "Saved as " + filename + " to external public storage";
        writeData(file, message, toastText);

    }

    public void nextActivity (View view){
        Intent intent = new Intent(this, NextActivity.class);
        startActivity(intent);
    }

    public void writeData(File file, String message, String type) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(message.getBytes());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, type, Toast.LENGTH_LONG).show();
    }
}
