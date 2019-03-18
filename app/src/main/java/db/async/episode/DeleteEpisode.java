package db.async.episode;

import android.content.Context;
import android.os.AsyncTask;

import db.AppDatabase;
import db.entity.Episode;
import db.util.OnAsyncEventListener;

public class DeleteEpisode extends AsyncTask<Episode, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteEpisode(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Episode... params) {
        try {
            for (Episode episode : params)
                database.episodeDao().deleteEpisode(episode);
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