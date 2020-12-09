package nhung.nguyen.infoage.Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import nhung.nguyen.infoage.MainActivity.MainActivity;
import nhung.nguyen.infoage.R;

public class DiscussionActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbref;
    String hisUid;
    String myUid;
    EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        ImageButton sendBtn = findViewById(R.id.sendBtn);
        //  Intent intent = getActivity().getIntent();
        // hisUid = intent.getStringExtra("hisUid");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbref= firebaseDatabase.getReference(getString(R.string.discussionPath));
        msg= findViewById(R.id.messageEt);
        Query userQuery = userDbref.orderByChild(getString(R.string.discussionUid)).equalTo(myUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String name = ds.child(getString(R.string.discussionName)).getValue().toString();
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

    }
    private void sendMsg(String mesg){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(getString(R.string.discussionSender),myUid);
        //hashMap.put("receiver",hisUid);
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