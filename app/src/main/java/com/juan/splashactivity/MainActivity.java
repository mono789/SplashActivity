package com.juan.splashactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static com.juan.splashactivity.R.id.container;

public class MainActivity extends AppCompatActivity {

    private String correoR, contrasenaR, nombre,idf, profileimg, correo;
    private boolean flag=false;
    private String opc;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrincipalFragment fragment= new PrincipalFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, fragment);
        Bundle extras= getIntent().getExtras();
        opc=extras.getString("opc");

        if(opc.equals("1")){
            correoR= extras.getString("correo");
            contrasenaR= extras.getString("contrasena");
            Toast.makeText(this, "Login normal", Toast.LENGTH_SHORT).show();
        }
        if(opc.equals("2")){
            nombre=extras.getString("nombre");
            idf=extras.getString("id");
            profileimg=extras.getString("profileimg");
            correo=extras.getString("correo");
            Toast.makeText(this, "Login facebook", Toast.LENGTH_SHORT).show();
        }
        if(opc.equals("3")){
            nombre=extras.getString("nombre");
            profileimg=extras.getString("profileimg");
            correo=extras.getString("correo");
            Toast.makeText(this, "Login google", Toast.LENGTH_SHORT).show();
        }
        if(opc.equals("4")){

            PrincipalFragment fragment1= new PrincipalFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor, fragment1).commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        Intent intent;
        switch (id){
            case R.id.mPerfil:
                intent= new Intent(MainActivity.this,PerfilActivity.class);
                if(opc.equals("1")){
                    intent.putExtra("correo",correoR);
                    intent.putExtra("contrasena",contrasenaR);
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();
                    break;
                }
                if(opc.equals("2")){
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("opc",opc);
                    intent.putExtra("id",idf);
                    intent.putExtra("profileimg",profileimg);
                    intent.putExtra("correo",correo);
                    startActivity(intent);
                    finish();
                    break;

                }
                if(opc.equals("3")){
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("opc",opc);
                    intent.putExtra("profileimg",profileimg);
                    intent.putExtra("correo",correo);
                    startActivity(intent);
                    finish();
                    break;
                }

            case R.id.mCerrar:
                intent= new Intent(MainActivity.this,LoginActivity.class);
                if(opc.equals("1")){
                    intent.putExtra("correo",correoR);
                    intent.putExtra("contrasena",contrasenaR);
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();
                    break;
                }
                if(opc.equals("2")){
                    LoginManager.getInstance().logOut();
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();
                    break;

                }
                if(opc.equals("3")){
                    if (mGoogleApiClient.isConnected()){
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();}

                    break;
                }
        }
        return super.onOptionsItemSelected(item);
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

}
