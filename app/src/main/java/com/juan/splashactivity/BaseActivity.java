package com.juan.splashactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OpInterface {

    private String correoR, contrasenaR, nombre,idf, profileimg, correo;
    private boolean flag=false;
    private int inicio;
    private String opc;
    private GoogleApiClient mGoogleApiClient;
    private TextView tBaseNombre, tBaseCorreo;
    private ImageView imageView;
    FragmentManager fm;
    FragmentTransaction ft;
    Bundle args= new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        SharedPreferences prefs = getSharedPreferences("Preferences1",0);
        SharedPreferences.Editor editor= prefs.edit();
        inicio= prefs.getInt("inicio1",inicio);
        if(inicio==0) {
            Intent intent= new Intent(BaseActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();
        }
        else if(inicio==1){
            Intent intent= new Intent(BaseActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView= navigationView.getHeaderView(0);
        tBaseCorreo= (TextView) hView.findViewById(R.id.tBaseCorreo);
        tBaseNombre= (TextView) hView.findViewById(R.id.tBaseNombre);
        imageView= (ImageView) hView.findViewById(R.id.imgBaseProfile);
        nombre= prefs.getString("nombre",nombre);
        correo= prefs.getString("correo",correo);
        profileimg=prefs.getString("profileimg",profileimg);
        tBaseCorreo.setText(correo);
        tBaseNombre.setText(nombre);
        Picasso.with(getApplicationContext()).load(profileimg).resize(200,200).into(imageView);
        navigationView.setNavigationItemSelectedListener(this);

        /**********************Principal***************************************/
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        PrincipalFragment fragment = new PrincipalFragment();
        ft.replace(R.id.contenedor, fragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent= new Intent(BaseActivity.this, PerfilActivity.class);
            startActivity(intent);

        }
        if (id == R.id.nav_logout){
            SharedPreferences prefs= getSharedPreferences("Preferences1",0);
            SharedPreferences.Editor editor = prefs.edit();
            flag=prefs.getBoolean("flag",false);
            opc= prefs.getString("opc",opc);
            Intent intent= new Intent(BaseActivity.this,LoginActivity.class);
            inicio=1;
            if(opc.equals("1")){
                editor.putBoolean("flag",false);
                editor.putInt("inicio1",inicio);
                editor.commit();
                startActivity(intent);
                finish();
            }
            if(opc.equals("2")){
                editor.putInt("inicio1",inicio);
                editor.commit();
                LoginManager.getInstance().logOut();
                startActivity(intent);
                finish();


            }
            if(opc.equals("3")){
                if (mGoogleApiClient.isConnected()){
                    editor.putInt("inicio1",inicio);
                    editor.commit();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    startActivity(intent);
                    finish();}
            }


            }
        if (id == R.id.nav_principal) {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            PrincipalFragment fragment = new PrincipalFragment();
            ft.replace(R.id.contenedor, fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    public void openData() {
        SharedPreferences prefs = getSharedPreferences("Preferences1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (opc.equals("1")) {
            editor.putString("correo",correo);
            editor.putString("contrasena", nombre);
            editor.putString("opc", opc);
            editor.commit();
        }
        if (opc.equals("2")) {
            editor.putString("correo",correo);
            editor.putString("nombre", nombre);
            editor.putString("opc", opc);
            editor.putString("profileimg", profileimg);
            editor.commit();
        }
        if (opc.equals("3")) {
            editor.putString("correo",correo);
            editor.putString("nombre", nombre);
            editor.putString("opc", opc);
            editor.putString("profileimg", profileimg);;
            editor.commit();
        }

    }
    @Override
    public void perfilFragment() {
        openData();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        PerfilFragment fragment = new PerfilFragment();
        fragment.setArguments(args);
        ft.replace(R.id.contenedor, fragment).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
