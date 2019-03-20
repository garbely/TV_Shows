package com.example.tv_shows;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import db.AppDatabase;
import db.entity.Show;

public class ShowModify extends AppCompatActivity {

    String name;
    String description;
    int number;

    EditText editText1;
    EditText editText2;
    EditText editText3;

    Button button;

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_modify);

        editText1 = (EditText)findViewById(R.id.name);
        editText2 = (EditText)findViewById(R.id.description);
        editText3 = (EditText)findViewById(R.id.number);
        button = (Button)findViewById(R.id.save);

        if (editText1.getText().toString().equals("")) {
            setTitle("Add show");
        }
        else {
            setTitle("Edit show");
        }

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "show-database").allowMainThreadQueries().build();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                name = editText1.getText().toString();
                description = editText2.getText().toString();
                number = Integer.valueOf(editText3.getText().toString());

                Show show = new Show(name, description, number);
                appDatabase.showDao().insertNewShow(show);
            }
        });
    }
}
