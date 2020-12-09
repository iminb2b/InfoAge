package nhung.nguyen.infoage.Class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nhung.nguyen.infoage.Adapter.ModelPost;
import nhung.nguyen.infoage.R;

public class NewPost extends AppCompatActivity {
    EditText title, content;
    Button post;
    FirebaseAuth firebaseAuth;
    String cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        SharedPreferences sharedPreferences = getSharedPreferences("Class", MODE_PRIVATE);
        cid = sharedPreferences.getString("classid","");

        title= findViewById(R.id.post_title);
        content= findViewById(R.id.post_content);
        post = findViewById(R.id.post);
        firebaseAuth = FirebaseAuth.getInstance();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();
                String tit = title.getText().toString();
                String con = content.getText().toString();
                String auth = user.getDisplayName();
                String postid = cid+uid+(int)(Math.random()*1000);
                ModelPost modelPost= new ModelPost(tit,con,auth,postid);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ClassPost").child(cid);
                myRef.child(postid).setValue(modelPost);
                content.setText(cid);
                Intent intent = new Intent(NewPost.this,ClassActivity.class);
                intent.putExtra("classid", cid);
                startActivity(intent);
            }
        });
    }
}