package nhung.nguyen.infoage.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nhung.nguyen.infoage.MainActivity.HomeActivity;
import nhung.nguyen.infoage.R;

public class ChoiceActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    CheckBox teacher, student;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("UserMode").child(uid);

        teacher = findViewById(R.id.teacher);
        student = findViewById(R.id.student);
        next = findViewById(R.id.next);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacher.isChecked()){
                    databaseReference.child("Teacher").setValue(true);
                }else{
                    databaseReference.child("Teacher").setValue(false);
                }
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacher.isChecked()){
                    databaseReference.child("Student").setValue(true);
                }else{
                    databaseReference.child("Student").setValue(false);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceActivity.this, HomeActivity.class));
            }
        });
    }
}