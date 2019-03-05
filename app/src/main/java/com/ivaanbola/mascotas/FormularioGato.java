package com.ivaanbola.mascotas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FormularioGato extends BaseActivity {


    private static final String TAG = "activity_FormularioGato";
    private static final String NODE = "gatos";
    private DatabaseReference databaseReference;

    private TextInputEditText nombre, raza;
    private String idgato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_gato);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        idgato = getIntent().getStringExtra("id");
        String nombrere = getIntent().getStringExtra("nombre");
        String razare = getIntent().getStringExtra("raza");
        if (idgato != null) {
            //Rescatar los elementos de la UI de la template
            nombre = (TextInputEditText) findViewById(R.id.nombreGato);
            raza = (TextInputEditText) findViewById(R.id.razaGato);
            //Hacer un set de la info del elemento en los elementos de la UI
            nombre.setText(nombrere);
            raza.setText(razare);
        }

    }

    public void insertar(View view) {
        nombre = (TextInputEditText) findViewById(R.id.nombreGato);
        raza = (TextInputEditText) findViewById(R.id.razaGato);
        if (idgato == null) {
            Gato gato = new Gato(databaseReference.push().getKey(), nombre.getText().toString(), raza.getText().toString());
            databaseReference.child(NODE).child(gato.getId()).setValue(gato).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.w(TAG, "Se ha insertado con exito");
                    Intent intent = new Intent(getApplicationContext(), home.class);
                    startActivity(intent);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error al insertar gato");
                        }
                    });

        } else {

            Gato gato = new Gato(idgato,nombre.getText().toString(), raza.getText().toString());

            databaseReference.child(NODE).child(idgato).setValue(gato);

            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);

        }
    }


    public void eliminar(View view) {
        databaseReference.child(NODE).child(idgato).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.w(TAG, "Registro eliminado");
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
            }
        });
    }

}

