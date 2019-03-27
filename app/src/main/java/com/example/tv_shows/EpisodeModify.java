package com.example.tv_shows;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import db.entity.Episode;
import db.util.OnAsyncEventListener;
import db.viewmodel.episode.EpisodeViewModel;

public class EpisodeModify extends AppCompatActivity {

    private static final String TAG = "EpisodeModify";

    private String showName;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button button;

    private boolean isEditMode;

    private EpisodeViewModel viewModel;
    private Episode episode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_episode_modify);

        showName = getIntent().getStringExtra("showName");

        editText1 = (EditText) findViewById(R.id.name);
        editText2 = (EditText) findViewById(R.id.number);
        editText3 = (EditText) findViewById(R.id.length);
        button = (Button) findViewById(R.id.save);

        editText1.addTextChangedListener(loginTextWatcher);
        editText2.addTextChangedListener(loginTextWatcher);
        editText3.addTextChangedListener(loginTextWatcher);

        int idEpisode = getIntent().getIntExtra("idEpisode", -1);

        if (idEpisode == -1) {
            setTitle("Add Episode");
            isEditMode = false;
        } else {
            setTitle("Edit Episode");
            button.setText("Save Changes");
            isEditMode = true;
        }

        EpisodeViewModel.Factory factory = new EpisodeViewModel.Factory(getApplication(), idEpisode);
        viewModel = ViewModelProviders.of(this, factory).get(EpisodeViewModel.class);

        if (isEditMode) {
            viewModel.getEpisode().observe(this, episodeEntity -> {
                if (episodeEntity != null) {
                    episode = episodeEntity;
                    editText1.setText(episode.getName());
                    editText2.setText(String.valueOf(episode.getNumber()));
                    editText3.setText(String.valueOf(episode.getLength()));
                }
            });
        }
        button.setOnClickListener(view -> {
            saveChanges(editText1.getText().toString(),
                    Integer.valueOf(editText2.getText().toString()),
                    Integer.valueOf(editText3.getText().toString())
            );
            onBackPressed();
        });
    }

    private void saveChanges(String name, int number, int length) {

        Intent intent = getIntent();
        intent = new Intent(EpisodeModify.this, ShowDetails.class);

        if (isEditMode) {
            if (!"".equals(name)) {
                episode.setName(name);
                episode.setNumber(number);
                episode.setLength(length);
                viewModel.updateEpisode(episode, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "update Episode: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "update Episode: failure", e);
                    }
                });
                intent.putExtra("showName", episode.getShowName());
            }
        } else {
            Episode newEpisode = new Episode(number, name, length, showName);
            viewModel.createEpisode(newEpisode, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "create Episode: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create Episode: failure", e);
                }
            });
            intent.putExtra("showName", showName);
        }
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
        );
        startActivity(intent);
    }


    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = editText1.getText().toString().trim();
            String numberInput = editText2.getText().toString().trim();
            String lenghtInput = editText3.getText().toString().trim();

            button.setEnabled(!nameInput.isEmpty() && !numberInput.isEmpty() && !lenghtInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}