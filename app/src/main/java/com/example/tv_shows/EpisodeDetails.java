package com.example.tv_shows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EpisodeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_details);
        setTitle("Episode Details");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);

        MenuItem item1 = menu.findItem(R.id.add);
        item1.setVisible(false);
        this.invalidateOptionsMenu();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();

        switch(item.getItemId()){
            case R.id.edit:
                intent = new Intent(EpisodeDetails.this, EpisodeModify.class);
                break;
            case R.id.delete:
                // Delete Episode
                break;
        }
        startActivityForResult(intent, 0);
        return true;
    }

}
