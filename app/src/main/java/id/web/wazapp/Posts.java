package id.web.wazapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "posts")
public class Posts {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idposts")
    private int id;

    @ForeignKey(entity = User.class,parentColumns = "iduser",childColumns = "idusers_posts")
    @ColumnInfo(name = "idusers_posts")
    private String idusers_posts;

    @ColumnInfo(name = "isi")
    private String isi;

    @ColumnInfo(name = "like")
    private int like;

    @ColumnInfo(name = "date")
    private String date;

    public Posts(String idusers_posts, String isi, int like, String date) {
        this.idusers_posts = idusers_posts;
        this.isi = isi;
        this.like = like;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdusers_posts() {
        return idusers_posts;
    }

    public void setIdusers_posts(String idusers_posts) {
        this.idusers_posts = idusers_posts;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
