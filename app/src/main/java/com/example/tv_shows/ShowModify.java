package com.example.tv_shows;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import db.AppDatabase;
import db.entity.Show;
import db.util.OnAsyncEventListener;
import db.viewmodel.show.ShowViewModel;

public class ShowModify extends AppCompatActivity {

    private static final String TAG = "ShowModify";

    private String showName;
    private boolean isEditMode;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button button;

    private ShowViewModel viewModel;
    private Show show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_modify);

        showName = getIntent().getStringExtra("showName");

        editText1 = (EditText) findViewById(R.id.name);
        editText2 = (EditText) findViewById(R.id.description);
        editText3 = (EditText) findViewById(R.id.number);
        button = (Button) findViewById(R.id.save);

        if (showName.equals("")) {
            setTitle("Add Show");
            isEditMode = false;
        } else {
            setTitle("Edit Show");
            button.setText("Save Changes");
            isEditMode = true;
        }

        ShowViewModel.Factory factory = new ShowViewModel.Factory(getApplication(), showName);
        viewModel = ViewModelProviders.of(this, factory).get(ShowViewModel.class);

        if (isEditMode) {
            viewModel.getShow().observe(this, showEntity -> {
                if (showEntity != null) {
                    show = showEntity;
                    editText1.setText(show.getName() + " (Not editable)");
                    editText1.setEnabled(false);
                    editText2.setText(String.valueOf(show.getDescription()));
                    editText3.setText(String.valueOf(show.getNumberEpisodes()));
                }
            });
        }

        button.setOnClickListener(view -> {
            saveChanges(editText1.getText().toString(),
                    editText2.getText().toString(),
                    Integer.valueOf(editText3.getText().toString())
            );
            onBackPressed();
        });
    }

    private void saveChanges(String name, String description, int numberEpisodes) {

        Intent intent = getIntent();
        intent = new Intent(ShowModify.this, MainActivity.class);

        if (isEditMode) {
            if (!"".equals(name)) {
                String substringShowName = name.substring(0, name.length()-15);
                show.setName(substringShowName);
                System.out.println(show.getName());
                show.setDescription(description);
                show.setNumberEpisodes(numberEpisodes);
                viewModel.updateShow(show, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        System.out.println("update Show: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("update Show: failure");
                    }
                });
                System.out.println(show.getName());
            }
        } else {
            Show newShow = new Show(name, description, numberEpisodes);
            viewModel.createShow(newShow, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "create Show: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create Show: failure", e);
                }
            });
        }
        startActivity(intent);
    }
}