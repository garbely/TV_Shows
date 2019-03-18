package db.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import db.AppDatabase;
import db.async.episode.CreateEpisode;
import db.async.episode.DeleteEpisode;
import db.async.episode.UpdateEpisode;
import db.entity.Episode;
import db.util.OnAsyncEventListener;

public class EpisodeRepository {

    private static EpisodeRepository instance;

    private EpisodeRepository() {}

    public static EpisodeRepository getInstance() {
        if (instance == null) {
            synchronized (EpisodeRepository.class) {
                if (instance == null) {
                    instance = new EpisodeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Episode> getEpisode(int episodeId, Context context) {
        return AppDatabase.getInstance(context).episodeDao().getEpisode(episodeId);
    }

    public LiveData<List<Episode>> getAllEpisodes(String showName, Context context) {
        return AppDatabase.getInstance(context).episodeDao().getAllEpisodes(showName);
    }

    public void insert(final Episode episode, OnAsyncEventListener callback, Context context) {
        new CreateEpisode(context, callback).execute(episode);
    }

    public void update(final Episode episode, OnAsyncEventListener callback, Context context) {
        new UpdateEpisode(context, callback).execute(episode);
    }

    public void delete(final Episode episode, OnAsyncEventListener callback, Context context) {
        new DeleteEpisode(context, callback).execute(episode);
    }
}
