package nhung.nguyen.infoage.Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nhung.nguyen.infoage.Adapter.ClassInfo;
import nhung.nguyen.infoage.R;

public class PostActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String postid, classid;
    TextView author, title, content;
    Button back;
    FirebaseUser user;
    ClassInfo classInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        author = findViewById(R.id.post_author);
        title = findViewById(R.id.post_title);
        content = findViewById(R.id.post_content);
        back = findViewById(R.id.back);
        firebaseAuth = FirebaseAuth.getInstance();
        postid = getIntent().getStringExtra("postid");
        SharedPreferences sharedPreferences= getSharedPreferences("Class",MODE_PRIVATE);
        classid = sharedPreferences.getString("classid","");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ClassPost").child(classid).child(postid);
        user = firebaseAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String au = snapshot.child("author").getValue().toString();
                String con = snapshot.child("content").getValue().toString();
                String tit = snapshot.child("title").getValue().toString();
                author.setText(au);
                content.setText(con);
                title.setText(tit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, ClassActivity.class);
                intent.putExtra("classid",classid);
                startActivity(intent);
            }
        });
    }

}