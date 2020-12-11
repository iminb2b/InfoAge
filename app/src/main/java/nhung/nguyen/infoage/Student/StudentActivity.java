package nhung.nguyen.infoage.Student;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nhung.nguyen.infoage.Login.SettingActivity;
import nhung.nguyen.infoage.MainActivity.HomeActivity;
import nhung.nguyen.infoage.R;
import nhung.nguyen.infoage.MainActivity.ProfileFragment;
import nhung.nguyen.infoage.Teacher.TeacherActivity;


public class StudentActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ListView listView;
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
        setContentView(R.layout.activity_student);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
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
       // listView= (ListView)findViewById(R.id.list_view);
      //  adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.language));
      //  listView.setAdapter(adapter);
//        onCreateOptionsMenu(toolbar.getMenu());
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(StudentActivity.this, ClassDetail.class);
//                intent.putExtra("lang",listView.getItemAtPosition(position).toString());
//                startActivity(intent);
//            }
//        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FindClass()).commit();

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.find_class:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FindClass()).commit();

                break;
            case R.id.setting:
                Intent intent3 = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.classes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AppliedClass()).commit();
                break;

            case R.id.nav_home:
                Intent intent4 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }
}