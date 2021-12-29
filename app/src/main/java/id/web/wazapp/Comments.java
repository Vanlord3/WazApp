package id.web.wazapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments")
public class Comments {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idcomment")
    private int id;

    @ForeignKey(entity = User.class,parentColumns = "iduser",childColumns = "idsender_comment")
    @ColumnInfo(name = "idsender_comment")
    private String idsender_comment;

    @ForeignKey(entity = Posts.class,parentColumns = "idposts",childColumns = "idposts_comment")
    @ColumnInfo(name = "idposts_comment")
    private int idposts_comment;

    @ColumnInfo(name = "isi")
    private String isi;

    @ColumnInfo(name = "time")
    private String time;

    public Comments(String idsender_comment, int idposts_comment, String isi, String time) {
        this.idsender_comment = idsender_comment;
        this.idposts_comment = idposts_comment;
        this.isi = isi;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdsender_comment() {
        return idsender_comment;
    }

    public void setIdsender_comment(String idsender_comment) {
        this.idsender_comment = idsender_comment;
    }

    public int getIdposts_comment() {
        return idposts_comment;
    }

    public void setIdposts_comment(int idposts_comment) {
        this.idposts_comment = idposts_comment;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
