package db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import db.AppDatabase;
import db.BaseApp;
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

    public LiveData<Episode> getEpisode(final int episodeId, Application application) {
        return ((BaseApp) application).getDatabase().episodeDao().getEpisode(episodeId);
    }

    public LiveData<List<Episode>> getAllEpisodes(final String showName, Application application) {
        return ((BaseApp) application).getDatabase().episodeDao().getAllEpisodes(showName);
    }

    public void insert(final Episode episode, OnAsyncEventListener callback, Application application) {
        new CreateEpisode(application, callback).execute(episode);
    }

    public void update(final Episode episode, OnAsyncEventListener callback, Application application) {
        new UpdateEpisode(application, callback).execute(episode);
    }

    public void delete(final Episode episode, OnAsyncEventListener callback, Application application) {
        new DeleteEpisode(application, callback).execute(episode);
    }
}
