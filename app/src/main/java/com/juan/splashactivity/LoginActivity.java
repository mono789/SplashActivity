package com.juan.splashactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private String correoR, contrasenaR, repcontrasenaR, correo, contrasena;
    private EditText eCorreo, eContrasena;
    boolean flag=false;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String opc="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eCorreo= (EditText) findViewById(R.id.eCorreo);
        eContrasena= (EditText) findViewById(R.id.eContrasena);
        loginButton= (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        callbackManager= CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login exitoso.", Toast.LENGTH_SHORT).show();
                opc="2";
                goMainActivity();


            }



            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelado.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error en login.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void goMainActivity() {
        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
        if(opc.equals("1")){
            intent.putExtra("correo", correoR);
            intent.putExtra("contrasena",contrasenaR);
            intent.putExtra("opc",opc);
            startActivity(intent);
            finish();
        }
        if(opc.equals("2")){
            intent.putExtra("opc",opc);
            startActivity(intent);
            finish();
        }
        if(opc.equals("3")){
            intent.putExtra("opc",opc);
            startActivity(intent);
            finish();
        }



    }

    public void iniciar(View view) {
        // se realizan las validaciones
        correo=eCorreo.getText().toString();
        contrasena=eContrasena.getText().toString();
        if(correo.isEmpty() || contrasena.isEmpty()){
            Toast.makeText(this, "Faltan campos por completar.", Toast.LENGTH_SHORT).show();
        }
        else{
            //SI SE CUMPLEN:
            if(flag==false){
                Bundle extras= getIntent().getExtras();
                correoR= extras.getString("correo");
                contrasenaR= extras.getString("contrasena");
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



        if(requestCode== 1234 && resultCode== RESULT_OK){
            correoR= data.getExtras().getString("correo");
            contrasenaR= data.getExtras().getString("contrasena");
            repcontrasenaR= data.getExtras().getString("repcontrasena");
            flag=data.getExtras().getBoolean("flag");
            opc=data.getExtras().getString("opc");
            Log.d("correo",correoR);
            Log.d("contrasena", contrasenaR);

        }
        else{
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Registrese(View view) {
        flag= true;
        Intent intent= new Intent(LoginActivity.this, RegistroActivity.class);
        startActivityForResult(intent,1234);
    }
}
