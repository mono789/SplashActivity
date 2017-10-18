package com.juan.splashactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PerfilActivity extends AppCompatActivity {
    String correoR,contrasenaR,nombre, correo,id, profileimg;
    TextView tCorreo, tNombre;
    boolean flag=false;
    private String opc;
    ImageView imageView;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        tCorreo= (TextView) findViewById(R.id.tCorreo) ;
        tNombre= (TextView) findViewById(R.id.tNombre);
        imageView= (ImageView) findViewById(R.id.imgProfile);
        SharedPreferences prefs = getSharedPreferences("Preferences1",0);
        opc=prefs.getString("opc",opc);

        if(opc.equals("1")){
            correoR= prefs.getString("correo","");
            tCorreo.setText(correoR);
        }
        if(opc.equals("2")){
            nombre= prefs.getString("nombre","");
            tNombre.setText(nombre);
            correo=prefs.getString("correo","");
            tCorreo.setText(correo);
            profileimg=prefs.getString("profileimg","");
            Picasso.with(PerfilActivity.this).load(profileimg).into(imageView);

        }
        if(opc.equals("3")){
            nombre= prefs.getString("nombre","");
            tNombre.setText(nombre);
            correo=prefs.getString("correo","");
            tCorreo.setText(correo);
            profileimg=prefs.getString("profileimg","");
            Picasso.with(PerfilActivity.this).load(profileimg).into(imageView);

        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        Intent intent;
        switch (id){
            case R.id.mPrincipal:
                intent= new Intent(PerfilActivity.this,MainActivity.class);
                if(opc.equals("1")){
                    intent.putExtra("correo",correoR);
                    intent.putExtra("contrasena",contrasenaR);
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();
                }
                if(opc.equals("2")){
                    intent.putExtra("opc",opc);
                    intent.putExtra("profileimg",profileimg);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("correo",correo);
                    startActivity(intent);
                    finish();
                }
                if(opc.equals("3")){
                    intent.putExtra("opc",opc);
                    intent.putExtra("profileimg",profileimg);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("correo",correo);
                    startActivity(intent);
                    finish();
                }


                break;
            case R.id.mCerrar:
                intent= new Intent(PerfilActivity.this,LoginActivity.class);
                if(opc.equals("1")){
                    intent.putExtra("correo",correoR);
                    intent.putExtra("contrasena",contrasenaR);
                    intent.putExtra("opc",opc);
                    setResult(RESULT_OK,intent);
                    startActivity(intent);
                    finish();
                    break;
                }
                if(opc.equals("2")){
                    LoginManager.getInstance().logOut();
                    intent.putExtra("opc",opc);
                    setResult(RESULT_OK,intent);
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

