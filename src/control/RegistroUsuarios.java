/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import modelo.Sismo;
import modelo.TProvincia;
import modelo.Usuario;

/**
 *
 * @author josti
 */
public class RegistroUsuarios {
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    
    /**
     * Crea usuarios y los agrega al ArrayList usuarios
     * @param nombre: Dato String
     * @param correo: Dato String
     * @param celular: Dato String
     * @param provincias: ArrayList de tipo TProvincia
     */
    public void agregarUsuarios(String nombre, String correo, String celular, ArrayList<TProvincia> provincias){
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setCelular(celular);
        usuario.setProvincias(provincias);
        
        usuarios.add(usuario);
        //System.out.println(usuario);
    }
    
    /**
     * Lee el archivo Excel de usuarios y los agrega
     */
    public void LeerArchivosExcel(){
        try{
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("C:/Users/josti/Documents/Usuarios.xlsx"));
            XSSFSheet sheet = wb.getSheetAt(0);

            int rows = sheet.getLastRowNum() + 1;

            for (int i = 1; i < rows; ++i) {
                XSSFRow row = sheet.getRow(i);

                XSSFCell nombre = row.getCell(0);
                XSSFCell correo = row.getCell(1);
                XSSFCell celular = row.getCell(2);
                ArrayList<TProvincia> provincias = new ArrayList<TProvincia>();
                
                //For que recorre las celdas de las provincias 
                // y verifica cuáles tiene asignadas
                for (int j = 3; j < 10; ++j){
                    XSSFCell provin = row.getCell(j);
                    
                    if (provin == null){
                        //esta celda está vacía
                        break;
                    }
                    //Switch para asignar el enum de la provincia que está en la celda
                    switch (provin.getStringCellValue()){
                        case "SAN_JOSE":
                            provincias.add(TProvincia.SAN_JOSE);
                            break;
                        case "ALAJUELA":
                            provincias.add(TProvincia.ALAJUELA);
                            break;
                        case "LIMON":
                            provincias.add(TProvincia.LIMON);
                            break;
                        case "GUANACASTE":
                            provincias.add(TProvincia.GUANACASTE);
                            break;
                        case "HEREDIA":
                            provincias.add(TProvincia.HEREDIA);
                            break;
                        case "CARTAGO":
                            provincias.add(TProvincia.CARTAGO);
                            break;
                        case "PUNTARENAS":
                            provincias.add(TProvincia.PUNTARENAS);
                            break;
                    }
                    
                }
                //Se llama a la función para que cree y agregue un usuario
                agregarUsuarios(nombre.getStringCellValue(), correo.getStringCellValue(), celular.getStringCellValue(), provincias);
            }

        } catch (Exception e) {
        e.getMessage();
        }
    }
    
    /**
     * Busca a qué usuario se le debe enviar el correo dependiendo del epicentro del sismo creado
     * @param elSismo: Dato tipo Sismo 
     */
    public void correo(Sismo elSismo){
        for (Usuario actual:usuarios){
            if (actual.getProvincias().contains(elSismo.getEpicentro())){
                enviarCorreo(actual.getCorreo(), actual.getNombre(), elSismo);
            }
        }
    }
    
    /**
     * Envía el correo electrónico al usuario
     * @param receptor: Dato String
     * @param nombre: Dato String
     * @param elSismo: Dato tipo Sismo
     * @return true si se envia el correo electrónico o false en caso contrario
     */
    public boolean enviarCorreo(String receptor, String nombre, Sismo elSismo){ 
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            //props.put("mail.smtp.ssl.trust", "*");

            Session session = Session.getDefaultInstance(props);

            String emisor = "pooequipo@gmail.com";
            String clave = "//equipo10";
            
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(elSismo.getFecha());
            
            String asunto = "Alerta de sismo";
            String mensaje = "Estimado usuario(a): "+ nombre +
                    "\nSe le informa que ha ocurrido un sismo el día "+ fecha +
                    " con hora "+ elSismo.getInstante() +". Dicho sismo presenta una magnitud de "+ elSismo.getMagnitud() +
                    " y tiene como epicentro "+ elSismo.getEpicentro() + "\nA continuación, se presentan más detalles:\n"+
                    "Descripción: "+ elSismo.getDescripcion()+
                    "\nProfundidad: " + elSismo.getProfundidad() + " km\n"+
                    "Origen: " + elSismo.getOrigen() +
                    "\nTipo de magnitud: "+ elSismo.getTipoMagnitud()+
                    "\nEscala: "+ elSismo.getEscala() +
                    "\nLocalización: "+ elSismo.getLocalizacion()+
                    "\nLatitud: "+ elSismo.getLatitud() + 
                    "\nLongitud: "+ elSismo.getLongitud();

            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(emisor));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            
            Transport transporte = session.getTransport("smtp");
            transporte.connect(emisor, clave);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            
            System.out.println("Correo enviado a " + nombre);
            return true;
        } catch (AddressException ex) {
            Logger.getLogger(RegistroUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MessagingException ex) {
            Logger.getLogger(RegistroUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
