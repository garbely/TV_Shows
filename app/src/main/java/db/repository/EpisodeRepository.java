package db.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import db.AppDatabase;
import db.async.CreateEpisode;
import db.async.UpdateEpisode;
import db.entity.Episode;
import db.util.OnAsyncEventListener;

public class EpisodeRepository {

    public LiveData<Episode> getEpisode(int episodeId, Context context) {
        return AppDatabase.getAppDatabase(context).episodeDao().getEpisode(episodeId);
    }

    public LiveData<List<Episode>> getAllEpisodes(int showId, Context context) {
        return AppDatabase.getAppDatabase(context).episodeDao().getAllEpisodes(showId);
    }

    public void insert(final Episode show, OnAsyncEventListener callback, Context context) {
        new CreateEpisode(context, callback).execute(show);
    }

    public void update(final Episode show, OnAsyncEventListener callback, Context context) {
        new UpdateEpisode(context, callback).execute(show);
    }
}
