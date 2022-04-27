package com.example.rmp_pr6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SAVED_TEXT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonSave = (Button) findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(this);
        Button buttonLoad = (Button) findViewById(R.id.btnLoad);
        buttonLoad.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                textSave();
                break;
            case R.id.btnLoad:
                textLoad();
                break;
            default:
                break;
        }
    }

    private void textSave(){
        EditText editText = findViewById(R.id.editText);
        SharedPreferences shPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = shPref.edit();
        editor.putString(SAVED_TEXT, editText.getText().toString());
        editor.commit();
        Toast.makeText(MainActivity.this, "Текст сохранён", Toast.LENGTH_SHORT).show();
    }

    private void textLoad(){
        EditText editText = findViewById(R.id.editText);
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        editText.setText(savedText);
        Toast.makeText(MainActivity.this, "Текст загружен", Toast.LENGTH_SHORT).show();
    }

}