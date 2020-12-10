package nhung.nguyen.infoage.Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nhung.nguyen.infoage.Adapter.AdapterChat;
import nhung.nguyen.infoage.Adapter.ModelChat;
import nhung.nguyen.infoage.MainActivity.MainActivity;
import nhung.nguyen.infoage.R;

public class DiscussionActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbref;
    RecyclerView recyclerView;
    String hisUid;
    String myUid;
    EditText msg;
    TextView check;
    ValueEventListener valueEventListener;
    List<ModelChat> chatList;
    AdapterChat adapterChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        ImageButton sendBtn = findViewById(R.id.sendBtn);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chat_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        check = findViewById(R.id.check);
        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbref= firebaseDatabase.getReference(getString(R.string.discussionPath));
        FirebaseUser user = firebaseAuth.getCurrentUser();
        myUid = user.getUid();
        msg= findViewById(R.id.messageEt);

        Query userQuery = userDbref.orderByChild(getString(R.string.discussionUid)).equalTo(myUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String name = ds.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = msg.getText().toString().trim();
                if(TextUtils.isEmpty(mes)){

                }else{
                    sendMsg(mes);
                }
            }
        });
       readMsg();

    }

    private void readMsg() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    String send = ds.child("sender").getValue().toString();
                    String mes = ds.child("message").getValue().toString();
                    String receive = ds.child("receiver").getValue().toString();
                    ModelChat chat = new ModelChat(mes, send, receive );

                    if(chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid) ||
                            chat.getReceiver().equals(hisUid) && chat.getSender().equals(myUid)){
                        chatList.add(chat);
                   }
                   adapterChat = new AdapterChat(DiscussionActivity.this, chatList);
                   adapterChat.notifyDataSetChanged();
                   recyclerView.setAdapter(adapterChat);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMsg(String mesg){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(getString(R.string.discussionSender),myUid);
        hashMap.put("receiver",hisUid);
        hashMap.put(getString(R.string.discussionMessage),mesg);
        databaseReference.child(getString(R.string.discussionChat)).push().setValue(hashMap);
        msg.setText(getString(R.string.discussionBlank));
    }
    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            myUid = user.getUid();
        }else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

}