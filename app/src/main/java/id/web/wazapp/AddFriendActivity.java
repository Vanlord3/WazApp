package id.web.wazapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    EditText etSearch;
    ImageButton ivbBack;
    RecyclerView rvResults;
    SearchFriendAdapter searchFriendAdapter;

    ArrayList<User> listUser = new ArrayList<>();
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        etSearch = findViewById(R.id.editTextSearchUsername);
        ivbBack = findViewById(R.id.imageButtonBack);
        rvResults = findViewById(R.id.rvResultsSearch);

        rvResults.setHasFixedSize(true);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "DBProject").build();
        new getUser().execute();


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivbBack.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Setup.class));
            finish();
        });
    }
    private class getUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listUser.clear();
            listUser.addAll(db.userDAO().getUser());
            Log.i("Data: ",listUser.toString());
            searchFriendAdapter = new SearchFriendAdapter(AddFriendActivity.this, listUser);
            rvResults.setAdapter(null);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            UserAdapter.notifyDataSetChanged();
        }
    }
    private void filter(CharSequence s){
        ArrayList<User> filteredlist = new ArrayList<>();

        if(s.length()==0){
            rvResults.setAdapter(null);
        }
        else{
            for(User user : listUser){
                if(user.getUsername().toLowerCase().contains(s) && !user.getId().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())){
                    filteredlist.add(user);
                }
            }
            searchFriendAdapter = new SearchFriendAdapter(AddFriendActivity.this, filteredlist);
            rvResults.setAdapter(searchFriendAdapter);

//            friendsAdapter.setItemClick((v, i) -> {
//                Intent profFriend = new Intent(Friend.this,ProfileFriend.class);
//                profFriend.putExtra("usernamefriend", filteredlist.get(i).getUsername());
//                profFriend.putExtra("username",username);
//                profFriend.putParcelableArrayListExtra("user",listUser);
//                profFriend.putParcelableArrayListExtra("posts",listPosts);
//                profFriend.putParcelableArrayListExtra("message",messageUsers);
//                startActivity(profFriend);
//            });
        }

    }
}