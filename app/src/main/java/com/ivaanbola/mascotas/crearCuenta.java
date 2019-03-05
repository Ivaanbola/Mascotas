package com.ivaanbola.mascotas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class crearCuenta extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener estadoSesion;
    private static final String TAG = "activity_crear_cuenta";
    private Button guardar;
    private TextInputEditText nombreUsuario, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        guardar = (Button) findViewById(R.id.btnGuardar);
        nombreUsuario = (TextInputEditText) findViewById(R.id.email);
        pass = (TextInputEditText) findViewById(R.id.password_createaccount);
        estadoSesion = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if (usuario != null) {
                    Log.w(TAG, "El usuario esta Logeado" + usuario.getEmail());
                } else {
                    Log.w(TAG, "El usuario NO esta Logeado");
                }
            }
        };
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altaCuenta();
            }
        });
    }

    private void altaCuenta() {
        firebaseAuth.createUserWithEmailAndPassword(nombreUsuario.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(crearCuenta.this, "cuenta creada", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getParent(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(crearCuenta.this, "El usuario ya existe", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(crearCuenta.this, "Error" + nombreUsuario.getText().toString() + pass.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


    //tenemos que arrancar el escuchador y cerrarlo en todo el ciclo de vida de la activity

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(estadoSesion);
    }

    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(estadoSesion);
    }


}
