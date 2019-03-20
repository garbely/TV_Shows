package db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import db.entity.Show;

@Dao
public interface ShowDao {

    @Query("SELECT * FROM shows")
    LiveData<List<Show>> getAllShows();

    @Query("SELECT * FROM shows WHERE name = :name")
    LiveData<Show> getShow(String name);

    @Insert
    void insertNewShow(Show newShow);

    @Update
    void modifyShow(Show modifiedShow);

    @Delete
    void deleteShow(Show deletedShow);

    @Query("DELETE FROM shows")
    void deleteAll();

}
