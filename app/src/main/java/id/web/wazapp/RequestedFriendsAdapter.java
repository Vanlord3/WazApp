package id.web.wazapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class RequestedFriendsAdapter extends RecyclerView.Adapter<RequestedFriendsAdapter.itemViewHolder>{
    private Context context;
    private ArrayList<Friends> listFriendsRequested;
    private ArrayList<User> listUser = new ArrayList<>();
    Friends f;
    AppDatabase db;

    public RequestedFriendsAdapter(Context context, ArrayList<Friends> listFriendsRequested, ArrayList<User> listUser) {
        this.context = context;
        this.listFriendsRequested = listFriendsRequested;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_requestedfriends, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DBProject").build();

        f = listFriendsRequested.get(position);
        Friends selected;

        for(User u:listUser){
            for (Friends f:listFriendsRequested){
                if(f.getIdsender_request().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())){
                    if(f.getIdreceiver_request().equalsIgnoreCase(u.getId())){
                        if(f.getStatus()==0){
                            holder.tvName.setText(u.getName());
                            holder.ivbAction.setImageResource(R.drawable.ic_baseline_cancel_24);
                            holder.ivbAction.setTag("2");
                        }

                    }

                }
                else if(f.getIdreceiver_request().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())){
                    if(f.getIdsender_request().equalsIgnoreCase(u.getId())){
                        if(f.getStatus()==0){
                            holder.tvName.setText(u.getName());
                            holder.ivbAction.setImageResource(R.drawable.ic_baseline_add_24);
                            holder.ivbAction.setTag("1");
                            selected = f;
                        }

                    }
                }
            }
        }

        holder.ivbAction.setOnClickListener(view -> {
            if(holder.ivbAction.getTag().toString().equalsIgnoreCase("1")){
                Toast.makeText(context.getApplicationContext(), "Add Clicked", Toast.LENGTH_SHORT).show();
                Log.d("Selected:",String.valueOf(f.getId()));
                f.setStatus(1);
                new updateFriends().execute(f);
            }
            else{
                Toast.makeText(context.getApplicationContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFriendsRequested.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton ivbAction;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.textView10);
            ivbAction = itemView.findViewById(R.id.imageButton4);

        }
    }

//    private class getFriends extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            listFriendsRequested.clear();
//            listFriendsRequested.addAll(db.friendsDAO().getFriends());
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
////            UserAdapter.notifyDataSetChanged();
//        }
//    }


    private class updateFriends extends AsyncTask<Friends, Void, Void> {

        @Override
        protected Void doInBackground(Friends... Friends) {
            db.friendsDAO().updateFriends(Friends[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            new getFriends().execute();
//            etBarang.setText("");
//            etJumlah.setText("");
        }
    }
}
