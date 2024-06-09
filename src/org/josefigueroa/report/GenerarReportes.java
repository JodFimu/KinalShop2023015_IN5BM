package org.josefigueroa.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.josefigueroa.db.Conexion;

public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String tituloReporte, Map parametros){
        
        try{
            InputStream reporte=GenerarReportes.class.getResourceAsStream(nombreReporte);
            JasperReport Reporte = (JasperReport) JRLoader.loadObject(reporte);
            
            
            JasperPrint reporteImpreso = JasperFillManager.fillReport(Reporte, parametros, Conexion.getInstance().getConexion());
 
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(tituloReporte);
            visor.setVisible(true);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    /*
        interfaz Map es uno de los objetos que implementa un conjunto de key-value.
        tiene un constructor sin parametros, new HashMap() y su finalidad suele referirse para agrupar informacion en un unico objeto
    
        tiene cierta similitud con el arreglo de array list pero con la diferencia que estos no tienen orden
        Hash hace referencia a una tecnica de organizacion de archivos, hashing en la cual se almacenan registros de una direccion que es generada por una funcion,
        se aplica a la llave del registro.
    
        En java el hashmap posee un espacio de memoria y cuando se guarda un objeto en dicho espacio se determinaa su direccion aplicando una funcion a la llave que le indiquemos nosotros
        cada objeto se identifica mediante algun identificador apropiado
    */
}
