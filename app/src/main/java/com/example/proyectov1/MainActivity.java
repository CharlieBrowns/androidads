package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String url = "https://aplicationapp.herokuapp.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void registrarse(View view) {
        Intent intent = new Intent(this,RegistrarseActivity.class);
        startActivity(intent);

    }

    public void ingresar(View view){
        EditText EContrasena = (EditText) findViewById(R.id.Contrasena);
        String Contrasena1 = EContrasena.getText().toString();
        final Users usuario = new Users();
        usuario.setPassword(Contrasena1);

        //Toast.makeText(getBaseContext(),"BIENVENIDO: "+ usuario.getPassword(), Toast.LENGTH_LONG).show();
        newClient(usuario);

    }
    public void respuesta(String rpt) {
        Toast.makeText(getBaseContext(),"Datos: "+ rpt, Toast.LENGTH_LONG).show();
        Log.d("respuestaServidor", rpt);
    }
        public void newClient(final Users users) {
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
                params.put("password", users.getPassword());
                //Log.d("String", users.getDNI());

                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
