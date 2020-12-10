package nhung.nguyen.infoage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import nhung.nguyen.infoage.R;

public class AdapterChat  extends RecyclerView.Adapter<AdapterChat.MyHolder> {
    Context context;
    List<ModelChat> chatList;
    int SHOW_LEFT = 0;
    int SHOW_RIGHT = 1;

    public AdapterChat(Context context, List<ModelChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
       // LayoutInflater inflater= LayoutInflater.from(context);
        //v= inflater.inflate(R.layout.show_chat_left,parent, false);
        if(viewType==SHOW_LEFT){
            LayoutInflater inflater= LayoutInflater.from(context);
            v= inflater.inflate(R.layout.show_chat_left,parent, false);

        }else{
            LayoutInflater inflater= LayoutInflater.from(context);
            v= inflater.inflate(R.layout.show_chat_right,parent, false);

        }
        MyHolder holder = new MyHolder(v);
        return holder;

    }
    FirebaseUser fUser;
    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(fUser.getUid())){
            return  SHOW_RIGHT;
        }else{
            return  SHOW_LEFT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String message = chatList.get(position).getMessage();

        holder.message.setText(message);

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        TextView message;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageTv);
        }

    }
}
