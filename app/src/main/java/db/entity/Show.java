package db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "shows", primaryKeys = {"name"})
public class Show implements Comparable {

    @NonNull
    private String name;

    private String description;
    private int numberEpisodes;
/*
    @Ignore
    private Image picture;
*/

    @Ignore
    public Show() {
    }

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
/*
    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
    */

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Show)) return false;
        Show o = (Show) obj;
        return o.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}
