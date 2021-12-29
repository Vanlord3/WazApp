package id.web.wazapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.web.wazapp.DAO.CommentsDAO;
import id.web.wazapp.DAO.FriendsDAO;
import id.web.wazapp.DAO.PostsDAO;
import id.web.wazapp.DAO.UserDAO;

@Database(entities = {User.class,Posts.class,Friends.class,Comments.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract PostsDAO postsDAO();
    public abstract FriendsDAO friendsDAO();
    public abstract CommentsDAO commentsDAO();
}
