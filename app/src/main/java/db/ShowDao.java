package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ShowDao {

    @Query("SELECT name FROM show")
    List<Show> getAllShows();

    @Query("SELECT * FROM show WHERE id = :id")
    Show getShow(int id);

    @Query("SELECT * FROM episode WHERE showId = :id")
    List<Episode> getEpisodes(int id);

    @Query("SELECT * FROM episode WHERE id = :id")
    Episode getEpisode(int id);

    @Insert
    void insertNewShow(Show newShow);
    void insertNewEpisode (Episode newEpisode);

    @Update
    void modifyShow(Show modifiedShow);
    void modifyEpisode(Episode modifiedEpisode);

    @Delete
    void deleteShow(int showId);
    void deleteEpisode(int episodeId);


}
