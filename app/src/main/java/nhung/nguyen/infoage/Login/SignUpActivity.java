package nhung.nguyen.infoage.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import nhung.nguyen.infoage.R;

public class SignUpActivity extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword;
    Button btn_signUp;
    FirebaseAuth fAuth;
    TextView signin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        btn_signUp = findViewById(R.id.signup_btn);
        signin = findViewById(R.id.ac_signin);
        fAuth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError(getString(R.string.signUpEmailInvalid));
                    mEmail.setFocusable(true);
                }
                else if(TextUtils.isEmpty(email)){
                    mEmail.setError(getString(R.string.signUpEmptyEmail));
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError(getString(R.string.signUpEmptyPassword));
                    return;
                }
                if(password.length()<6){
                    mPassword.setError(getString(R.string.signUpPasswordLen));
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = fAuth.getCurrentUser();
                            String email = user.getEmail();
                            String uid = user.getUid();
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put(getString(R.string.signInEmail),email);
                            hashMap.put(getString(R.string.signInUid), uid);
                            hashMap.put(getString(R.string.signInName),getString(R.string.signInBlank));
                            hashMap.put(getString(R.string.signInPhone),getString(R.string.signInBlank));
                            hashMap.put(getString(R.string.signInImage),getString(R.string.signInBlank));
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference(getString(R.string.signInPath));
                            reference.child(uid).setValue(hashMap);
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(SignUpActivity.this, getString(R.string.signUpCreationFail),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}