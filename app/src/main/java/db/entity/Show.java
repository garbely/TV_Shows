package db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"name"})
public class Show {

    @NonNull
    private String name;

    private String description;
    private int numberEpisodes;

    @Ignore
    private Image picture;

    public Show(@NonNull String name, String description, int numberEpisodes) {
        this.name = name;
        this.description = description;
        this.numberEpisodes = numberEpisodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberEpisodes() {
        return numberEpisodes;
    }

    public void setNumberEpisodes(int numberEpisodes) {
        this.numberEpisodes = numberEpisodes;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}
