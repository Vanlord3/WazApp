package id.web.wazapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AllFriendsAdapter extends RecyclerView.Adapter<AllFriendsAdapter.itemViewHolder>{
    private Context context;
    private ArrayList<Friends> listFriendsAccepted;
    private ArrayList<User> listUser = new ArrayList<>();
    Friends f;
    AppDatabase db;

    public AllFriendsAdapter(Context context, ArrayList<Friends> listFriendsAccepted, ArrayList<User> listUser) {
        this.context = context;
        this.listFriendsAccepted = listFriendsAccepted;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_allfriends, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DBProject").build();

        f = listFriendsAccepted.get(position);
        Friends selected;

        for(User u:listUser){
            for (Friends f:listFriendsAccepted){
                if(f.getIdsender_request().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())){
                    if(f.getIdreceiver_request().equalsIgnoreCase(u.getId())){
                        if(f.getStatus()==1){
                            holder.tvName.setText(u.getName());
//                            holder.ivbAction.setImageResource(R.drawable.ic_baseline_cancel_24);
//                            holder.ivbAction.setTag("2");
                        }

                    }

                }
                else if(f.getIdreceiver_request().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())){
                    if(f.getIdsender_request().equalsIgnoreCase(u.getId())){
                        if(f.getStatus()==1){
                            holder.tvName.setText(u.getName());
//                            holder.ivbAction.setImageResource(R.drawable.ic_baseline_add_24);
//                            holder.ivbAction.setTag("1");
                            selected = f;
                        }

                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return listFriendsAccepted.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.textView11);
        }
    }
}
