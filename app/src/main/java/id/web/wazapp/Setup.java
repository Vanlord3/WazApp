package id.web.wazapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Setup extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(item -> {
            //memeriksa item mana yang dipilih
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = HomeFragment.newInstance(); //fragment = new AddFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    return true;
                case R.id.chats:
                    fragment = ChatsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    return true;
                case R.id.posts:
                    fragment = PostsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    return true;
                case R.id.settings:
//                    fragment = Fragment2.newInstance();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.container, fragment)
//                            .commit();
                    return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.home);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent2 = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}