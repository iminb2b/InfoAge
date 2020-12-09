package nhung.nguyen.infoage.Student;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import nhung.nguyen.infoage.HelpAndSupportActivity;
import nhung.nguyen.infoage.R;
import nhung.nguyen.infoage.Login.SettingActivity;
import nhung.nguyen.infoage.Teacher.TeacherActivity;

public class ClassDetail extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ListView listView;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    ArrayList<String> classes = new ArrayList<>();
    ArrayList<String> classid = new ArrayList<>();
    String cid, pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.homeItem);
        pos =getIntent().getStringExtra("lang");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(pos);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classes.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                   classes.add(dataSnapshot.child("className").getValue().toString());
                   classid.add(dataSnapshot.child("classid").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView= (ListView)findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classes);
        listView.setAdapter(adapter);
        onCreateOptionsMenu(toolbar.getMenu());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassDetail.this, ApplyClass.class);
                intent.putExtra("classid",classid.get(position));
                intent.putExtra("lang",pos);
                startActivity(intent);
            }
        });
     //   FirebaseDatabase database = FirebaseDatabase.getInstance();
      //  DatabaseReference myRef = database.getReference(lang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.studentSearch));
        Toast.makeText(getApplicationContext(),getString(R.string.studentHello),Toast.LENGTH_LONG).show();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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


}
