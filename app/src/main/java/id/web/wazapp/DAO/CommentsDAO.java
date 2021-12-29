package id.web.wazapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.web.wazapp.Comments;
import id.web.wazapp.Posts;

@Dao
public interface CommentsDAO {
    @Query("select * from comments")
    List<Comments> getComments();

    @Insert
    void insertComments(Comments c);

    @Update
    void updateComments(Comments c);

    @Delete
    void deleteComments(Comments c);
}
