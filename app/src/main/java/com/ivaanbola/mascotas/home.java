package com.ivaanbola.mascotas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends BaseActivity {

    private static final String NODE = "gatos";
    private DatabaseReference databaseReference;

    private ListView lista;
    private ArrayAdapter arrayAdapter;
    private List<String> gatoName;
    private List<Gato> gatitos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        lista = findViewById(R.id.lista);
        gatoName = new ArrayList<>();
        gatitos = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gatoName);
        lista.setAdapter(arrayAdapter);

        databaseReference.child(NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gatoName.clear();
                gatitos.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Gato gato = snapshot.getValue(Gato.class);
                        gatoName.add(gato.getNombre());
                        gatitos.add(gato);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String idgatos = gatitos.get(position).getId();
                String nombre = gatitos.get(position).getNombre();
                String raza = gatitos.get(position).getRaza();

                Intent intent = new Intent(getApplicationContext(), FormularioGato.class);
                intent.putExtra("id",idgatos);
                intent.putExtra("nombre",nombre);
                intent.putExtra("raza",raza);

                startActivity(intent);

                /*String idgatos = gatitos.get(position).getId();
                gatitos.remove(position);
                gatoName.remove(position);
                databaseReference.child(NODE).child(idgatos).removeValue();
                return true;*/
                return true;
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void revokeAccess(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void insertar(View v) {
        Intent intent = new Intent(getApplicationContext(), FormularioGato.class);
        startActivity(intent);
    }


}
