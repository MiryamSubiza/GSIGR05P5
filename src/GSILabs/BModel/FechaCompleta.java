/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BModel;

import GSILabs.Serializable.XMLRepresentable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Extensión de la clase Date para poder obtener los días de las fechas
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class FechaCompleta extends java.util.Date implements XMLRepresentable, Serializable {
    
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int minuto;
    
    /**
     * Primer método constructor, inicialización de variables
     * @param dia Día de la fecha
     * @param mes Mes de la fecha
     * @param anio Año de la fecha
     * @param hora Hora de la hora
     * @param minuto Minuto de la hora
     */
    public FechaCompleta (int dia, int mes, int anio, int hora, int minuto) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
    }
    
    /**
     * Segundo método constructor, inicialización de variables
     * @param fecha Fecha (dd/mm/yyyy)
     * @param hora Hora (hh:mm)
     */
    public FechaCompleta (String fecha, String hora) {
        //Asumimos que el String fecha recibido tiene el formato "dd/mm/aaaa"
        String dd = fecha.substring(0,2);
        this.dia = Integer.parseInt(dd);
        String mm = fecha.substring(3,5);
        this.mes = Integer.parseInt(mm);
        String aaaa = fecha.substring(6,10);
        this.anio = Integer.parseInt(aaaa);
        //Asumimos que el String hora recibido tiene el formato "hh:mm"
        String hh = hora.substring(0,2);
        this.hora = Integer.parseInt(hh);
        String min = hora.substring(3,5);
        this.minuto = Integer.parseInt(min);
    }
    
    //MÉTODOS PARA ESTABLECER Y OBTENER ATRIBUTOS
    
    /**
     * Establecer día
     * @param dia Día de la fecha
     */
    public void setDia (int dia) {
        this.dia = dia;
    }
    
    /**
     * Obtener día
     * @return Día de la fecha
     */
    public int getDia () {
        return dia;
    }
    
    /**
     * Establecer mes
     * @param mes Mes de la fecha
     */
    public void setMes (int mes) {
        this.mes = mes;
    }
    
    /**
     * Obtener mes
     * @return Mes de la fecha
     */
    public int getMes () {
        return mes;
    }
    
    /**
     * Establecer año
     * @param anio Año de la fecha
     */
    public void setAnio (int anio) {
        this.anio = anio;
    }
    
    /**
     * Obtener año
     * @return Año de la fecha
     */
    public int getAnio () {
        return anio;
    }
    
    /**
     * Establecer hora
     * @param hora Hora de la hora
     */
    public void setHora (int hora) {
        this.hora = hora;
    }
    
    /**
     * Obtener hora
     * @return Hora de la hora
     */
    public int getHora () {
        return hora;
    }
    
    /**
     * Establecer minuto
     * @param minuto Minuto de la hora
     */
    public void setMinuto (int minuto) {
        this.minuto = minuto;
    }
    
    /**
     * Obtener minuto
     * @return Minuto de la hora
     */
    public int getMinuto () {
        return minuto;
    }
    
    //MÉTODOS PARA MOSTRAR FECHA Y HORA COMO STRING
    
    /**
     * Representación por pantalla
     * @return Información a mostrar sobre la fecha
     */
    public String fechaToString () {
        if ((dia < 10) && (mes < 10)) return "0" + dia + "/0" + mes + "/" + anio;
        else if (dia < 10) return "0" + dia + "/" + mes + "/" + anio;
        else if (mes < 10) return dia + "/0" + mes + "/" + anio;
        else return dia + "/" + mes + "/" + anio;
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar sobre la hora
     */
    public String horaToString () {
        if ((hora < 10) && (minuto < 10)) return "0" + hora + ":0" + minuto;
        else if (hora < 10) return "0" + hora + ":" + minuto;
        else if (minuto < 10) return hora + ":0" + minuto;
        else return hora + ":" + minuto;
    }
    
    /**
     * Comparación entre dos objetos FechaCompleta
     * @param o Objeto a comparar
     * @return True si coinciden en día, mes y año. False en caso contrario
     */
    @Override
    public boolean equals (Object o) {
        if (o instanceof FechaCompleta) {
            FechaCompleta fh = (FechaCompleta)o;
            return (this.getAnio() == fh.getAnio()) && (this.getMes() == fh.getMes())
                    && (this.getDia() == fh.getDia());
        }
        else return false;
    }

    @Override
    public String toXML() {
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("fechacompleta", FechaCompleta.class);
        return xStream.toXML(this);
    }

    @Override
    public boolean saveToXML(File f) {
        boolean respuesta = false;
        XStream xStream = new XStream(new DomDriver());
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(f);
            pw = new PrintWriter(fichero);
            pw.println(toXML());            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                    respuesta = true;
                }
                else {
                    respuesta = false;
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return respuesta;
    }

    @Override
    public boolean saveToXML(String filePath) {
        //Creación del fichero en la ruta especificada
        File f = new File(filePath);
        return this.saveToXML(f);
    }    
}
