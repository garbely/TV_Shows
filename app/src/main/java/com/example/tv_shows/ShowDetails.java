package com.example.tv_shows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ShowDetails extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        setTitle("Show Details & Episodes");

        listview = (ListView) findViewById(R.id.listview);

        String [] episodes = getResources().getStringArray(R.array.episodes_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, episodes);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener (new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(view.getContext(), EpisodeDetails.class);
                startActivityForResult(myintent, 0);
            }
        });

        setListViewHeightBasedOnChildren(listview); // To stretch the listView dynamically, so it's not only showing the first object in the listview
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();

        switch(item.getItemId()){
            case R.id.add:
                intent = new Intent(ShowDetails.this, EpisodeModify.class);
                break;
            case R.id.edit:
                intent = new Intent(ShowDetails.this, ShowModify.class);
                break;
            case R.id.delete:
                // Delete Show
                break;
        }
        startActivityForResult(intent, 0);
        return true;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
