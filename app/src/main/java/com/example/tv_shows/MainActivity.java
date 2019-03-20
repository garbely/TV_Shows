package com.example.tv_shows;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import db.AppDatabase;
import db.dao.ShowDao;
import db.entity.Show;

public class MainActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);

        String[] shows = getResources().getStringArray(R.array.shows_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shows);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(view.getContext(), ShowDetails.class);
                startActivityForResult(myintent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);

        MenuItem item1 = menu.findItem(R.id.delete);
        item1.setVisible(false);
        this.invalidateOptionsMenu();

        MenuItem item2 = menu.findItem(R.id.edit);
        item2.setVisible(false);
        this.invalidateOptionsMenu();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ShowModify.class);
        startActivityForResult(intent, 0);
        return true;
    }
}