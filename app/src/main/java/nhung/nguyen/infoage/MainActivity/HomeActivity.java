package nhung.nguyen.infoage.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nhung.nguyen.infoage.HelpAndSupportActivity;
import nhung.nguyen.infoage.R;
import nhung.nguyen.infoage.Login.SettingActivity;
import nhung.nguyen.infoage.Student.StudentActivity;
import nhung.nguyen.infoage.Teacher.TeacherActivity;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ArrayAdapter<String> adapter;
    ImageView avatarIv;
    TextView nameTv, emailTv, phoneTv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(getString(R.string.profilePath));
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.homeItem);
        View headerview = navigationView.getHeaderView(0);
        avatarIv= headerview.findViewById(R.id.avatarIv);
        nameTv= headerview.findViewById(R.id.nameTv);
        emailTv= headerview.findViewById(R.id.emailTv);
        if(user!= null){
            Glide.with(this).load(user.getPhotoUrl()).into(avatarIv);
            nameTv.setText(user.getDisplayName());
            emailTv.setText(user.getEmail());
        }
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);}

            AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
            alert.setTitle("");
            alert.setMessage("Do you want to exit?" );
            alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.create().show();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.homeItem: break;
            case R.id.teacherItem:
                Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                startActivity(intent);
                break;
            case R.id.studentItem:
                Intent intent1 = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intent1);
                break;

            case R.id.helpAndSupportItem:
                Intent intent3 = new Intent(getApplicationContext(), HelpAndSupportActivity.class);
                startActivity(intent3);
                break;
            case R.id.setting:
                Intent intent4 = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        DatabaseReference ref = firebaseDatabase.getReference("Usermode").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean teacher = (Boolean) snapshot.child("Teacher").getValue();
                    Boolean student = (Boolean) snapshot.child("Student").getValue();
                if (teacher==false){
                    menu.removeItem(R.id.teacherItem);
                }else if(student== false){
                    menu.removeItem(R.id.studentItem);
                }
                Toast.makeText(HomeActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return super.onPrepareOptionsMenu(menu);
    }
}
