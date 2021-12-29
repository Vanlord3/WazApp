package id.web.wazapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllPostsAdapter extends RecyclerView.Adapter<AllPostsAdapter.itemViewHolder>{
    private Context context;
    private ArrayList<Posts> listPosts;
    private ArrayList<User> listUser;
    Posts p;

    public AllPostsAdapter(Context context, ArrayList<Posts> listPosts, ArrayList<User> listUser) {
        this.context = context;
        this.listPosts = listPosts;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_allposts, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        p = listPosts.get(position);

//        boolean found = false;
        for(User u : listUser){
            if(p.getIdusers_posts() == u.getId()){
                holder.tvName.setText(u.getName());
                holder.tvIsi.setText(p.getIsi());
                holder.tvCountLike.setText(p.getLike());
            }
        }

//        holder.tvIsi.setText(p.getIsi());

        holder.ivLike.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvIsi,tvCountLike;
        ImageView ivLike,ivComment,ivMenu;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.textView3);
            tvIsi = itemView.findViewById(R.id.textView5);
            tvCountLike = itemView.findViewById(R.id.textView6);
            ivLike = itemView.findViewById(R.id.imageView3);
            ivComment = itemView.findViewById(R.id.imageView4);
            ivMenu = itemView.findViewById(R.id.imageView5);
        }
    }
}
