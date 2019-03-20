package db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "episodes",
        foreignKeys =
        @ForeignKey(
                entity = Show.class,
                parentColumns = "name",
                childColumns = "showName",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"showName"}
                )}
)
public class Episode {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int number;
    private String name;
    private int length;
    private String showName;

    @Ignore
    public Episode() {
    }

    public Episode(int number, String name, int length, String showName) {
        this.name = name;
        this.number = number;
        this.length = length;
        this.showName = showName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Episode)) return false;
        Episode o = (Episode) obj;
        return (o.getId() == this.getId());
    }

    @Override
    public String toString() {
        return name;
    }
}
