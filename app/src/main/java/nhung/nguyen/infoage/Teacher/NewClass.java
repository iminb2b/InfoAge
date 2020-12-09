package nhung.nguyen.infoage.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nhung.nguyen.infoage.Adapter.ClassInfo;
import nhung.nguyen.infoage.R;

public class NewClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int count=0;
    String lang="English";
    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        Spinner sp_laguage = findViewById(R.id.sp_langguage);
        final EditText class_name = findViewById(R.id.class_name);
        final EditText class_ins = findViewById(R.id.class_ins);
        final EditText class_des = findViewById(R.id.class_des);

        Button create = findViewById(R.id.create);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language,
                android.R.layout.simple_spinner_item
        );
        sp_laguage.setAdapter(adapter);
        sp_laguage.setOnItemSelectedListener(this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firebaseAuth = FirebaseAuth.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();
                String name = class_name.getText().toString();
                String ins = class_ins.getText().toString();
                String des = class_des.getText().toString();
                String classid = uid+lang+(int)(Math.random()*1000+0);
                ClassInfo classInfo= new ClassInfo(name,des,ins,lang,classid);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(lang);
                myRef.child(classid).setValue(classInfo);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference("Teacher").child(uid);
                ref.child(classid).setValue(classInfo);

                Intent intent = new Intent(NewClass.this, TeacherActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        lang = parent.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}