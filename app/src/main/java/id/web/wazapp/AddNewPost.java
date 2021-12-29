package id.web.wazapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

public class AddNewPost extends AppCompatActivity {
    ImageButton ivbBack;
    EditText etPost;
    Button btnPost;

    ArrayList<Posts> listPosts = new ArrayList<>();
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        ivbBack = findViewById(R.id.imageButton3);
        etPost = findViewById(R.id.editTextTextMultiLine);
        btnPost = findViewById(R.id.button);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "DBProject").build();
        new getPost().execute();

        btnPost.setOnClickListener(view -> {
//            Log.d("UID: ",FirebaseAuth.getInstance().getUid());
            if(etPost.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(getApplicationContext(), "Harus terisi semua", Toast.LENGTH_SHORT).show();
            }
            else{
                Posts p = new Posts(FirebaseAuth.getInstance().getUid(),etPost.getText().toString(),0, "");
                new insertPosts().execute(p);
                startActivity(new Intent(getApplicationContext(),Setup.class));
            }
        });
    }

    private class getPost extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listPosts.clear();
            listPosts.addAll(db.postsDAO().getPosts());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            PostsAdapter.notifyDataSetChanged();
        }
    }

    private class insertPosts extends AsyncTask<Posts, Void, Void> {

        @Override
        protected Void doInBackground(Posts... Posts) {
            db.postsDAO().insertPosts(Posts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new getPost().execute();
//            etUser.setText("");
//            etJumlah.setText("");
        }
    }
}