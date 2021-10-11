package control;

import modelo.Sismo;
import modelo.Localizacion;
import modelo.TEscala;
import modelo.TMagnitud;
import modelo.TOrigenSismo;
import modelo.TProvincia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Pamela
 */
public class RegistroDeSismos {
    
    private ArrayList<Sismo> sismos = new ArrayList();
    private RegistroUsuarios registro = new RegistroUsuarios(); //Para poder usar las funciones del registro de usuarios

    public ArrayList<Sismo> getSismos() {
        return sismos;
    }

    public void setSismos(ArrayList<Sismo> sismos) {
        this.sismos = sismos;
    }

    @Override
    public String toString() {
        return "RegistroDeSismos{" + "sismos=" + sismos + '}';
    }

    
    //FUNCIONES 
//    public void imprimirRegistro(){
//        sismos.forEach((Sismo actual) -> {
//            System.out.println( actual.toString());
//            System.out.println(actual.getLongitud());     
//        });
//    }
    
    
    /**
     * Lee el excel donde aparecen los registros de los sismos. Los añade al ArrayList
     * del registro.
     * @param ruta : Es la ubicación del archivo excel.
     * @return Un ArrayList de sismos. 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public ArrayList<Sismo> LeerExcel(String ruta) throws FileNotFoundException, IOException{
        
        String rutaArchivo = ruta;
 
        try (FileInputStream file = new FileInputStream(new File(rutaArchivo))) {
            // leer archivo excel
            XSSFWorkbook worbook = new XSSFWorkbook(file);
            //obtener la hoja que se va leer
            XSSFSheet sheet = worbook.getSheetAt(0);
            //obtener todas las filas de la hoja excel
            Iterator<Row> rowIterator = sheet.iterator();

            Row row;
            // se recorre cada fila hasta el final
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                //se obtiene las celdas por fila
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                //se recorre cada celda
                int control = 0;
                
                //Variables que se van a utilizar en la creacion del sismo.
                Date fecha = new Date();
                String hora = "12:00:00";
                double profundidad = 0;
                TOrigenSismo origen = TOrigenSismo.CHOQUE_DE_PLACAS;
                double magnitud = 0;
                TMagnitud tMagnitud = TMagnitud.EPICO;
                TEscala escala = TEscala.ML;
                Localizacion localizacion =null;
                double latitud = 0;
                double longitud = 0;
                String descripcion = "";
                TProvincia epicentro = null;
                int id = 0;
                //System.out.println(tMagnitud);

                while (cellIterator.hasNext()) {
                    // se obtiene la celda en específico y se la imprime
                    cell = cellIterator.next();
                    switch (cell.getCellType()){   
                        //En el caso de que la celda sea de tipo numerico.
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell) ){
                                //Se usan el indice de cada columna
                                if(cell.getColumnIndex() == 1){
                                    fecha = cell.getDateCellValue();
                                    Date nuevo = new Date();
                                    //System.out.println("Entra al date de fecha"+cell.getDateCellValue());
                                    
                                    
                                }else if(cell.getColumnIndex() == 2){
                                    //System.out.println("Entra al date de hora"+cell.getDateCellValue());
                                    Date time=cell.getDateCellValue();
                                    hora = new SimpleDateFormat("H:mm:ss").format(time); 
                                }    
                            }else{
                                if(cell.getColumnIndex() == 11){
                                    //System.out.println("Entra al numero de lati"+ cell.getNumericCellValue());
                                latitud = cell.getNumericCellValue();
                                
                                }else if (cell.getColumnIndex() == 12) {
                                    //System.out.println("Entra al numero de longi"+cell.getNumericCellValue());
                                longitud = cell.getNumericCellValue();
                                
                                }else if(cell.getColumnIndex() == 6){
                                    //System.out.println("Entra al numero de profu"+cell.getNumericCellValue());
                                    profundidad = cell.getNumericCellValue();
                                      
                                }else if (cell.getColumnIndex() == 3){
                                    //System.out.println("Entra al numero de magni"+cell.getNumericCellValue());
                                    magnitud = cell.getNumericCellValue();
                                    
                                }else if (cell.getColumnIndex() == 0){
                                    //Entra en la columna 0 del id
                                    id = (int)cell.getNumericCellValue();
                                    
                                }else{
                                    System.out.println("NO ENTRA");
                                }   
                            }
                            break;
                        //celda sea de tipo string    
                        case Cell.CELL_TYPE_STRING:
                            if(cell.getColumnIndex() == 3){                               
                              magnitud = Double.parseDouble(cell.getStringCellValue()); 
                              
                            }else if (cell.getColumnIndex() == 4){                               
                                escala = TEscala.valueOf(cell.getStringCellValue());
                                      
                            }else if (cell.getColumnIndex() == 5){
                                tMagnitud = TMagnitud.valueOf(cell.getStringCellValue());
                                
                            } else if(cell.getColumnIndex() == 6){
                                profundidad = Double.parseDouble(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 7){
                                epicentro = TProvincia.valueOf(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 8){
                                descripcion = cell.getStringCellValue();
                                
                            }else if(cell.getColumnIndex() == 9){
                                localizacion = Localizacion.valueOf(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 10){
                                origen = TOrigenSismo.valueOf(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 11){   
                                latitud = Double.parseDouble(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 12){
                                longitud = Double.parseDouble(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 1){
                                //Date fecha1 = new Date(cell.getStringCellValue());
                                fecha = formatoFecha(cell.getStringCellValue());
                                
                            }else if(cell.getColumnIndex() == 2){
                                hora = cell.getStringCellValue();
                                
                            }else if(cell.getColumnIndex() == 0){
                                id = Integer. parseInt(cell.getStringCellValue());
                            }else{
                                System.out.println("Entra al string de NADA"+cell.getStringCellValue());
                            //control = 15;
                            }
                            break;
                    }
                }
                if(magnitud != 0 && (latitud != 0)){  
                    Sismo unSismo = new Sismo( fecha, hora, profundidad, origen, magnitud, tMagnitud,  
                    escala, localizacion, latitud, longitud, descripcion, epicentro,id);
                    sismos.add(unSismo);
                }     
            }
        } catch (Exception e) {
            e.getMessage();
            //return sismos;
            }
        return sismos;
    }
    
    /**
     * Añade un sismo al ArrayList del registro y al excel.
     * @param unSismo : Un sismo ya creado.
     * @return
     * @throws InvalidFormatException 
     */
    public boolean agregarSismo (Sismo unSismo) throws InvalidFormatException{
        Sismo unSismo1 = darTMagnitud(darEscala(unSismo));
        for (int i = 0; i < sismos.size(); i++) {          // forl + TAB
            Sismo actual = sismos.get(i);
            if (actual.getId() == unSismo1.getId()){
                    System.out.println("Son iguales");
                    return false;   
            }
        }
        sismos.add(unSismo1);
        //System.out.println(unSismo1.toString());
        agregarSismoExcel(unSismo1);
        //Función para notificar a los usuarios sobre el sismo agregado
        registro.LeerArchivosExcel();
        registro.correo(unSismo1);
        return true;
    }
    
