package nhung.nguyen.infoage.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import nhung.nguyen.infoage.Class.DiscussionActivity;
import nhung.nguyen.infoage.R;

public class ApplyClass extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String lang, classid;
    TextView name, ins, des, language;
    Button aplly, talk;
    FirebaseUser user;
    ClassInfo classInfo;
    String insid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_class);
        name = findViewById(R.id.class_name);
        ins = findViewById(R.id.class_ins);
        des = findViewById(R.id.class_des);
        language = findViewById(R.id.class_lang);
        aplly = findViewById(R.id.aplly);
        talk = findViewById(R.id.talk);
        firebaseAuth = FirebaseAuth.getInstance();
        lang = getIntent().getStringExtra("lang");
        classid = getIntent().getStringExtra("classid");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(lang).child(classid);
        user = firebaseAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nam = snapshot.child("className").getValue().toString();
                insid = snapshot.child("instructor").getValue().toString();
                String lan = snapshot.child("language").getValue().toString();
                String de = snapshot.child("description").getValue().toString();
                name.setText(nam);
                //ins.setText(instructor);
                language.setText(lan);
                des.setText(de);
                classInfo= new ClassInfo(nam,de,insid,lan,classid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        aplly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Student").child(uid);
                myRef.child(classid).setValue(classInfo);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference("Classes");
                ref.child(classid).child(uid).child("id").setValue(uid);
                startActivity(new Intent(ApplyClass.this, StudentActivity.class));
            }
        });
        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyClass.this, DiscussionActivity.class);
                intent.putExtra("hisUid", insid);
                startActivity(intent);
            }
        });
    }
}