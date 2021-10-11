/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import modelo.Sismo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
//import sismo.RegistroDeSismos;

/**
 *
 * @author ersolano
 */
public class Cargador {
 
    /**
     * Carga los datos del ArrayList de sismos en un modelo de tabla
     * @param lista: ArrayList que contiene los sismos
     * @return Un modelo de tabla con datos cargados
     */
    public static DefaultTableModel cargarSismos(ArrayList<Sismo> lista){

        Object [] encabezado = {"Id","Fecha","Hora Local","Magnitud","Escala(Rister o Momento)","Tipo de Magnitud",
            "Profundidad en km","Epicentro","Descipción","Localizacion","Origen","Latitud","Longitud","Ver"};
        DefaultTableModel dtm = new DefaultTableModel(encabezado, lista.size());
        for (int i = 0; i < lista.size(); i++) {
            Sismo sismo = lista.get(i);
            String fecha1 = new SimpleDateFormat("dd-MM-yyyy").format(sismo.getFecha());
            dtm.setValueAt(sismo.getId(),i,0);
            dtm.setValueAt(fecha1, i, 1);
            dtm.setValueAt(sismo.getInstante(), i, 2);
            dtm.setValueAt(sismo.getMagnitud(), i, 3);
            dtm.setValueAt(sismo.getEscala(), i, 4);
            dtm.setValueAt(sismo.getTipoMagnitud(), i, 5);
            dtm.setValueAt(sismo.getProfundidad(), i, 6);
            dtm.setValueAt(sismo.getEpicentro(), i, 7);
            dtm.setValueAt(sismo.getDescripcion(), i, 8);
            dtm.setValueAt(sismo.getLocalizacion(), i, 9);
            dtm.setValueAt(sismo.getOrigen(), i, 10);
            dtm.setValueAt(sismo.getLatitud(), i, 11);
            dtm.setValueAt(sismo.getLongitud(), i, 12);
            dtm.setValueAt("Mapa", i, 13);
            
            
        }
        return dtm;
   }
    
    /**
     * Carga los años de los sismos en un modelo de combo box
     * @param sismos: ArrayList que contiene los sismos
     * @return Un modelo de combo box con datos cargados
     */
    public static DefaultComboBoxModel cargarAños(ArrayList<Sismo> sismos){
        ArrayList<Integer> años = new ArrayList<Integer>();
        
        for(Sismo actual: sismos){
            if(!años.contains(actual.getFecha().getYear())){
                años.add(actual.getFecha().getYear());
            }
        }
        
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        for(int i = 0; i < años.size(); i++){
            cbm.addElement(años.get(i) + 1900);
        }
        
        return cbm;
    }
    
     
}
