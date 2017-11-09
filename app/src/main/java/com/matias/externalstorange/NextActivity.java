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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NextActivity extends AppCompatActivity {

    EditText etFilename;
    TextView tvDisplay;
    FileInputStream fis = null;
    String storageType, filename;
    Button btnLoadInternalCache, btnLoadExternalCache, btnLoadExternalStorage, btnLoadExternalPublic;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        etFilename = (EditText) findViewById(R.id.et_filename);
        tvDisplay = (TextView) findViewById(R.id.tv_display);
        btnLoadInternalCache   = (Button) findViewById(R.id.btn_loadInternalCache);
        btnLoadExternalCache   = (Button) findViewById(R.id.btn_loadExternalCache);
        btnLoadExternalStorage = (Button) findViewById(R.id.btn_loadExternalStorage);
        btnLoadExternalPublic  = (Button) findViewById(R.id.btn_loadExternalPublic);
        preference = getSharedPreferences("test", Context.MODE_WORLD_READABLE);
    }

    public void prevActivity (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loadSharedPref(View view) {
        String message = preference.getString("message","");
        tvDisplay.setText(message);
        Toast.makeText(this, "Loaded Successfully", Toast.LENGTH_LONG).show();
    }

    public void loadInternalCache(View view) {
        File dir = getCacheDir();
        filename = etFilename.getText().toString() + ".txt";
        displayMessage(dir, filename);
    }

    public void loadInternalStorage(View view) {
        File dir = getFilesDir();
        filename = etFilename.getText().toString() + ".txt";
        displayMessage(dir, filename);
    }

    public void loadExternalCache(View view) {
        File dir = getExternalCacheDir();
        filename = etFilename.getText().toString() + ".txt";
        displayMessage(dir, filename);
    }

    public void loadExternalStorage(View view) {
        File dir = getExternalFilesDir("Temp");
        filename = etFilename.getText().toString() + ".txt";
        displayMessage(dir, filename);
    }

    public void loadExternalPublic(View view) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        filename = etFilename.getText().toString() + ".txt";
        displayMessage(dir, filename);
    }

    public void displayMessage(File directory, String filename) {
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        try {
            FileInputStream fis = new FileInputStream(new File(directory, filename));
            while((read = fis.read()) != -1) {
                buffer.append((char)read);
            }
            fis.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        tvDisplay.setText(buffer.toString());
        Toast.makeText(this, "Loaded Successfully", Toast.LENGTH_LONG).show();
    }
}
