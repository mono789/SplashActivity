package com.juan.splashactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;

public class PerfilActivity extends AppCompatActivity {
    String correoR,contrasenaR;
    TextView tCorreo;
    boolean flag=false;
    private String opc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Bundle extras= getIntent().getExtras();
        contrasenaR= extras.getString("contrasena");
        opc=extras.getString("opc");
        if(opc.equals("1")){
            correoR= extras.getString("correo");
            tCorreo= (TextView) findViewById(R.id.tCorreo) ;
            tCorreo.setText(correoR);
        }
        if(opc.equals("2")){

        }
        if(opc.equals("3")){

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
                    startActivity(intent);
                    finish();
                }
                if(opc.equals("3")){
                    intent.putExtra("opc",opc);
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
                    intent.putExtra("opc",opc);
                    setResult(RESULT_OK,intent);
                    startActivity(intent);
                    finish();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
