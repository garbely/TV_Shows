package db.async.show;

import android.app.Application;
import android.os.AsyncTask;

import db.BaseApp;
import db.entity.Show;
import db.util.OnAsyncEventListener;

public class DeleteShow extends AsyncTask<Show, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteShow(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Show... params) {
        try {
            for (Show show : params)
                ((BaseApp) application).getDatabase().showDao().deleteShow(show);
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