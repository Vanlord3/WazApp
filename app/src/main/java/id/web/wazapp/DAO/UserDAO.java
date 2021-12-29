package id.web.wazapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.web.wazapp.User;

@Dao
public interface UserDAO {
    @Query("select * from users")
    List<User> getUser();

    @Insert
    void insertUser(User u);

    @Update
    void updateUser(User u);

    @Delete
    void deleteUser(User u);
}
