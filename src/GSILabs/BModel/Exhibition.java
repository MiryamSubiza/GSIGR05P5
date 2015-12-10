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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Evento protagonizado por un artista o varios de ellos, puede extenderse durante
 * varios días o semanas
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class Exhibition implements LastingEvent, XMLRepresentable, Serializable {
    
    private String exhibitionName;
    private String title;
    private String organizerName;
    private FechaCompleta startDateExhibition;
    private FechaCompleta closingDateExhibition;
    private FechaCompleta startTimeExhibition;
    private FechaCompleta closingTimeExhibition;
    private Performer p;
    private HashSet <String> webLinks;
    private Location location;
    
    /**
     * Método constructor, inicialización de variables
     * @param exhibitionName Nombre asociado con el evento
     * @param title Título de la exposición
     * @param organizerName Nombre de la entidad organizadora
     * @param startDateExhibition Fecha de apertura
     * @param closingDateExhibition Fecha de cierre
     * @param startTimeExhibition Hora de apertura
     * @param closingTimeExhibition Hora de cierre
     * @param p Performer, puede ser un artista o varios, en cuyo caso se considerará colectivo
     * @param webLink Enlaces web (número indeterminado)
     * @param location Localización única
     */
    public Exhibition (String exhibitionName, String title, String organizerName, 
            FechaCompleta startDateExhibition, FechaCompleta closingDateExhibition, FechaCompleta startTimeExhibition,
            FechaCompleta closingTimeExhibition, Performer p, String webLink, Location location) {
        
        this.exhibitionName = exhibitionName;
        this.title = title;
        this.organizerName = organizerName;
        this.startDateExhibition = startDateExhibition;
        this.closingDateExhibition = closingDateExhibition;
        this.startTimeExhibition = startTimeExhibition;
        this.closingTimeExhibition = closingTimeExhibition;
        this.p = p;
        webLinks = new HashSet();
        webLinks.add(webLink);
        this.location = location;
        
    }
    
    /**
     * Establecer nombre de la exposición
     * @param exhibitionName Nombre de la exposición
     */
    public void setExhibitionName (String exhibitionName) {
        this.exhibitionName = exhibitionName;
    }
    
    /**
     * Obtener nombre de la exposición
     * @return Nombre de la exposición
     */
    @Override
    public String getName () {
        return exhibitionName;
    }
    
    /**
     * Establecer título de la exposición
     * @param title Título de la exposición
     */
    public void setTitle (String title) {
        this.title = title;
    }
    
    /**
     * Obtener título de la exposición
     * @return Título de la exposición
     */
    public String getTitle () {
        return title;
    }
    
    /**
     * Establecer entidad organizadora
     * @param organizerName Entidad organizadora
     */
    public void setOrganizerName (String organizerName) {
        this.organizerName = organizerName;
    }
    
    /**
     * Obtener entidad organizadora
     * @return Entidad organizadora
     */
    public String getOrganizerName () {
        return organizerName;
    }
    
    /**
     * Establecer fecha de apertura
     * @param startDateExhibition Fecha de apertura
     */
    public void setStartDateExhibition (FechaCompleta startDateExhibition) {
        this.startDateExhibition = startDateExhibition;
    }
    
    /**
     * Obtener fecha de apertura
     * @return Fecha de apertura
     */
    @Override
    public Date getStartDate () {
        return startDateExhibition;
    }
    
    /**
     * Establecer fecha de cierre
     * @param closingDateExhibition Fecha de cierre
     */
    public void setClosingDateExhibition (FechaCompleta closingDateExhibition) {
        this.closingDateExhibition = closingDateExhibition;
    }
    
    /**
     * Obtener fecha de cierre
     * @return Fecha de cierre
     */
    @Override
    public Date getEndingDate () {
        return closingDateExhibition;
    }
    
    /**
     * Establecer hora de apertura
     * @param startTimeExhibition Hora de apertura
     */
    public void setStartTimeExhibition (FechaCompleta startTimeExhibition) {
        this.startTimeExhibition = startTimeExhibition;
    }
    
    /**
     * Obtener hora de apertura
     * @return Hora de apertura
     */
    public Date getStartTimeExhibition () {
        return startTimeExhibition;
    }
    
    /**
     * Establecer hora de cierre
     * @param closingTimeExhibition Hora de cierre
     */
    public void setClosingTimeExhibition (FechaCompleta closingTimeExhibition) {
        this.closingTimeExhibition = closingTimeExhibition;
    }
    
    /**
     * Obtener hora de cierre
     * @return Hora de cierre
     */
    public Date getClosingTimeExhibition () {
        return closingTimeExhibition;
    }
    
    /**
     * Establecer performer
     * @param p Performer, un artista o varios, en cuyo caso se considerará colectivo
     */
    public void setPerformer (Performer p) {
        this.p = p;
    }
    
    /**
     * Obtener performer
     * @return Performer, un artista o varios, en cuyo caso se considerará colectivo
     */
    public Performer getPerformer () {
        return p;
    }
    
    /**
     * Añadir enlace web a la exposición
     * @param webLink Enlace web
     */
    public void addWebLink (String webLink) {
        webLinks.add(webLink);
    }
    
    /**
     * Obtener enlaces web
     * @return HashSet con todos los enlaces web asociados a esta exposición
     */
    public HashSet getWebLinks () {
        return webLinks;
    }
    
    /**
     * Establecer localización
     * @param location Location
     */
    public void setLocation (Location location) {
        this.location = location;
    }
    
    /**
     * Obtener localización
     * @return Localización única
     */
    public Location getLocation () {
        return location;
    }
    
    /**
     * Obtener días que abarca la exposición
     * @return Días que dura la exposición
     */
    @Override
    public Date[] getDates() {
        ArrayList <Date> al = new ArrayList();
        FechaCompleta auxDate = startDateExhibition;
        for(int i=0; i<(calculateExhibitionDays(startDateExhibition, closingDateExhibition)); i++) {
            al.add(auxDate);
            auxDate = incrementDay(auxDate);
        }
        return (Date[]) al.toArray();
    }
    
    /**
     * Obtener cuántos días hay de diferencia entre una fecha y otra
     * @return Número de días de diferencia entre una fecha y otra
     */
    private int calculateExhibitionDays (FechaCompleta dia1, FechaCompleta dia2) {
        
        int numDias;
        if ((dia2.getMes() - dia1.getMes()) >= 1) {
            numDias = 30*(dia2.getMes() - dia1.getMes() - 1) + dia2.getDia() + 
                    (numDiasMes(dia1.getMes()) - dia1.getDia());
        }
        else numDias = dia2.getDia() - dia1.getDia();
        return numDias;
    }
    
    /**
     * Obtener cuántos días contiene un determinado mes.
     * Es un método static porque no modifica la instancia.
     * @param mes Mes a calcular
     * @return Número de días que contiene ese mes
     */
    private static int numDiasMes (int mes) {
        int nDias;
        switch(mes){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                nDias = 31;
                break;
            case 4: case 6: case 9: case 11:
                nDias = 30;
                break;
            case 2:
                nDias = 28;
                break;
            default:
                nDias = 30;
        }
        
        return nDias;
    }

    /**
     * Incrementar una fecha un día (cambiando si es necesario el mes o año de la fecha)
     * @param day Día a incrementar
     * @return Día incrementado
     */
    private FechaCompleta incrementDay (FechaCompleta day) {
        
        final int ANIO = day.getAnio();
        final int MES = day.getMes();
        final int DIA = day.getDia();
        final int HORA = day.getHora();
        final int MINUTOS = day.getMinuto();
        
        FechaCompleta nextDay = null;
        switch (MES) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                if(DIA == 31){
                    if(MES == 12) nextDay = new FechaCompleta(1, 1, ANIO+1, HORA, MINUTOS);
                    else nextDay = new FechaCompleta(1, MES + 1, ANIO, HORA, MINUTOS);
                }
                else nextDay = new FechaCompleta(day.getDay() + 1, MES, ANIO, HORA, MINUTOS);
                break;
                
            case 4: case 6: case 9: case 11:
                if(DIA == 30) nextDay = new FechaCompleta(1, MES + 1, ANIO, HORA, MINUTOS);
                else nextDay = new FechaCompleta(DIA + 1, MES, ANIO, HORA, MINUTOS);
                break;
                
            case 2:
                if(DIA == 28) nextDay = new FechaCompleta(1, MES + 1, ANIO, HORA, MINUTOS);
                else nextDay = new FechaCompleta(DIA + 1, MES, ANIO, HORA, MINUTOS);
                break;
        }        
        return nextDay;
    }
    
    /**
     * Comprobar si en esta exposición actúa un performer dado
     * @param p Performer por el que se pregunta
     * @return True si el performer forma parte de la exposición. False en caso contrario
     */
    @Override
    public boolean involvesPerformer (Performer p) {

        if (p instanceof Artist) {
            if (this.p instanceof Artist) return (((Artist)p).equals(((Artist)this.p)));
            else if (this.p instanceof Collective) return ((Collective)this.p).isArtistInCollective((Artist)p);
            else return false;
        }
        else if (p instanceof Collective) {
            if (this.p instanceof Artist) return false;
            else if (this.p instanceof Collective) return (((Collective)p).equals(((Collective)this.p)));
            else return false;
        }    
        else return false;
        
    }
    
    
    
    /**
     * Obtener los performers que actúan en este evento
     * @return Performers que actúan en este evento
     */
    @Override
    public Performer[] getPerformers() {
        
        Performer[] performers = new Performer[1];
        performers[0] = p;
        return performers;
        
    }
    
    /**
     * Comparación entre dos objetos Exhibition
     * @param o Objeto a comparar
     * @return True si se llaman de la misma manera. False en caso contrario
     */
    @Override
    public boolean equals (Object o) {
        
        if (o instanceof Exhibition) {
            Exhibition e = (Exhibition)o;
            return this.getName().equalsIgnoreCase(e.getName());
        }
        else return false;
        
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar
     */
    @Override
    public String toString() {
        return "EXHIBITION\nExhibition's name: " + exhibitionName + "\nTitle: " +
                title + "\nOrganizer name: " + organizerName + "\nStart date: " + 
                startDateExhibition.fechaToString() + "\nStart time: " + 
                startTimeExhibition.horaToString() + "h\nClosing date: " + 
                closingDateExhibition.fechaToString() + "\nClosing time: " + 
                closingTimeExhibition.horaToString() + "h\nPerformer: " + p.getName() + 
                "\n->" + location.toString() + "\n";
    }

    @Override
    public String toXML() {
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("location", Location.class);        
        String xml = xStream.toXML(this);        
        return xml;
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