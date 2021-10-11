/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import modelo.Sismo;
import modelo.Localizacion;
import modelo.TEscala;
import modelo.TMagnitud;
import modelo.TOrigenSismo;
import modelo.TProvincia;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


/**
 *
 * @author ersolano
 */
public class Controlador {
    
    private RegistroDeSismos registro = new RegistroDeSismos();  // asociación comunicación entre el controlador y el admClientes
    
    /**
     * Constructor que lee el registro del Excel
     */
    public Controlador(){
        try {
            leerRegistro();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Lee el archivo de Excel que contiene la información de los sismos
     * @throws IOException 
     */
    public void leerRegistro() throws IOException{
        registro.LeerExcel(rutaGlobal.ruta);
    }
    
    /**
     * Carga el modelo de la tabla en la tabla correspondiente
     * @return Un modelo de tabla con datos cargados
     * @throws IOException 
     */
    public DefaultTableModel cargarSismos() throws IOException{
        return Cargador.cargarSismos(registro.getSismos());
    }
    
    /**
     * Carga el modelo del combo box en el combo box correspondiente
     * @return Un modelo de combo box con datos cargados
     * @throws IOException 
     */
    public DefaultComboBoxModel cargarAños() throws IOException{
        return Cargador.cargarAños(registro.getSismos());
    }
    
    /**
     * Recupera los datos para crear un nuevo sismo
     * @param fecha: Dato tipo Date
     * @param hora: Dato String
     * @param profundidad: Dato double
     * @param origen: Dato tipo TOrigenSismo
     * @param magnitud: Dato double
     * @param localizacion: Dato tipo Localizacion
     * @param latitud: Dato double
     * @param longitud: Dato double
     * @param descripcion: Dato String
     * @param epicentro: Dato tipo TProvincia
     * @param id: Dato int
     * @return true si se pudo crear el sismo o false en caso contrario
     */
    public boolean agregarSismo(Date fecha, String hora, double profundidad, TOrigenSismo origen, 
            double magnitud, Localizacion localizacion, 
            double latitud, double longitud, String descripcion, TProvincia epicentro,int id){
        
        Sismo unSismo = new Sismo( fecha,hora,profundidad,origen,magnitud,localizacion,latitud,longitud, 
                                  descripcion,epicentro,id);
        
        try {
            return registro.agregarSismo(unSismo);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Modifica la latiud y longitud de un sismo existente
     * @param id: Dato int que identifica el sismo
     * @param nuevaLatitud: Dato double
     * @param nuevaLongitud: Dato double
     * @return true si se modifica el sismo o false en caso contrario
     */
    public boolean modificarSismo(int id, double nuevaLatitud, double nuevaLongitud){
        try {
            leerRegistro();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            return registro.modificarSismoLocalizacion(id, nuevaLatitud, nuevaLongitud);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Modifica el epicentro de un sismo existente
     * @param id: Dato int que identifica el sismo
     * @param nuevoEpicentro: Dato tipo TProvincia
     * @return true si se modifica el sismo o false en caso contrario
     */
    public boolean modificarSismo(int id, TProvincia nuevoEpicentro){
        try {
            leerRegistro();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            return registro.modificarSismoEpicentro(id, nuevoEpicentro);
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Despliega el mapa del epicentro del sismo
     * @param latitud: Dato double 
     * @param longitud: Dato double
     */
    public void mostarMapa(String latitud, String longitud){
        if (java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try{
                    java.net.URI uri = new java.net.URI("https://www.openstreetmap.org/#map=14/" + latitud + "/" + longitud);
                    desktop.browse(uri);
                }catch (URISyntaxException | IOException ex){}
            }
        }
    }
    
    /**
     * Valida si un String se puede convertir en entero
     * @param cadena: Dato String
     * @return true si es entero o false en caso contrario
     */
    public boolean esNumero (String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    public Date formatoFecha(String cadena){
        return registro.formatoFecha(cadena);
    }

}
