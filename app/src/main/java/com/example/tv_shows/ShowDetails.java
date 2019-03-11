package com.example.tv_shows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowDetails extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details2);

        listview = (ListView) findViewById(R.id.listview);

        String [] episodes = getResources().getStringArray(R.array.episodes_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, episodes);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener (new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(view.getContext(), ShowModify.class);
                startActivityForResult(myintent, 0);
            }
        });


    }






}
