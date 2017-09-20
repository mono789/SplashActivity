package com.juan.splashactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    private String correoR, contrasenaR;
    private boolean flag=false;
    private String opc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras= getIntent().getExtras();
        opc=extras.getString("opc");

        if(opc.equals("1")){
            correoR= extras.getString("correo");
            contrasenaR= extras.getString("contrasena");
            Toast.makeText(this, "Login normal", Toast.LENGTH_SHORT).show();
        }
        if(opc.equals("2")){

            Toast.makeText(this, "Login facebook", Toast.LENGTH_SHORT).show();
        }
        if(opc.equals("3")){
            Toast.makeText(this, "Login google", Toast.LENGTH_SHORT).show();
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
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();
                    break;

                }
                if(opc.equals("3")){
                    intent.putExtra("opc",opc);
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
                    intent.putExtra("opc",opc);
                    startActivity(intent);
                    finish();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
