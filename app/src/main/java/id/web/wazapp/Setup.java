package id.web.wazapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

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
//                    fragment = Fragment2.newInstance();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.container, fragment)
//                            .commit();
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
}