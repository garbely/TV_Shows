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
public interface EpisodeDao {

    @Query("SELECT * FROM episode WHERE showName = :showName")
    LiveData<List<Episode>> getAllEpisodes(String showName);

    @Query("SELECT * FROM episode WHERE id = :id")
    LiveData<Episode> getEpisode(int id);

    @Insert
    void insertNewEpisode (Episode newEpisode);

    @Update
    void modifyEpisode(Episode modifiedEpisode);

    @Delete
    void deleteEpisode(Episode deletedEpisode);

    @Query("DELETE FROM Episode")
    public abstract void deleteAll();

}
