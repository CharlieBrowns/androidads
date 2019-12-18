package com.example.proyectov1;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {
    private String url = "https://aplicationapp.herokuapp.com/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void sesion(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    public void registrar(View view){
        EditText Enombres = (EditText) findViewById(R.id.Nombres);
        String nombres = Enombres.getText().toString();

        EditText Eapellidos = (EditText) findViewById(R.id.Apellidos);
        String apellidos = Eapellidos.getText().toString();

        EditText EDNI = (EditText) findViewById(R.id.DNI);
        String dni = EDNI.getText().toString();

        EditText Epassword = (EditText) findViewById(R.id.Password);
        String contrasena = Epassword.getText().toString();

        EditText REpassword = (EditText) findViewById(R.id.RePassword);
        String REcontrasena = REpassword.getText().toString();

        final Users usuariosR = new Users();
        usuariosR.setNombres(nombres);
        usuariosR.setApellidos(apellidos);
        usuariosR.setDNI(dni);
        usuariosR.setPassword(contrasena);

        //Toast.makeText(getBaseContext(),"BIENVENIDO: "+ usuario.getPassword(), Toast.LENGTH_LONG).show();
        newRegister(usuariosR);

    }
    public void respuesta(String rpt) {
        Toast.makeText(getBaseContext(),"Datos: "+ rpt, Toast.LENGTH_LONG).show();
        Log.d("respuestaServidor", rpt);
    }
    public void newRegister(final Users users) {
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {


                    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        Log.d("bann", response);
                        Toast.makeText(getBaseContext(),"BIENVENIDO: "+ response, Toast.LENGTH_LONG).show();
                        respuesta(response);

                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                //Log.d("String", users.getDNI());
                params.put("nombres", users.getNombres());
                params.put("apellidos", users.getApellidos());
                params.put("DNI", users.getDNI());
                params.put("password", users.getPassword());

                //Log.d("String", users.getDNI());

                return params;
            }
        };
        MySingleton.getInstance(RegistrarseActivity.this).addToRequestQueue(jsonObjectRequest);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
