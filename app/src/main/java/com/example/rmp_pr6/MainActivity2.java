package com.example.rmp_pr6;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity2 extends Activity implements View.OnClickListener {

    final String LOG_TAG = "Create";
    final String DIR_SD = "SD";
    final String FILENAME_SD = "SDFile";
    final String FILENAME = "TestFile";
    private TextView tvPermission;

    private static final int PERMISSION_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button1 = (Button) findViewById(R.id.buttonWrite);
        button1.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.buttonRead);
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.buttonWriteSD);
        button3.setOnClickListener(this);
        Button button4 = (Button) findViewById(R.id.buttonReadSD);
        button4.setOnClickListener(this);
        tvPermission = findViewById(R.id.tvPermission);
        Button btnPermission = findViewById(R.id.btnPermission);
        if (PermissionUtils.hasPermissions(this)) {
            tvPermission.setText("Разрешение получено");
        } else {
            tvPermission.setText("Разрешение не предоставлено");
        }
        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v){
                if (PermissionUtils.hasPermissions(MainActivity2.this)) return;
                PermissionUtils.requestPermissions(MainActivity2.this, PERMISSION_STORAGE);
            }}
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PERMISSION_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (PermissionUtils.hasPermissions(this)) {
                    tvPermission.setText("Разрешение получено");
                } else {
                    tvPermission.setText("Разрешение не предоставлено");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tvPermission.setText("Разрешение получено");
            } else {
                tvPermission.setText("Разрешение не предоставлено");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonWrite:
                writeFile();
                break;
            case R.id.buttonRead:
                readFile();
                break;
            case R.id.buttonWriteSD:
                writeFileSD();
                break;
            case R.id.buttonReadSD:
                readFileSD();
                break;
            default:
                break;
        }
    }

    void writeFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_PRIVATE )));
            bw.write("какие-то сохранённые данные");
            bw.close();
            Log.d(LOG_TAG,"Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeFileSD(){
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        sdPath.mkdirs();
        File sdFile = new File(sdPath, FILENAME_SD);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            bw.write("какие-то сохранённые данные на SD");
            bw.close();
            Log.d(LOG_TAG,"Файл записан на SD" + sdFile.getAbsolutePath());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFileSD(){
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        File sdFile = new File(sdPath, FILENAME_SD);
        try{
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
