package org.techtown.drawer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.techtown.drawer.databinding.ActivityMainBinding;
import org.techtown.drawer.ui.Fragment1;
import org.techtown.drawer.ui.Fragment2;
import org.techtown.drawer.ui.Fragment3;

public class MainActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.menu1){
                    Toast.makeText(getApplicationContext(), "첫 번재 메뉴 선택됨", Toast.LENGTH_LONG).show();
                    onFragmentSelected(0, null);
                } else if(id == R.id.menu2){
                    Toast.makeText(getApplicationContext(), "두 번째 메뉴 선택됨", Toast.LENGTH_LONG).show();
                    onFragmentSelected(1, null);
                } else if(id == R.id.menu3){
                    Toast.makeText(getApplicationContext(), "세 번재 메뉴 선택됨", Toast.LENGTH_LONG).show();
                    onFragmentSelected(2, null);
                }

                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onFragmentSelected(int position, Bundle bundle){
        Fragment curFragment = null;

        if(position == 0 ){
            curFragment = fragment1;
            toolbar.setTitle("첫 번째 화면");
        } else if(position == 1){
            curFragment = fragment2;
            toolbar.setTitle("두 번째 화면");
        } else if(position == 2){
            curFragment = fragment3;
            toolbar.setTitle("세 번째 화면");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }
}