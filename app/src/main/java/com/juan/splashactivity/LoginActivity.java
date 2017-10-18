package com.juan.splashactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private String correoR, contrasenaR, repcontrasenaR, correo, contrasena, nombre;
    private EditText eCorreo, eContrasena;
    boolean flag=false;
    private int inicio;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String opc;
    GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN=5678;
    private String id, profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Error en login", Toast.LENGTH_SHORT).show();
                    }

                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sigIn();
            }
        });

        /*******************************Login con facebook************************/
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override

            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {

                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    correo = response.getJSONObject().optString("email");
                                    Profile profile=Profile.getCurrentProfile();
                                    Datos(profile);
                                    Toast.makeText(getApplicationContext(), "Login exitoso.", Toast.LENGTH_SHORT).show();
                                    opc="2";
                                    goMainActivity();



                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    /*******************************Login con facebook************************/

    private void sigIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void goMainActivity() {
        SharedPreferences prefs = getSharedPreferences("Preferences1", 0);
        SharedPreferences.Editor editor = prefs.edit();
        Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
        if (opc.equals("1")) {
            inicio=2;
            editor.putString("correo", correo);
            editor.putString("contrasena", contrasena);
            editor.putString("opc", opc);
            editor.putBoolean("flag",flag);
            editor.putInt("inicio1",inicio);
            editor.commit();
            startActivity(intent);
            finish();
        }
        if (opc.equals("2")) {
            inicio=2;
            editor.putString("correo", correo);
            editor.putString("nombre", nombre);
            editor.putString("profileimg", profileimg);
            editor.putString("opc", opc);
            editor.putInt("inicio1",inicio);
            editor.commit();
            startActivity(intent);
            finish();
        }
        if (opc.equals("3")) {
            inicio=2;
            editor.putString("correo", correo);
            editor.putString("nombre", nombre);
            editor.putString("profileimg", profileimg);
            editor.putString("opc", opc);
            editor.putInt("inicio1",inicio);
            editor.commit();
            startActivity(intent);
            finish();
        }
    }

    public void iniciar(View view) {
        // se realizan las validaciones
        correo=eCorreo.getText().toString();
        contrasena=eContrasena.getText().toString();
        SharedPreferences prefs = getSharedPreferences("Preferences1",0);
        flag= prefs.getBoolean("falg",flag);
        if(correo.isEmpty() || contrasena.isEmpty()){
            Toast.makeText(this, "Faltan campos por completar.", Toast.LENGTH_SHORT).show();
        }
        else{
            //SI SE CUMPLEN:
            if(flag==false){

                correoR= prefs.getString("correo","");
                contrasenaR= prefs.getString("contrasena","");
                if(correo.equals(correoR) && contrasena.equals(contrasenaR)){
                    opc="1";
                    goMainActivity();
                }
                else{
                    Toast.makeText(this, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                if(correo.isEmpty() || contrasena.isEmpty()){
                    Toast.makeText(this, "Faltan campos por completar.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(correo.equals(correoR) && contrasena.equals(contrasenaR)){
                        opc="1";
                        goMainActivity();
                    }
                    else{
                        Toast.makeText(this, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if(requestCode== 1234 && resultCode== RESULT_OK){//REGISTRO
            correoR= data.getExtras().getString("correo");
            contrasenaR= data.getExtras().getString("contrasena");
            repcontrasenaR= data.getExtras().getString("repcontrasena");
            flag=data.getExtras().getBoolean("flag");
            opc=data.getExtras().getString("opc");
            Log.d("correo",correoR);
            Log.d("contrasena", contrasenaR);

        }
        else if (requestCode == RC_SIGN_IN) {//Login Google
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else{//Login Facebook
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("google", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Log.d("nombre de usuario: ",acct.getDisplayName());
            nombre=acct.getDisplayName();
            correo= acct.getEmail();
            profileimg=acct.getPhotoUrl().toString();
            opc="3";
            goMainActivity();

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(),"Error en login", Toast.LENGTH_SHORT).show();
        }
    }

    public void Registrese(View view) {
        flag= true;
        Intent intent= new Intent(LoginActivity.this, RegistroActivity.class);
        startActivityForResult(intent,1234);
    }

    private void Datos(Profile perfil){
        if(perfil!=null){
            nombre=perfil.getName();
            id=perfil.getId();
            profileimg=perfil.getProfilePictureUri(200,200).toString();
        }
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
