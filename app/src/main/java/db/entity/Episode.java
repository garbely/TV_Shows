package db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Show.class,
                                    parentColumns = "name",
                                    childColumns = "showName"))
public class Episode {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int number;
    private String name;
    private int length;
    private String showName;

    public Episode(@NonNull int number, String name, int length, String showName) {
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
}
