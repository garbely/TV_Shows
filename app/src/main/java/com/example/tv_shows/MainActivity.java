package com.example.tv_shows;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import db.entity.Show;
import db.viewmodel.show.ShowListViewModel;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private List<Show> showList;
    private ShowListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Shows");

        listview = findViewById(R.id.listview);
        showList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ShowListViewModel.Factory factory = new ShowListViewModel.Factory(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ShowListViewModel.class);
        viewModel.getShows().observe(this, showEntities -> {
            if (showEntities != null) {
                showList = showEntities;
                adapter.addAll(showList);
            }
        });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ShowDetails.class);

                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
                );

                intent.putExtra("showName", showList.get(position).getName());
                startActivity(intent);
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
        intent.putExtra("showName", "");
        startActivityForResult(intent, 0);
        return true;
    }
}