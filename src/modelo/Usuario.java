/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author josti
 */
public class Usuario {
    
    private String nombre;
    private String correo;
    private String celular;
    private ArrayList<TProvincia> provincias;

    public Usuario() {
    }
    
    /**
     * Constructor sobrecargado 
     * @param nombre: Dato String
     * @param correo: Dato String
     * @param celular: Dato String
     * @param provincias: Dato ArrayList tipo TProvincia
     */
    public Usuario(String nombre, String correo, String celular, ArrayList<TProvincia> provincias) {
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.provincias = provincias;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public ArrayList<TProvincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(ArrayList<TProvincia> provincias) {
        this.provincias = provincias;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", correo=" + correo + ", celular=" + celular + ", provincias=" + provincias + '}';
    }

    
    
    
    
}
