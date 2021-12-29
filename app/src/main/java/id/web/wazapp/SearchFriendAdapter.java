package id.web.wazapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//import id.web.wazapp.R;


public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.itemViewHolder>{
    private Context context;
    private ArrayList<User> listUser;
    private CharSequence chars;
    User u;

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

        holder.ivbAdd.setOnClickListener(view -> {

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
}
