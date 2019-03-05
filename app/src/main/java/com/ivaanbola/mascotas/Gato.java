package com.ivaanbola.mascotas;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Gato {

    private String id;
    private String nombre;
    private String raza;
    private Map<String, Boolean> stars = new HashMap<>();

    public Gato() {
    }

    public Gato(String id, String nombre, String raza) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
    }

    public Gato( String nombre, String raza) {
        this.nombre = nombre;
        this.raza = raza;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("nombre", nombre);
        result.put("raza", raza);

        return result;
    }

}
