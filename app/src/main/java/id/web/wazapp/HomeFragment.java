package id.web.wazapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView tvName;
    FirebaseUser firebaseUser;
    ImageButton ivbSignOut,ivAddFriend;

    ArrayList<Friends> listFriends = new ArrayList<>();
    ArrayList<User> listUser = new ArrayList<>();
    AppDatabase db;

    TextView tvRequestFriend,tvFriends;
    ImageView ivDropdown1,ivDropdown2;

    RecyclerView rvRequestFriend,rvAllFriend;
    RequestedFriendsAdapter requestedFriendsAdapter;
    AllFriendsAdapter allFriendsAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tvName);
        ivbSignOut = view.findViewById(R.id.imageButton);
        ivAddFriend = view.findViewById(R.id.imageButton2);
        tvRequestFriend = view.findViewById(R.id.textView8);
        tvFriends = view.findViewById(R.id.textView9);
        ivDropdown1 = view.findViewById(R.id.imageView9);
        ivDropdown2 = view.findViewById(R.id.imageView8);

        rvRequestFriend = view.findViewById(R.id.rvRequestedFriends);
        rvAllFriend = view.findViewById(R.id.rvAllFriends);

        rvAllFriend.setHasFixedSize(true);
        rvAllFriend.setLayoutManager(new LinearLayoutManager(getContext()));

        rvRequestFriend.setHasFixedSize(true);
        rvRequestFriend.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        Log.d("UID:", FirebaseAuth.getInstance().getUid());

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "DBProject").build();
        new getFriends().execute();
        new getUser().execute();

        if(firebaseUser!=null){
            tvName.setText(firebaseUser.getDisplayName());
        }

        ivbSignOut.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),Login.class));
        });

        ivAddFriend.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(),AddFriendActivity.class));
        });

        rvAllFriend.setVisibility(View.GONE);
        rvRequestFriend.setVisibility(View.GONE);

        tvRequestFriend.setOnClickListener(view1 -> {
            if(ivDropdown1.getTag().toString().equalsIgnoreCase("1")){
                ivDropdown1.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                ivDropdown1.setTag("2");
                rvRequestFriend.setVisibility(View.VISIBLE);
            }
            else{
                ivDropdown1.setImageResource(R.drawable.ic_baseline_arrow_right_24);
                ivDropdown1.setTag("1");
                rvRequestFriend.setVisibility(View.GONE);
            }
        });
        tvFriends.setOnClickListener(view1 -> {
            if(ivDropdown2.getTag().toString().equalsIgnoreCase("1")){
                ivDropdown2.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                ivDropdown2.setTag("2");
                rvAllFriend.setVisibility(View.VISIBLE);
            }
            else{
                ivDropdown2.setImageResource(R.drawable.ic_baseline_arrow_right_24);
                ivDropdown2.setTag("1");
                rvAllFriend.setVisibility(View.GONE);
            }
        });



    }

    private class getUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listUser.clear();
            listUser.addAll(db.userDAO().getUser());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private class getFriends extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listFriends.clear();
            listFriends.addAll(db.friendsDAO().getFriends());
            ArrayList<Friends> listRequested = new ArrayList<>();
            ArrayList<Friends> listAllFriends = new ArrayList<>();
            for(Friends f:listFriends){


                if(f.getStatus() == 0){
                    listRequested.add(f);
                    Log.d("List Request: ",listRequested.toString());
                    requestedFriendsAdapter = new RequestedFriendsAdapter(getContext(),listRequested,listUser);
                    rvRequestFriend.setAdapter(requestedFriendsAdapter);
                }
                else{
                    listAllFriends.add(f);
                    allFriendsAdapter = new AllFriendsAdapter(getContext(),listAllFriends,listUser);
                    rvAllFriend.setAdapter(allFriendsAdapter);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class insertFriends extends AsyncTask<Friends, Void, Void> {

        @Override
        protected Void doInBackground(Friends... Friends) {
            db.friendsDAO().insertFriends(Friends[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new getFriends().execute();
        }
    }
}