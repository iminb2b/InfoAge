package nhung.nguyen.infoage.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import nhung.nguyen.infoage.R;
import nhung.nguyen.infoage.MainActivity.HomeActivity;

public class SignInActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    Button ggSignIn,btnSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    TextView signup;
    CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ggSignIn = findViewById(R.id.gg_btn);
        signup = findViewById(R.id.signup_intent);
        remember = findViewById(R.id.remember);
        sharedPreferences = getSharedPreferences("Remember",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final EditText username = findViewById(R.id.editTextTextPersonName);
        final EditText password = findViewById(R.id.editTextTextPassword);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken(getString(R.string.signInIDRequestToken))
                .requestEmail().build();
        //
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        ggSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });
        btnSignIn = findViewById(R.id.signup_btn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pw = password.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    username.setError(getString(R.string.signInNameError));
                    username.setFocusable(true);
                }else if(pw.length()<6){
                    password.setError(getString(R.string.signInPasswordLen));
                    password.setFocusable(true);
                }else{
                    loginUser(email,pw);
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remember.isChecked()){

                    editor.putBoolean("remember", true);
                    editor.commit();
                }else{
                    editor.putBoolean("remember", false);
                    editor.commit();
                }
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if (firebaseUser != null) {
//            startActivity(new Intent(SignInActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//
//        }
    }

    private void loginUser(String email, String pw) {
        firebaseAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
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
                         //   }

                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {

                            Toast.makeText(SignInActivity.this, getString(R.string.signInAuthFail),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInActivity.this,getString(R.string.signInOnFail),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                String s = getString(R.string.googleSignIn);
                displayToast(s);
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String email = user.getEmail();
                                    String uid = user.getUid();
                                    HashMap<Object, String> hashMap = new HashMap<>();
                                    hashMap.put(getString(R.string.signInEmail),email);
                                    hashMap.put(getString(R.string.signInUid), uid);
                                    hashMap.put(getString(R.string.signInName),user.getDisplayName());
                                    hashMap.put(getString(R.string.signInPhone),getString(R.string.signInBlank));
                                    hashMap.put(getString(R.string.signInImage),getString(R.string.signInBlank));
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference(getString(R.string.signInPath));
                                    reference.child(uid).setValue(hashMap);
                                    startActivity(new Intent(SignInActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    displayToast(getString(R.string.googleFBAS));
                                } else {
                                    displayToast(getString(R.string.googleFBAF) + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}