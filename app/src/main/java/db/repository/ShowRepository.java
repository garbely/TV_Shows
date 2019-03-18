package db.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import db.AppDatabase;
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

    public LiveData<Show> getShow(String name, Context context) {
        return AppDatabase.getInstance(context).showDao().getShow(name);
    }

    public LiveData<List<Show>> getAllShows(Context context) {
        return AppDatabase.getInstance(context).showDao().getAllShows();
    }

    public void insert(final Show show, OnAsyncEventListener callback, Context context) {
        new CreateShow(context, callback).execute(show);
    }

    public void update(final Show show, OnAsyncEventListener callback, Context context) {
        new UpdateShow(context, callback).execute(show);
    }

    public void delete(final Show show, OnAsyncEventListener callback, Context context) {
        new DeleteShow(context, callback).execute(show);
    }
}
