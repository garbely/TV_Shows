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
import android.widget.Toast;

import db.entity.Show;
import db.util.OnAsyncEventListener;
import db.viewmodel.show.ShowViewModel;

public class ShowModify extends AppCompatActivity {

    private static final String TAG = "ShowModify";

    private String showName;
    private boolean isEditMode;

    private EditText editText1;
    private EditText editText2;
    private Button button;

    private ShowViewModel viewModel;
    private Show show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_show_modify);

        showName = getIntent().getStringExtra("showName");

        editText1 = (EditText) findViewById(R.id.name);
        editText2 = (EditText) findViewById(R.id.description);
        button = (Button) findViewById(R.id.save);

        editText1.addTextChangedListener(loginTextWatcher);
        editText2.addTextChangedListener(loginTextWatcher);

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
                }
            });
        }

        button.setOnClickListener(view -> {
            saveChanges(editText1.getText().toString(),
                    editText2.getText().toString()
            );
            onBackPressed();
        });
    }

    private void saveChanges(String name, String description) {

        Intent intent = new Intent(ShowModify.this, MainActivity.class);

        if (isEditMode) {
                String substringShowName = name.substring(0, name.length() - 15);
                show.setName(substringShowName);
                System.out.println(show.getName());
                show.setDescription(description);
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
        } else {

            Show newShow = new Show(name, description);
            viewModel.createShow(newShow, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "create Show: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create Show: failure", e);
                    Toast toast = Toast.makeText(getApplicationContext(),"Show is already in the list. Try Again.", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
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
            String descInput = editText2.getText().toString().trim();

            button.setEnabled(!nameInput.isEmpty() && !descInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}