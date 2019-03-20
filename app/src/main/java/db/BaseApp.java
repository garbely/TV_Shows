package db;

import android.app.Application;

import db.AppDatabase;
import db.repository.EpisodeRepository;
import db.repository.ShowRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ShowRepository getShowRepository() {
        return ShowRepository.getInstance();
    }

    public EpisodeRepository getEpisodeRepository() {
        return EpisodeRepository.getInstance();
    }
}