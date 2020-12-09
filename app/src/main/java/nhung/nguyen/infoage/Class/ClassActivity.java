package nhung.nguyen.infoage.Class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nhung.nguyen.infoage.R;

public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        BottomNavigationView navigationView= findViewById(R.id.bottom_menu);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        String classid = getIntent().getStringExtra("classid");
        SharedPreferences sharedPreferences = getSharedPreferences("Class",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classid",classid);
        editor.commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.content,new Dashboard()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.content,new Dashboard()).commit();

                            break;
                        case R.id.nav_group:
                            getSupportFragmentManager().beginTransaction().replace(R.id.content,new Student()).commit();

                            break;

                    }

                    return false;
                }
            };
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}