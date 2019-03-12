package db.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import db.AppDatabase;
import db.async.CreateShow;
import db.async.UpdateShow;
import db.entity.Show;
import db.util.OnAsyncEventListener;

public class ShowRepository {

    public LiveData<Show> getShow(int id, Context context) {
        return AppDatabase.getAppDatabase(context).showDao().getShow(id);
    }

    public LiveData<List<Show>> getAllShows(Context context) {
        return AppDatabase.getAppDatabase(context).showDao().getAllShows();
    }

    public void insert(final Show show, OnAsyncEventListener callback, Context context) {
        new CreateShow(context, callback).execute(show);
    }

    public void update(final Show show, OnAsyncEventListener callback, Context context) {
        new UpdateShow(context, callback).execute(show);
    }
}
