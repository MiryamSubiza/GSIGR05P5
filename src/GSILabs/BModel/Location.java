/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BModel;
import java.io.FileWriter;
import GSILabs.Serializable.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Información sobre localizaciones que son únicas
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class Location implements XMLRepresentable, Serializable{
    
    private String name;
    private int maxCapacity;
    private String geographicPosition;
    private String webSite = "-";
    
    /**
     * Primer método constructor, inicialización de variables para una localización
     * con página web
     * @param name Nombre de la localización
     * @param maxCapacity Aforo máximo
     * @param geographicPosition Posición geográfica de la localización
     * @param webSite Enlace web oficial de la localización, puede no tener
     */
    public Location (String name, int maxCapacity, String geographicPosition, String webSite) {
        
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.geographicPosition = geographicPosition;
        this.webSite = webSite;
        
    }
    
    /**
     * Primer método constructor, inicialización de variables para una localización
     * sin página web
     * @param name Nombre de la localización
     * @param maxCapacity Aforo máximo
     * @param geographicPosition Posición geográfica de la localización
     */
    public Location (String name, int maxCapacity, String geographicPosition) {
        
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.geographicPosition = geographicPosition;
        
    }
    
    /**
     * Establecer nombre de la localización
     * @param name Nombre de la localización
     */
    public void setName (String name) {
        this.name = name;
    }
    
    /**
     * Obtener nombre de la localización
     * @return Nombre de la localización
     */
    public String getName(){
        return name;
    }
    
    /**
     * Establecer el aforo máximo
     * @param maxCapacity Aforo máximo
     */
    public void setMaxCapacity (int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    /**
     * Obtener aforo máximo
     * @return Aforo máximo
     */
    public int getMaxCapacity () {
        return maxCapacity;
    }
    
    /**
     * Establecer posición geográfica
     * @param geographicPosition Posición geográfica
     */
    public void setGeographicPosition (String geographicPosition) {
        this.geographicPosition = geographicPosition;
    }
    
    /**
     * Obtener posición geográfica
     * @return Posición geográfica
     */
    public String getGeographicPosition () {
        return geographicPosition;
    }
    
    /**
     * Establecer enlace web
     * @param webSite Enlace web
     */
    public void setWebSite (String webSite) {
        this.webSite = webSite;
    }
    
    /**
     * Obtener enlace web
     * @return Enlace web
     */
    public String getWebSite(){
        return webSite;
    }
    
    /**
     * Comparación entre dos objetos Location
     * @param o Objeto a comparar
     * @return True si se llaman de la misma manera. False en caso contrario
     */
    @Override
    public boolean equals (Object o) {
        
        if (o instanceof Location) {
            Location l = (Location)o;
            return this.getName().equalsIgnoreCase(l.getName());
        }
        else return false;
        
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar
     */
    @Override
    public String toString() {
        return "LOCATION\nLocation's name: " + name + "\nMaximum capacity: " + 
                maxCapacity + "\nGeographic position: " + geographicPosition + 
                "\nWebsite: " + webSite + "\n";
    }

    @Override
    public String toXML() {
        
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("location", Location.class);
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