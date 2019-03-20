package db.async.episode;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import db.AppDatabase;
import db.BaseApp;
import db.entity.Episode;
import db.util.OnAsyncEventListener;

public class UpdateEpisode extends AsyncTask<Episode, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateEpisode(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Episode... params) {
        try {
            for (Episode episode : params)
                ((BaseApp) application).getDatabase().episodeDao().modifyEpisode(episode);
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
