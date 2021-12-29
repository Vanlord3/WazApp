package id.web.wazapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "friends")
public class Friends {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idfriends")
    private int id;

    @ForeignKey(entity = User.class,parentColumns = "iduser",childColumns = "idsender_request")
    @ColumnInfo(name = "idsender_request")
    private String idsender_request;

    @ForeignKey(entity = User.class,parentColumns = "iduser",childColumns = "idreceiver_request")
    @ColumnInfo(name = "idreceiver_request")
    private String idreceiver_request;

    @ColumnInfo(name = "status")
    private int status;

    public Friends(String idsender_request, String idreceiver_request, int status) {
        this.idsender_request = idsender_request;
        this.idreceiver_request = idreceiver_request;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdsender_request() {
        return idsender_request;
    }

    public void setIdsender_request(String idsender_request) {
        this.idsender_request = idsender_request;
    }

    public String getIdreceiver_request() {
        return idreceiver_request;
    }

    public void setIdreceiver_request(String idreceiver_request) {
        this.idreceiver_request = idreceiver_request;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