    /**
     * Agrega el sismo al excel del registro.
     * @param unSismo1 : Un sismo ya creado.
     * @throws InvalidFormatException 
     */
    public void agregarSismoExcel(Sismo unSismo1) throws InvalidFormatException{  
        Sismo unSismo = darTMagnitud(darEscala(unSismo1));
 
        String escala = darEscalaString(unSismo.getEscala());
        String localizacion = darLocalizacionString(unSismo.getLocalizacion());
        String tMag = darTMagnitudString(unSismo.getTipoMagnitud());
        String tOrig = darTOrigenString(unSismo.getOrigen());
        String epi = darProvinciaString(unSismo.getEpicentro());
        
        File file = new File(rutaGlobal.ruta);         
         //String fecha1 = new SimpleDateFormat("dd/MM/yyyy").format(unSismo.getFecha());
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         String fecha = sdf.format(unSismo.getFecha()); 
         
        
        try {
            FileInputStream inputStream = new FileInputStream(new File(rutaGlobal.ruta));
            Workbook workbook = WorkbookFactory.create(inputStream);
 
            Sheet sheet = workbook.getSheetAt(0);
            
            //Formato que tendrá la celda
            Object[][] bookData = {
                    {unSismo.getId(),fecha,unSismo.getInstante(),unSismo.getMagnitud(),escala,tMag,
                        unSismo.getProfundidad(),epi,unSismo.getDescripcion(),localizacion,
                        tOrig,unSismo.getLatitud(),unSismo.getLongitud()
                    }
            };
            int rowCount = sheet.getLastRowNum();
            for (Object[] aBook : bookData) {
                Row row = sheet.createRow(++rowCount);
 
                int columnCount = 0;
                int control = 0;
                 
                Cell cell = row.createCell(columnCount);
                 
                for (Object field : aBook) {
                    if (control == 0){
                    cell = row.createCell(columnCount);
                    control = control + 1;
                    }
                    else{
                    cell = row.createCell(++columnCount);
                    
                    }
                    if (field instanceof Date) {
                        cell.setCellValue((Date) field);
                    } else if (field instanceof Double) {
                        cell.setCellValue((Double) field);
                    }else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    } else if (field instanceof String){
                        cell.setCellValue((String)field);
                    } 
                }
            }          
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
             
        } catch (IOException | EncryptedDocumentException
                | InvalidFormatException ex) {
        }
    }
    
    /**
     * Devuelve un String del enum Localizacion ingresado.
     * @param loc : Un enum de Localizacion. 
     * @return un String, dependiendo del loc ingresado.
     */
    public String darLocalizacionString (Localizacion loc){
        String resultado = "";
        
        if (loc == Localizacion.MARITIMA){
            resultado = Localizacion.MARITIMA.name();
        }else if(loc == Localizacion.TERRESTRE){
            resultado= Localizacion.TERRESTRE.name();
        }else{
            System.out.print("No se asignó ninguna localización");
        }
        return resultado;
    }
    
    /**
     * Devuelve un String del enum TEscala ingresado.  
     * @param escala : un enum TEscala.
     * @return un String del enum
     */
    public String darEscalaString (TEscala escala){
        String resultado = "";
        
        if(escala == TEscala.ML){
            resultado = TEscala.ML.name();
        }else if(escala == TEscala.MW){
            resultado = TEscala.MW.name();
        }else{
            System.out.print("No se asignó ninguna escala");
        }
        return resultado;
    }
    
    /**
     * Devuelve un String del enum TMagnitud ingresado.  
     * @param tMag : un enum TMagnitud
     * @return un String del enum
     */
    public String darTMagnitudString (TMagnitud tMag){
        String resultado = "";
        
        if(tMag == TMagnitud.EPICO){
            resultado = TMagnitud.EPICO.name();
        }else if(tMag == TMagnitud.FUERTE){
            resultado = TMagnitud.FUERTE.name();
        }else if(tMag == TMagnitud.GRAN){
            resultado = TMagnitud.GRAN.name();
        }else if(tMag == TMagnitud.LIGERO){
            resultado = TMagnitud.LIGERO.name();
        }else if(tMag == TMagnitud.MAYOR){
            resultado = TMagnitud.MAYOR.name();
        }else if(tMag == TMagnitud.MENOR){
            resultado = TMagnitud.MENOR.name();
        }else if(tMag == TMagnitud.MICRO){
            resultado = TMagnitud.MICRO.name();
        }else if(tMag == TMagnitud.MODERADO){
            resultado = TMagnitud.MODERADO.name();
        }else{
            System.out.print("No se asignó ningún tipo de magnitud");
        }
        return resultado;
    }
    
    /**
     * Devuelve un String del enum TOrigenSismo ingresado. 
     * @param tOrigen : un enum TOrigenSismo.
     * @return un String del enum
     */
    public String darTOrigenString(TOrigenSismo tOrigen){
        String resultado = "";
        
        if(tOrigen == TOrigenSismo.CHOQUE_DE_PLACAS){
            resultado = TOrigenSismo.CHOQUE_DE_PLACAS.name();
        }else if(tOrigen == TOrigenSismo.DEFORMACION_INTERNA){
            resultado = TOrigenSismo.DEFORMACION_INTERNA.name();
        }else if(tOrigen == TOrigenSismo.INTRAPLACA){
            resultado = TOrigenSismo.INTRAPLACA.name();
        }else if(tOrigen == TOrigenSismo.SUBDUCCION){
            resultado = TOrigenSismo.SUBDUCCION.name();
        }else if(tOrigen == TOrigenSismo.TECTONICO_POR_FALLA_LOCAL){
            resultado = TOrigenSismo.TECTONICO_POR_FALLA_LOCAL.name();
        }else{
            System.out.print("No se asignó ningún tipo de origen");
        }
        return resultado;
    }
    
    /**
     * Devuelve un String del enum TProvincia ingresado. 
     * @param epicentro : un enum del epicentro de tipo TProvincia.
     * @return un String del enum
     */
    public String darProvinciaString(TProvincia epicentro){
        String resultado = "";
        
        if (epicentro == TProvincia.CARTAGO){
            resultado = TProvincia.CARTAGO.name();
        }else if(epicentro == TProvincia.ALAJUELA){
            resultado = TProvincia.ALAJUELA.name();
        }else if(epicentro == TProvincia.GUANACASTE){
            resultado = TProvincia.GUANACASTE.name();
        }else if(epicentro == TProvincia.HEREDIA){
            resultado = TProvincia.HEREDIA.name();
        }else if(epicentro == TProvincia.LIMON){
            resultado = TProvincia.LIMON.name();
        }else if(epicentro == TProvincia.PUNTARENAS){
            resultado = TProvincia.PUNTARENAS.name();
        }else if(epicentro == TProvincia.SAN_JOSE){
            resultado = TProvincia.SAN_JOSE.name();
        }else if(epicentro == TProvincia.SIN_ASIGNAR){
            resultado = TProvincia.SIN_ASIGNAR.name();
        }else{
            resultado = "NO SE ASIGNÓ NADA";
        }
        return resultado;
    }
   
    //FUNCIONES QUE CALCULAN LA ESCALA Y EL TIPO DE MAGNITUD DE UN SISMO.
    /**
     * Calcula el tipo de escala a registrar dependiendo de la magnitud del sismo
     * La registra en el sismo en formato de enum TEscala
     * @param unSismo : un sismo ya creado, pero sin el dato de escala.
     * @return un Sismo con el dato añadido
     */
    public Sismo darEscala (Sismo unSismo){
        Sismo actual1 = unSismo;
        double actual = actual1.getMagnitud(); 
        if (actual >= 6.9){
            actual1.setEscala(TEscala.MW);
        }else{
            actual1.setEscala(TEscala.ML);
        }
        return actual1;
    }
    
    /**
     * Calcula el tipo de magnitud a registrar dependiendo de la magnitud del sismo
     * La registra en el sismo en formato de enum TMagnitud
     * @param unSismo : un sismo ya creado, pero sin el dato de "tipo de escala".
     * @return un Sismo con el dato añadido
     */
    public Sismo darTMagnitud (Sismo unSismo){
        
        double actual = unSismo.getMagnitud(); 
        
        if (actual < 2){
            unSismo.setTipoMagnitud(TMagnitud.MICRO);
        }else if (actual <= 3.9){
            unSismo.setTipoMagnitud(TMagnitud.MENOR);
        }else if ( actual <= 4.9){
            unSismo.setTipoMagnitud(TMagnitud.LIGERO);
        }else if(actual <= 5.9){
            unSismo.setTipoMagnitud(TMagnitud.MODERADO);
        }else if (actual <= 6.9){
            unSismo.setTipoMagnitud(TMagnitud.FUERTE);
        }else if (actual <= 7.9){
            unSismo.setTipoMagnitud(TMagnitud.MAYOR);
        }else if (actual <= 9.9){
            unSismo.setTipoMagnitud(TMagnitud.GRAN);
        }else{
            unSismo.setTipoMagnitud(TMagnitud.EPICO);
        }
        return unSismo;
    }
    
    /**
     * Modifica la localizacion de un Sismo ya registrado. Lo modifica en el ArrayList
     * Llama a la función que también lo modifica en el excel.
     * @param id : número entero que representa la identificacion del sismo
     * @param latitud : número positivo que representa la latitud del sismo.
     * @param longitud : número negativo que representa la longitud del sismo.
     * @return : true si lo pudo registrar, false en caso contario.
     * @throws Exception 
     */
    public boolean modificarSismoLocalizacion (int id,double latitud, double longitud ) throws Exception{
        int control = 0;
        
        for (Sismo actual : sismos) {
            if (actual.getId() == id){
                actual.setLatitud(latitud);
                actual.setLongitud(longitud);
                modificarExcelLocalizacion(control,latitud,longitud);
                return true;
            }control ++;            
        }
        return false;
    }
    
    /**
     * Modifica el epicentro de un Sismo ya registrado. Lo modifica en el ArrayList
     * Llama a la función que también lo modifica en el excel.
     * @param id : número entero que representa la identificación del sismo
     * @param epi : un enum de tipo TProvincia
     * @return true si lo pudo registrar, false en caso contrario
     * @throws Exception 
     */
    public boolean modificarSismoEpicentro (int id, TProvincia epi ) throws Exception{
        int control = 0; 
        System.out.print("Entra "+sismos.size());
        for (Sismo actual : sismos) {
            if (actual.getId() == id){
                actual.setEpicentro(epi);
                modificarExcelEpicentro(control,epi);
                return true;
            }control ++;            
        }
        return false;
    }
    

    //MODIFICAR SISMO EXCEL  
    /**
     * Modifica la localizacion de un sismo en el excel.
     * @param fila : número entero que representa la fila del sismo que se desea modificar en el excel
     * @param latitud : número positivo que representa la latitud del sismo
     * @param longitud : número negativo que representa la longitud de un sismo
     * @throws Exception 
     */
    public void modificarExcelLocalizacion (int fila ,double latitud, double longitud) throws Exception {

        int columnaLatitud = 11;
        int columnaLongitud = 12;
        
        FileInputStream excelFileInputStream = new FileInputStream(rutaGlobal.ruta);
        XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
        // Cierra la secuencia de entrada a tiempo después de usarla
        excelFileInputStream.close();    
        
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Realizamos los cambios
        XSSFCell newLatitud = sheet.getRow(fila).getCell(columnaLatitud);
        newLatitud.setCellValue(latitud);
        
        XSSFCell newLongitud = sheet.getRow(fila).getCell(columnaLongitud);
        newLongitud.setCellValue(longitud);
        
        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

        //Guardamos los cambios
        FileOutputStream excelFileOutPutStream = new FileOutputStream(rutaGlobal.ruta);
        workbook.write(excelFileOutPutStream);    
        excelFileOutPutStream.flush();
        excelFileOutPutStream.close();
    }
    
    /**
     * Modifica el epicentro de un sismo en el excel.
     * @param fila :  numero entero que represneta la fila del sismo que se desea modificar
     * @param epicentro : un dato de tipo enum TProvincia, nuevo dato que remplaza al ya existente
     * @throws Exception 
     */
    public void modificarExcelEpicentro (int fila,TProvincia epicentro) throws Exception {
 
        String darEpicentro = darProvinciaString(epicentro);
     
        FileInputStream excelFileInputStream = new FileInputStream(rutaGlobal.ruta);
        XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
        // Cierra la secuencia de entrada a tiempo después de usarla
        excelFileInputStream.close();    
        
        XSSFSheet sheet = workbook.getSheetAt(0);

        //Realizamos los cambios
        XSSFCell newEpicentro = sheet.getRow(fila).getCell(7);
        newEpicentro.setCellValue(darEpicentro);
     
        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

        //Guardamos los cambios
        FileOutputStream excelFileOutPutStream = new FileOutputStream(rutaGlobal.ruta);
        workbook.write(excelFileOutPutStream);    
        excelFileOutPutStream.flush();
        excelFileOutPutStream.close();
    }
    
    
    /**
     * Convierte un dato ingresado a una formato específico de fecha día, mes y año
     * @param strFecha : un sString que representa la fecha 
     * @return undato del tipo Date
     */
    public Date formatoFecha(String strFecha){

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
        fecha = formato.parse(strFecha);
        } catch (ParseException ex) {
        }
        return fecha;
    }


}
    


    
   