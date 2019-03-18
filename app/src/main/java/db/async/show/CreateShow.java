package db.async.show;

import android.content.Context;
import android.os.AsyncTask;

import db.AppDatabase;
import db.entity.Show;
import db.util.OnAsyncEventListener;

public class CreateShow extends AsyncTask<Show, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateShow(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Show... params) {
        try {
            for (Show show : params)
                database.showDao().insertNewShow(show);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}