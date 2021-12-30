package id.web.wazapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

//import id.web.wazapp.R;


public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.itemViewHolder>{
    private Context context;
    private ArrayList<User> listUser;
    User u;
    AppDatabase db;
    ArrayList<Friends> listFriends;

    public SearchFriendAdapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_results_search, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        u = listUser.get(position);
        holder.tvUsername.setText(u.getUsername());
        holder.tvName.setText(u.getName());

        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DBProject").build();
//        new getFriends().execute();

        holder.ivbAdd.setOnClickListener(view -> {
            Friends f = new Friends(FirebaseAuth.getInstance().getUid(),u.getId(),0);
            new insertFriends().execute(f);
//            db.friendsDAO().insertFriends(f);
            context.startActivity(new Intent(context.getApplicationContext(),Setup.class));
//            Toast.makeText(context.getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvUsername;
        ImageButton ivbAdd;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.textViewName);
            tvUsername = itemView.findViewById(R.id.textViewUsername);
            ivbAdd = itemView.findViewById(R.id.imageButtonAdd);


        }
    }
//    private class getFriends extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            listFriends.clear();
//            listFriends.addAll(db.friendsDAO().getFriends());
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
////            UserAdapter.notifyDataSetChanged();
//        }
//    }

    private class insertFriends extends AsyncTask<Friends, Void, Void> {

        @Override
        protected Void doInBackground(Friends... Friends) {
            db.friendsDAO().insertFriends(Friends[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context.getApplicationContext(), "Sukses", Toast.LENGTH_SHORT).show();
//            new getFriends().execute();
//            etUser.setText("");
//            etJumlah.setText("");
        }
    }
}
