package db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import db.entity.Episode;

@Dao
public abstract class EpisodeDao {

    @Query("SELECT * FROM episodes WHERE showName = :showName")
    public abstract LiveData<List<Episode>> getAllEpisodes(String showName);

    @Query("SELECT * FROM episodes WHERE id = :id")
    public abstract LiveData<Episode> getEpisode(int id);

    @Insert
    public abstract void insertNewEpisode (Episode newEpisode);

    @Update
    public abstract void modifyEpisode(Episode modifiedEpisode);

    @Delete
    public abstract void deleteEpisode(Episode deletedEpisode);

    @Query("DELETE FROM episodes")
    public abstract void deleteAll();

}
