/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ersolano
 */
public class Sismo {
    private Date fecha;
    private String instante;
    private double profundidad;
    private TOrigenSismo origen;
    private double magnitud;
    private TMagnitud tipoMagnitud;
    private TEscala escala;
    private Localizacion localizacion;
    private double latitud;
    private double longitud;
    private String descripcion;
    private TProvincia epicentro;
    private int id;

    public Sismo() {
    }
    
    /**
     * Constructor sobrecargado
     * @param fecha :Dato Date
     * @param instante: Dato String
     * @param profundidad: Dato double
     * @param origen: Dato tipo TOrigenSismo
     * @param magnitud: Dato double
     * @param tipoMagnitud: Dato tipoMagnitud
     * @param escala: Dato tipo TEscala
     * @param localizacion: Dato tipo Localizacion
     * @param latitud: Dato double
     * @param longitud: Dato double
     * @param descripcion: Dato String
     * @param epicentro: Dato TProvincia
     * @param id:Dato entero
     */
    public Sismo(Date fecha, String instante, double profundidad, TOrigenSismo origen, 
            double magnitud, TMagnitud tipoMagnitud, TEscala escala, Localizacion localizacion, 
            double latitud, double longitud, String descripcion, TProvincia epicentro, int id) {
        this.fecha = fecha;
        this.instante = instante;
        this.profundidad = profundidad;
        this.origen = origen;
        this.magnitud = magnitud;
        this.tipoMagnitud = tipoMagnitud;
        this.escala = escala;
        this.localizacion = localizacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.epicentro = epicentro;
        this.id = id;
        
    }
    
    /**
     * Constructor sobrecargado
     * @param fecha: Dato Date
     * @param instante: Dato String
     * @param profundidad: Dato double
     * @param origen: Dato TOrigenSismo
     * @param magnitud: Dato double
     * @param localizacion: Dato tipo Localizacion 
     * @param latitud: Dato double
     * @param longitud: Dato double
     * @param descripcion: Dato String
     * @param epicentro: Dato TProvincia
     * @param id: Dato entero
     */
    public Sismo (Date fecha, String instante, double profundidad, TOrigenSismo origen, 
            double magnitud, Localizacion localizacion, double latitud, double longitud, 
            String descripcion, TProvincia epicentro,int id) {
        
        this.fecha = fecha;
        this.instante = instante;
        this.profundidad = profundidad;
        this.origen = origen;
        this.magnitud = magnitud;
        this.localizacion = localizacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.epicentro = epicentro;
        this.id = id;
    }

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getInstante() {
        return instante;
    }

    public void setInstante(String instante) {
        this.instante = instante;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }

    public TOrigenSismo getOrigen() {
        return origen;
    }

    public void setOrigen(TOrigenSismo origen) {
        this.origen = origen;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(double magnitud) {
        this.magnitud = magnitud;
    }

    public Localizacion getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion localizacion) {
        this.localizacion = localizacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TProvincia getEpicentro() {
        return epicentro;
    }

    public void setEpicentro(TProvincia epicentro) {
        this.epicentro = epicentro;
    }

    public TMagnitud getTipoMagnitud() {
        return tipoMagnitud;
    }

    public void setTipoMagnitud(TMagnitud tipoMagnitud) {
        this.tipoMagnitud = tipoMagnitud;
    }

    public TEscala getEscala() {
        return escala;
    }

    public void setEscala(TEscala escala) {
        this.escala = escala;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sismo other = (Sismo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        String fecha1 = new SimpleDateFormat("dd-MM-yyyy").format(fecha);
        return "Sismo{" + "fecha=" + fecha1 + ", instante=" + instante + ", profundidad=" 
                + profundidad + ", origen=" + origen + ", magnitud=" + magnitud + ", tipoMagnitud="
                + tipoMagnitud + ", escala=" + escala + ", localizacion=" + localizacion + ", latitud=" 
                + latitud + ", longitud=" + longitud + ", descripcion=" + descripcion + ", epicentro=" 
                + epicentro + ", id=" + id + '}';
    }

    
}

