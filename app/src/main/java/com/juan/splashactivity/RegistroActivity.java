package com.juan.splashactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private String correo, contrasena, repcontrasena;
    private EditText eCorreo, eContrasena, eRepContrasena;
    private boolean flag;
    private String opc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        eCorreo= (EditText) findViewById(R.id.eCorreo);
        eContrasena= (EditText) findViewById(R.id.eContrasena);
        eRepContrasena= (EditText) findViewById(R.id.eRepContrasena);
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void registrar(View view) {
        correo=eCorreo.getText().toString();
        contrasena=eContrasena.getText().toString();
        repcontrasena= eRepContrasena.getText().toString();
        if(correo.isEmpty() || contrasena.isEmpty() || repcontrasena.isEmpty()){
            Toast.makeText(this, "Faltan campos por completar.", Toast.LENGTH_SHORT).show();
        }
        else{
            if (!validarEmail(correo)){
                eCorreo.setError("Email no válido");
            }
            else{
                if(contrasena.equals(repcontrasena)){
                    flag=true;
                    opc="1";
                    Intent intent= new Intent();
                    intent.putExtra("correo",correo);
                    intent.putExtra("contrasena",contrasena);
                    intent.putExtra("repcontrasena",repcontrasena);
                    intent.putExtra("flag",flag);
                    intent.putExtra("opc",opc);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    Toast.makeText(this, "Las contraseñas no son iguales.", Toast.LENGTH_SHORT).show();
                    eContrasena.setText("");
                    eRepContrasena.setText("");
                    contrasena="";
                    repcontrasena="";
                }
            }



        }

    }


}
