package nhung.nguyen.infoage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import nhung.nguyen.infoage.Class.Discussion;
import nhung.nguyen.infoage.Class.Student;

public class AdapterChat  extends RecyclerView.Adapter<AdapterChat.MyHolder> {
    Context context;
    String message;
    int SHOW_LEFT = 0;
    int SHOW_RIGHT = 1;

    public AdapterChat(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
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

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.message.setText(message);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        TextView message;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageEt);
        }

    }
}
