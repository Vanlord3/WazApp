package id.web.wazapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.web.wazapp.Friends;
import id.web.wazapp.Posts;

@Dao
public interface FriendsDAO {
    @Query("select * from friends")
    List<Friends> getFriends();

    @Insert
    void insertFriends(Friends f);

    @Update
    void updateFriends(Friends f);

    @Delete
    void deleteFriends(Friends f);
}
