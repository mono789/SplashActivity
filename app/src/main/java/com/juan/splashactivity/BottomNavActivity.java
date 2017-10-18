package com.juan.splashactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BottomNavActivity extends AppCompatActivity {

    private TextView mTextMessage;
    FragmentManager fm;
    FragmentTransaction ft;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fm= getSupportFragmentManager();
            ft = fm.beginTransaction();

            /*switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft = fm.beginTransaction();
                    AlienFragment fragment= new AlienFragment();
                    ft.replace(R.id.content,fragment).commit();
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    ft = fm.beginTransaction();
                    AgendarFragment fragment2= new AgendarFragment();
                    ft.replace(R.id.content,fragment2).commit();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    ft = fm.beginTransaction();
                    AlienFragment fragment3= new AlienFragment();
                    ft.replace(R.id.content,fragment3).commit();
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }*/
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        PrincipalFragment framgment2= new PrincipalFragment();
        ft.add(R.id.content, framgment2).commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
