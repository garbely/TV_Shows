package db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import db.AppDatabase;
import db.BaseApp;
import db.async.show.CreateShow;
import db.async.show.DeleteShow;
import db.async.show.UpdateShow;
import db.entity.Show;
import db.util.OnAsyncEventListener;

public class ShowRepository {

    private static ShowRepository instance;

    private ShowRepository() {}

    public static ShowRepository getInstance() {
        if (instance == null) {
            synchronized (ShowRepository.class) {
                if (instance == null) {
                    instance = new ShowRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Show> getShow(final String name, Application application) {
        return ((BaseApp) application).getDatabase().showDao().getShow(name);
    }

    public LiveData<List<Show>> getAllShows(Application application) {
        return ((BaseApp) application).getDatabase().showDao().getAllShows();
    }

    public void insert(final Show show, OnAsyncEventListener callback, Application application) {
        new CreateShow(application, callback).execute(show);
    }

    public void update(final Show show, OnAsyncEventListener callback, Application application) {
        new UpdateShow(application, callback).execute(show);
    }

    public void delete(final Show show, OnAsyncEventListener callback, Application application) {
        new DeleteShow(application, callback).execute(show);
    }
}
