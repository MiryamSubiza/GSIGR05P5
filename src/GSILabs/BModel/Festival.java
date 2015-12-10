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
import java.util.Iterator;

/**
 * Evento compuesto por uno o varios conciertos, el festival puede extenderse 
 * durante varios días o semanas
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class Festival implements LastingEvent, XMLRepresentable, Serializable {

    private HashSet <Concert> concerts;
    private FechaCompleta startDateFestival;
    private FechaCompleta closingDateFestival;
    private FechaCompleta startTimeFestival;
    private FechaCompleta closingTimeFestival;
    private String festivalName;
    
    /**
     * Primer método constructor, inicialización de variables
     * @param festivalName Nombre del festival
     * @param c Primer concierto asociado a este festival, puede tener uno o varios
     * @param startDateFestival Fecha de apertura, que coincide con la fecha del primer concierto
     * @param closingDateFestival Fecha de cierre, que coincide con la fecha del último concierto
     * @param startTimeFestival Hora de apertura, que coincide con la hora de apertura de puertas
        del primer concierto
     * @param closingTimeFestival Hora de cierre, que coincide con la hora de cierre del último concierto
     */
    public Festival (String festivalName, Concert c, FechaCompleta startDateFestival, FechaCompleta closingDateFestival,
            FechaCompleta startTimeFestival, FechaCompleta closingTimeFestival) {
        
        this.festivalName = festivalName;
        concerts = new HashSet();
        concerts.add(c);
        this.startDateFestival = startDateFestival;
        this.closingDateFestival = closingDateFestival;
        this.startTimeFestival = startTimeFestival;
        this.closingTimeFestival = closingTimeFestival;
        
    }
    
    /**
     * Segundo método constructor, inicialización de variables
     * @param festivalName Nombre del festival
     * @param concerts Conjunto de conciertos asociados a este festival
     * @param startDateFestival Fecha de apertura, que coincide con la fecha del primer concierto
     * @param closingDateFestival Fecha de cierre, que coincide con la fecha del último concierto
     * @param startTimeFestival Hora de apertura, que coincide con la hora de apertura de puertas
        del primer concierto
     * @param closingTimeFestival Hora de cierre, que coincide con la hora de cierre del último concierto
     */
    public Festival (String festivalName, HashSet<Concert> concerts, FechaCompleta startDateFestival, FechaCompleta closingDateFestival,
        FechaCompleta startTimeFestival, FechaCompleta closingTimeFestival) {

        this.festivalName = festivalName;
        this.concerts = concerts;
        this.startDateFestival = startDateFestival;
        this.closingDateFestival = closingDateFestival;
        this.startTimeFestival = startTimeFestival;
        this.closingTimeFestival = closingTimeFestival;
        
    }
    
    /**
     * Tercer método constructor, inicialización de variables
     * @param festivalName Nombre del festival
     * @param startDateFestival Fecha de apertura, que coincide con la fecha del primer concierto
     * @param closingDateFestival Fecha de cierre, que coincide con la fecha del último concierto
     * @param startTimeFestival Hora de apertura, que coincide con la hora de apertura de puertas
        del primer concierto
     * @param closingTimeFestival Hora de cierre, que coincide con la hora de cierre del último concierto
     */
    public Festival (String festivalName, FechaCompleta startDateFestival, FechaCompleta closingDateFestival,
        FechaCompleta startTimeFestival, FechaCompleta closingTimeFestival) {

        this.festivalName = festivalName;
        this.concerts = new HashSet();
        this.startDateFestival = startDateFestival;
        this.closingDateFestival = closingDateFestival;
        this.startTimeFestival = startTimeFestival;
        this.closingTimeFestival = closingTimeFestival;
        
    }
    
    /**
     * Añadir concierto al festival
     * @param c Concierto a añadir
     */
    public boolean addConcert (Concert c) {
        return concerts.add(c);
    }
    
    /**
     * Eliminar concierto del festival
     * @param c Concierto a eliminar
     * @return True si el concierto ha sido eliminado. False en caso contrario
     */
    public boolean removeConcert (Concert c) {
        return concerts.remove(c);
    }
    
    /**
     * Obtener conciertos contenidos en el festival
     * @return Conjunto de conciertos contenidos en el festival
     */
    public HashSet<Concert> getConcerts(){
        return concerts;
    }
    
    /**
     * Establecer nombre del festival
     * @param festivalName Nombre del festival
     */
    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }
    
    /**
     * Obtener nombre del festival
     * @return Nombre del festival
     */
    @Override
    public String getName() {
        return this.festivalName;
    }
    
    /**
     * Establecer fecha de apertura
     * @param startDateFestival Fecha de apertura
     */
    public void setStartDateFestival (FechaCompleta startDateFestival) {
        this.startDateFestival = startDateFestival;
    }
    
    /**
     * Obtener fecha de apertura
     * @return Fecha de apertura
     */
    @Override
    public Date getStartDate () {
        return startDateFestival;
    }
    
    /**
     * Establecer fecha de cierre
     * @param closingDateFestival Fecha de cierre
     */
    public void setClosingDateFestival (FechaCompleta closingDateFestival) {
        this.closingDateFestival = closingDateFestival;
    }
    
    /**
     * Obtener fecha de cierre
     * @return Fecha de cierre
     */
    @Override
    public Date getEndingDate () {
        return closingDateFestival;
    }
    
    /**
     * Establecer hora de apertura
     * @param startTimeFestival Hora de apertura
     */
    public void setStartTimeFestival (FechaCompleta startTimeFestival) {
        this.startTimeFestival = startTimeFestival;
    }
    
    /**
     * Obtener hora de apertura
     * @return Hora de apertura
     */
    public Date getStartTimeFestival () {
        return startTimeFestival;
    }
    
    /**
     * Establecer hora de cierre
     * @param closingTimeFestival Hora de cierre
     */
    public void setClosingTimeFestival (FechaCompleta closingTimeFestival) {
        this.closingTimeFestival = closingTimeFestival;
    }
    
    /**
     * Obtener hora de cierre
     * @return Hora de cierre
     */
    public Date getClosingTimeFestival () {
        return closingTimeFestival;
    }
    
    /* Devuelve verdadero si el concierto se encuentra en el festival
    *  y falso en caso contrario
    */
    public boolean isConcertInFestival (Concert auxConcert) {
        return concerts.contains(auxConcert);
    }
    
    /**
     * Obtener días que abarca el festival
     * @return Días que dura el festival
     */
    @Override
    public Date[] getDates() {
        ArrayList <Date> al = new ArrayList();
        FechaCompleta auxDate = startDateFestival;
        for (int i=0; i<(calculateFestivalDays(startDateFestival, closingDateFestival)); i++) {
            al.add(auxDate);
            auxDate = incrementDay(auxDate);
        }
        return (Date[]) al.toArray();
    }
    
    /**
     * Obtener cuántos días hay de diferencia entre una fecha y otra
     * @return Número de días de diferencia entre una fecha y otra
     */
    private int calculateFestivalDays (FechaCompleta dia1, FechaCompleta dia2) {
        
        // Variable a devolver que contendrá el número de días de diferencia entre una fecha y otra
        int numDias;
        if ((dia2.getMes() - dia1.getMes()) >= 1) {
            numDias = 30*(dia2.getMes() - dia1.getMes() - 1) + dia2.getDia() + 
                    (numDiasMes(dia1.getMes()) - dia1.getDia());
        }
        else numDias = dia2.getDia() - dia1.getDia();        
        return numDias;
    }
    
    /**
     * Obtener cuántos días contiene un determinado mes
     * @param mes Mes a calcular
     * @return Número de días que contiene ese mes
     */
    private int numDiasMes (int mes) {
        int nDias;
        switch (mes) {
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
        FechaCompleta nextDay = null;
        switch (day.getMes()){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                if(day.getDia() == 31){
                    if(day.getMes() == 12) nextDay = new FechaCompleta(1, 1, day.getAnio()+1, day.getHora(), day.getMinuto());
                    else nextDay = new FechaCompleta(1, day.getMes() + 1, day.getAnio(), day.getHora(), day.getMinuto());
                }
                else nextDay = new FechaCompleta(day.getDay() + 1, day.getMes(), day.getAnio(), day.getHora(), day.getMinuto());
                break;
                
            case 4: case 6: case 9: case 11:
                if(day.getDia() == 30) nextDay = new FechaCompleta(1, day.getMes() + 1, day.getAnio(), day.getHora(), day.getMinuto());
                else nextDay = new FechaCompleta(day.getDia() + 1, day.getMes(), day.getAnio(), day.getHora(), day.getMinuto());
                break;
                
            case 2:
                if(day.getDia() == 28) nextDay = new FechaCompleta(1, day.getMes() + 1, day.getAnio(), day.getHora(), day.getMinuto());
                else nextDay = new FechaCompleta(day.getDia() + 1, day.getMes(), day.getAnio(), day.getHora(), day.getMinuto());
                break;
        }
        return nextDay;
    }
    
    /**
     * Comprobar si en este festival actúa un performer dado
     * @param p Performer por el que se pregunta
     * @return True si el performer forma parte del festival. False en caso contrario
     */
    @Override
    public boolean involvesPerformer (Performer p) {
        
        Iterator i = concerts.iterator();
        Concert concertAux = null;
        while (i.hasNext()) {
            concertAux = (Concert)i.next();
            if (concertAux.involvesPerformer(p)) {
                break;
            }
        }
        return concertAux.involvesPerformer(p);
        
    }
    
    /**
     * Obtener los performers que actúan en este evento
     * @return Performers que actúan en este evento
     */
    public Performer[] getPerformers() {
        
        ArrayList <Performer> al = new ArrayList();
        Iterator i = concerts.iterator();
        Concert concertAux = null;
        int j = 0;
        while(i.hasNext()){
            concertAux = (Concert)i.next();
            al.add(concertAux.getPerformer());
            j = j + 1;
        }
        Performer[] performers = new Performer[al.size()];
        al.toArray();
        return performers;
        
    }
    
    /**
     * Comparación entre dos objetos Festival
     * @param o Objeto a comparar
     * @return True si se llaman de la misma manera. False en caso contrario
     */
    @Override
    public boolean equals (Object o) {
        
        if (o instanceof Festival) {
            Festival f = (Festival)o;
            return this.getName().equalsIgnoreCase(f.getName());
        }
        else return false;
        
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar
     */
    @Override
    public String toString() {
        String aux = "";
        for (Concert concert : concerts) {
            aux = aux + "->" + concert.toString();
        }
        return "FESTIVAL\nFestival's name: " + festivalName + "\nStart date: " + 
                startDateFestival.fechaToString() + "\nStart time: " + 
                startTimeFestival.horaToString() + "h\nClosing date: " + 
                closingDateFestival.fechaToString() + "\nClosing time: " + 
                closingTimeFestival.horaToString() + "h\n" + aux + "\n";
    }
    
    @Override
    public String toXML() {
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("festival", Festival.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
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
