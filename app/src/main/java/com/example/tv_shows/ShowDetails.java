package com.example.tv_shows;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import db.entity.Episode;
import db.entity.Show;
import db.viewmodel.episode.EpisodeListViewModel;
import db.viewmodel.show.ShowViewModel;

public class ShowDetails extends AppCompatActivity {

    private Show show;
    private ShowViewModel vmShow;

    private TextView tvShowname;
    private TextView tvNumberEpisodes;
    private TextView tvDescription;

    private ListView listview;
    private List<Episode> episodeList;
    private EpisodeListViewModel vmEpisodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        setTitle("Show Details & Episodes");

        String showName = getIntent().getStringExtra("showName");

        initiateView();

        ShowViewModel.Factory showFac = new ShowViewModel.Factory(getApplication(), showName);
        vmShow = ViewModelProviders.of(this, showFac).get(ShowViewModel.class);
        vmShow.getShow().observe(this, showEntity -> {
            if (showEntity != null) {
                show = showEntity;
                updateContent();
            }
        });

    /*
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
    */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();

        switch (item.getItemId()) {
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

    private void initiateView() {
        tvShowname = findViewById(R.id.name);
        tvNumberEpisodes = findViewById(R.id.episodes);
        tvDescription = findViewById(R.id.description);
        listview = findViewById(R.id.listview);
    }

    private void updateContent() {
        if (show != null) {
            tvShowname.setText(show.getName());
            tvNumberEpisodes.setText(show.getNumberEpisodes() + " episodes");
            tvDescription.setText(show.getDescription());
            createEpisodeList();
        }
    }

    private void createEpisodeList() {
        episodeList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        EpisodeListViewModel.Factory episodesFac = new EpisodeListViewModel.Factory(getApplication(), show.getName());
        vmEpisodeList = ViewModelProviders.of(this, episodesFac).get(EpisodeListViewModel.class);
        vmEpisodeList.getEpisodes().observe(this, episodeEntities -> {
            if (episodeEntities != null) {
                episodeList = episodeEntities;
                adapter.addAll(episodeList);
                setListViewHeightBasedOnChildren(listview); // To stretch the listView dynamically, so it's not only showing the first object in the listview
            }
        });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EpisodeDetails.class);
        /*
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
                );
        */
                intent.putExtra("episodeName", episodeList.get(position).getName());
                startActivity(intent);
            }
        });
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
