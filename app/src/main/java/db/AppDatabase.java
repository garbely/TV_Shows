package db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import db.dao.EpisodeDao;
import db.dao.ShowDao;
import db.entity.Episode;
import db.entity.Show;

@Database(entities = {Show.class, Episode.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final Object LOCK = new Object();

    public abstract ShowDao showDao();
    public abstract EpisodeDao episodeDao();

    public synchronized static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "fruit-database")
                            /*
                            allow queries on the main thread.
                            Don't do this in a real app!
                            See PersistenceBasicSample
                            https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample
                            for an example.

                            Would throw java.lang.IllegalStateException:
                            Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                            */
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
