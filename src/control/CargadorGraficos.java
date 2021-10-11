/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import modelo.Sismo;
import modelo.TMagnitud;
import modelo.TOrigenSismo;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import vista.VtnInfoEstadistica;

/**
 *
 * @author josti
 */
public class CargadorGraficos {
    
    private RegistroDeSismos elRegistro = new RegistroDeSismos();
    private ArrayList<Sismo> sismos = new ArrayList<Sismo>();
    
    private String nombreArchivo = "Registro_de_Sismos.xlsx";
    private String rutaArchivo = "C:\\Users\\josti\\Documents\\" + nombreArchivo;
    
    /**
     * Lee el archivo Excel y almacena los datos en el ArrayList sismos
     */
    public void llamarAlExcel() {
        try {
            sismos = elRegistro.LeerExcel(rutaArchivo);
        } catch (IOException ex) {
            Logger.getLogger(VtnInfoEstadistica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crea el gráfico de cantidad de sismos por meses y lo almacena en un ChartPanel
     * @param año: Dato del año escogido para hacer el gráfico
     * @return Un panel tipo ChartPanel que contiene el gráfico 
     * @throws IOException 
     */
    public ChartPanel graficoMeses(int año) throws IOException{
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();

        int enero = 0;
        int febrero = 0;
        int marzo = 0;
        int abril = 0;
        int mayo = 0;
        int junio = 0;
        int julio = 0;
        int agosto = 0;
        int setiembre = 0;
        int octubre = 0;
        int noviembre = 0;
        int diciembre = 0;

        for (Sismo actual: sismos){
            if (actual.getFecha().getYear() == año){
                switch (actual.getFecha().getMonth()){
                    case 11 -> diciembre ++;
                    case 10 -> noviembre ++;
                    case 9 -> octubre ++;
                    case 8 -> setiembre ++;
                    case 7 -> agosto ++;
                    case 6 -> julio ++;
                    case 5 -> junio ++;
                    case 4 -> mayo ++;
                    case 3 -> abril ++;
                    case 2 -> marzo ++;
                    case 1 -> febrero ++;
                    case 0 -> enero ++;
                }
            }
        }
        
        datos.setValue(enero, "Enero", "");
        datos.setValue(febrero, "Febrero", "");
        datos.setValue(marzo, "Marzo", "");
        datos.setValue(abril, "Abril", "");
        datos.setValue(mayo, "Mayo", "");
        datos.setValue(junio, "Junio", "");
        datos.setValue(julio, "Julio", "");
        datos.setValue(agosto, "Agosto", "");
        datos.setValue(setiembre, "Septiembre", "");
        datos.setValue(octubre, "Octubre", "");
        datos.setValue(noviembre, "Noviembre", "");
        datos.setValue(diciembre, "Diciembre", "");
        
        JFreeChart grafico = ChartFactory.createBarChart3D("Cantidad de sismos por mes durante el año " + (año + 1900), 
                "Meses","Cantidad de sismos",datos, 
                PlotOrientation.VERTICAL, true, true, false);
        
        ChartPanel panel = new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(700,600));
        
        return panel;
    }
    
    /**
     * Crea el gráfico de cantidad de sismos por provincia
     * @return Un panel tipo ChartPanel que contiene el gráfico
     * @throws IOException 
     */
    public ChartPanel graficoSismosProvincia() throws IOException{
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        
        int sanJose = 0;
        int guanacaste = 0;
        int heredia = 0;
        int alajuela = 0;
        int cartago = 0;
        int limon = 0;
        int puntarenas = 0;
        int sin_asignar = 0; //Ver si se queda este o se quita
              
        for (Sismo actual: sismos){
            switch (actual.getEpicentro()){
                case ALAJUELA -> alajuela ++;
                case CARTAGO -> cartago ++;
                case GUANACASTE -> guanacaste ++;
                case HEREDIA -> heredia ++;
                case LIMON -> limon ++;
                case PUNTARENAS -> puntarenas ++;
                case SAN_JOSE -> sanJose ++;
                case SIN_ASIGNAR -> sin_asignar ++;
            } 
        }
        
        datos.setValue(sanJose, "San José", "");
        datos.setValue(alajuela, "Alajuela", "");
        datos.setValue(cartago, "Cartago", "");
        datos.setValue(heredia, "Heredia", "");
        datos.setValue(guanacaste, "Guanacaste", "");
        datos.setValue(puntarenas, "Puntarenas", "");
        datos.setValue(limon, "Limón", "");
        datos.setValue(sin_asignar, "Sin asignar", "");
        
        JFreeChart grafico = ChartFactory.createBarChart3D("Cantidad de sismos por provincia", 
                "Provincias","Cantidad de sismos",datos, 
                PlotOrientation.VERTICAL, true, true, false);
        
        ChartPanel panel = new ChartPanel(grafico);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(700,600));
        
        return panel;
    }
    
    /**
     * Crea el gráfico de cantidad de sismos por tipo de origen
     * @return Un panel tipo ChartPanel que contiene el gráfico
     * @throws IOException 
     */
    public ChartPanel graficoPorOrigen() throws IOException{
        
        DefaultPieDataset datos = new DefaultPieDataset();

        int subduccion = 0;
        int choque = 0;
        int tectonico = 0;
        int intra = 0;
        int deformacion = 0;
        
        for (int i = 0; i < sismos.size(); i++) {
            Sismo sis = (Sismo) sismos.get(i);
            TOrigenSismo origen = sis.getOrigen();
            
            if (null != origen) switch (origen) {
                case SUBDUCCION -> subduccion++;
                case CHOQUE_DE_PLACAS -> choque++;
                case TECTONICO_POR_FALLA_LOCAL -> tectonico++;
                case INTRAPLACA -> intra++;
                case DEFORMACION_INTERNA -> deformacion++;
                default -> {
                }
            }
        }

        datos.setValue("Subducción" , subduccion);
        datos.setValue("Choque de placas", choque);
        datos.setValue("Tectónico por falla local", tectonico);
        datos.setValue("Intraplaca", intra);
        datos.setValue("Deformación interna", deformacion);

        JFreeChart graficoPie = ChartFactory.createPieChart("Cantidad de sismos por tipo de origen",
            datos, true, true, true);

        ChartPanel panelPie = new ChartPanel(graficoPie);
        panelPie.setMouseWheelEnabled(true);
        panelPie.setPreferredSize(new Dimension(700,600));
        return panelPie;
    }
    
    /**
     * Crea el gráfico de cantidad de sismos por tipo de magnitud
     * @return Un modelo de tabla cargado con los datos
     * @throws IOException 
     */
    public DefaultTableModel graficoMagnitud() throws IOException{
        
        Object [] encabezado = {"Micro", "Menor","Ligero","Moderado","Fuerte","Mayor","Gran","Épico"};
        DefaultTableModel dtm = new DefaultTableModel(encabezado,1);
        
        int micro = 0;
        int menor = 0;
        int ligero = 0;
        int moderado = 0;
        int fuerte = 0;
        int mayor = 0;
        int gran = 0;
        int epico = 0;
      
        for (int i = 0; i < sismos.size(); i++) {
            Sismo sis = (Sismo) sismos.get(i);
            TMagnitud mag = sis.getTipoMagnitud();
            
            if (null != mag) switch (mag) {
                case MICRO -> micro++;
                case MENOR -> menor++;
                case LIGERO -> ligero++;
                case MODERADO -> moderado++;
                case FUERTE -> fuerte++;
                case MAYOR -> mayor++;
                case GRAN -> gran++;
                case EPICO -> epico++;
                default -> {
                }
            }
            
        }
        dtm.setValueAt(micro, 0, 0);
        dtm.setValueAt(menor, 0, 1);
        dtm.setValueAt(ligero, 0, 2);
        dtm.setValueAt(moderado, 0, 3);
        dtm.setValueAt(fuerte, 0, 4);
        dtm.setValueAt(mayor, 0, 5);
        dtm.setValueAt(gran, 0, 6);
        dtm.setValueAt(epico, 0, 7);
        return dtm; 
    }
    
    /**
     * Crea el gráfico de cantidad de sismos según las fechas indicadas
     * @param diaInicio: Día en el que se inicia el conteo de sismos
     * @param mesInicio: Mes en el que se inicia el conteo de sismos
     * @param diaFinal: Día en el que se detiene el conteo de sismos
     * @param mesFinal: Mes en el que se detiene el conteo de sismos
     * @return Un modelo de tabla con datos cargados
     * @throws IOException 
     */
    public DefaultTableModel graficoFechas(int diaInicio, int mesInicio, int diaFinal, int mesFinal) throws IOException{
        
        Object [] encabezado = {"Fecha inicio", "Fecha final","Cantidad"};
        DefaultTableModel dtm = new DefaultTableModel(encabezado,1);
        
        int cont = 0;

        for (int i = 0; i < sismos.size(); i++) {
            Sismo sis = (Sismo) sismos.get(i);
            Date fecha = sis.getFecha();
            int dia = fecha.getDate();
            int mes = fecha.getMonth();
            if (dia >= diaInicio && mes >= mesInicio && dia <= diaFinal && mes <= mesFinal) {
                    cont++;
            }
        }
        
        dtm.setValueAt(diaInicio + "/" + (mesInicio +1), 0, 0);
        dtm.setValueAt(diaFinal + "/" + (mesFinal +1), 0, 1);
        dtm.setValueAt(cont, 0, 2);
        return dtm;
    }
    
}
