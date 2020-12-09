package nhung.nguyen.infoage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nhung.nguyen.infoage.Class.PostActivity;
import nhung.nguyen.infoage.R;

public class AdapterPost  extends RecyclerView.Adapter<AdapterPost.MyHolder> {
    Context context;
    List<ModelPost> postList;

    public AdapterPost(Context context, List<ModelPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.post,parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        String title = postList.get(position).getTitle();
        String author = postList.get(position).getAuthor();
        final String postid = postList.get(position).getPostid();
        holder.pTitle.setText(title);
        holder.pAuthor.setText(author);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, PostActivity.class);
                intent.putExtra("postid",postid);
                context.startActivity(intent);

                // getSupportFragmentManager().beginTransaction().replace(R.id.content,new Discussion()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{


        TextView pTitle, pAuthor;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            pTitle = itemView.findViewById(R.id.pTitle);
            pAuthor= itemView.findViewById(R.id.pAuthor);
        }

    }
}
