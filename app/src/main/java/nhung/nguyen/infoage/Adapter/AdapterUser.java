package nhung.nguyen.infoage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nhung.nguyen.infoage.Class.DiscussionActivity;
import nhung.nguyen.infoage.R;

public class AdapterUser  extends RecyclerView.Adapter<AdapterUser.MyHolder> {
    Context context;
    List<ModelUser> userList;

    public AdapterUser(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.user,parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String userImage = userList.get(position).email;
        String userName = userList.get(position).name;
        String userEmail = userList.get(position).email;
        holder.mNameTv.setText(userName);
        holder.mEmailTv.setText(userEmail);
        //Picasso.get().load(userImage).placeholder(R.drawable.ic_group_white).into(holder.mAvatarIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            context.startActivity(new Intent(context, DiscussionActivity.class));
               // getSupportFragmentManager().beginTransaction().replace(R.id.content,new Discussion()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv= itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);
        }

    }
}
