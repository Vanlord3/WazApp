package id.web.wazapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.web.wazapp.Posts;
import id.web.wazapp.User;

@Dao
public interface PostsDAO {
    @Query("select * from posts")
    List<Posts> getPosts();

    @Insert
    void insertPosts(Posts p);

    @Update
    void updatePosts(Posts p);

    @Delete
    void deletePosts(Posts p);
}
