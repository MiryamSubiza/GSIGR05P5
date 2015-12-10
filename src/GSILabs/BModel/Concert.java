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
import java.util.Date;

/**
 * Evento protagonizado por un único performer y de un día de duración
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class Concert implements ImpermanentEvent, XMLRepresentable, Serializable {
    
    private String concertName;
    private Performer p;
    private FechaCompleta startDateConcert;
    private FechaCompleta startTimeConcert;
    private FechaCompleta doorOpeningTimeConcert;
    private FechaCompleta closingTimeConcert;
    private Location location;
    
    /**
     * Método constructor, inicialización de variables
     * @param concertName Nombre asociado con el evento
     * @param p Performer, puede ser Artista o Colectivo, pero solamente uno
     * @param startDateConcert Fecha del concierto (un solo día)
     * @param startTimeConcert Hora de comienzo
     * @param doorOpeningTimeConcert Hora de apertura de puertas
     * @param closingTimeConcert Hora de cierre
     * @param location Localización única
     */
    public Concert (String concertName, Performer p, FechaCompleta startDateConcert, 
            FechaCompleta startTimeConcert, FechaCompleta doorOpeningTimeConcert, 
            FechaCompleta closingTimeConcert, Location location) {
        
        this.concertName = concertName;
        this.p = p;
        this.startDateConcert = startDateConcert;
        this.startTimeConcert = startTimeConcert;
        this.doorOpeningTimeConcert = doorOpeningTimeConcert;
        this.closingTimeConcert = closingTimeConcert;
        this.location = location;
        
    }
    
    /**
     * Establecer nombre del concierto
     * @param concertName Nombre del concierto
     */
    public void setConcertName (String concertName) {
        this.concertName = concertName;
    }
    
    /**
     * Establecer performer, ya sea artista o colectivo
     * @param p Performer
     */
    public void setPerformer (Performer p) {
        this.p = p;
    }
    
    /**
     * Obtener performer
     * @return Performer, o artista o colectivo
     */
    public Performer getPerformer () {
        return p;
    }
    
    /**
     * Establecer día del concierto
     * @param startDateConcert Día del concierto
     */
    public void setStartDateConcert (FechaCompleta startDateConcert) {
        this.startDateConcert = startDateConcert;
    }
    
    /**
     * Obtener día del concierto
     * @return Día del concierto
     */
    @Override
    public Date getStartDate () {
        return startDateConcert;
    }
    
    /**
     * Establecer hora de comienzo
     * @param startTimeConcert Hora de comienzo
     */
    public void setStartTimeConcert (FechaCompleta startTimeConcert) {
        this.startTimeConcert = startTimeConcert;
    }
    
    /**
     * Obtener hora de comienzo
     * @return Hora de comienzo
     */
    public Date getStartTimeConcert () {
        return startTimeConcert;
    }
    
    /**
     * Establecer hora de apertura de puertas
     * @param doorOpeningTimeConcert Hora de apertura de puertas
     */
    public void setDoorOpeningTimeConcert (FechaCompleta doorOpeningTimeConcert) {
        this.doorOpeningTimeConcert = doorOpeningTimeConcert;
    }
    
    /**
     * Obtener hora de apertura de puertas
     * @return Apertura de puertas
     */
    public Date getDoorOpeningTimeConcert () {
        return doorOpeningTimeConcert;
    }
    
    /**
     * Establecer hora de cierre
     * @param closingTimeConcert Hora de cierre
     */
    public void setClosingTimeConcert (FechaCompleta closingTimeConcert) {
        this.closingTimeConcert = closingTimeConcert;
    }
    
    /**
     * Obtener hora de cierre
     * @return Hora de cierre
     */
    public Date getClosingTimeConcert () {
        return closingTimeConcert;
    }
    
    /**
     * Establecer localización
     * @param location Localización del concierto
     */
    public void setLocation (Location location) {
        this.location = location;
    }
    
    /**
     * Obtener localización
     * @return Localización
     */
    @Override
    public Location getLocation () {
        return location;
    }

    /**
     * Obtener nombre del concierto
     * @return Nombre del concierto
     */
    @Override
    public String getName() {
        return concertName;
    }
    
    /**
     * Obtener fecha en la que ocurre el concierto
     * @return Fecha del concierto
     */
    @Override
    public Date[] getDates() {
        
        Date[] dates;
        dates = new Date[1];
        dates[0] = startDateConcert;
        
        return dates;
        
    }

    /**
     * Comprobar si en este concierto actúa un performer dado
     * @param pAskedFor Performer por el que se pregunta
     * @return True si el performer actúa en el concierto. False en caso contrario
     */
    //p es el performer que tenemos almacenado en la clase Concert
    //pAskedFor es el performer por el que preguntan si actúa en el concierto
    @Override
    public boolean involvesPerformer (Performer pAskedFor) {

        //Utilización del operador instanceof para determinar si el tipo de cierto objeto Performer es Artist
        if (p instanceof Artist) {
            //Conversión descendente de la referencia de Performer a una referencia de Artist
            Artist aInvolved = (Artist) p;
            
            //Preguntan por un artista y tenemos un artista, comparación por nombre
            if (pAskedFor instanceof Artist) {
                Artist aAskedFor = (Artist) pAskedFor;
                return aInvolved.equals(aAskedFor);
            }
            //Preguntan por un colectivo y tenemos un artista, directamente no está involucrado
            else if (pAskedFor instanceof Collective) return false;
            else return false;
        }
        else if (p instanceof Collective) {
            Collective cInvolved = (Collective) p;
            
            //Preguntan por un artista y tenemos un colectivo, mirar si el artista está en el colectivo
            if (pAskedFor instanceof Artist) {
                Artist aAskedFor = (Artist) pAskedFor;
                return cInvolved.isArtistInCollective(aAskedFor);
                
            }
            //Preguntan por un colectivo y tenemos un colectivo, comparación por nombre
            else if (pAskedFor instanceof Collective) {
                Collective cAskedFor = (Collective) pAskedFor;
                return cInvolved.equals(cAskedFor);
            }
            else return false;
        }
        else return false;
        
    }
    
    /**
     * Obtener el performer que actúa en este evento
     * @return Performer que actúa en este evento
     */
    @Override
    public Performer[] getPerformers() {
        
        Performer[] performers;
        performers = new Performer[1];
        performers[0] = p;
        
        return performers;
        
    }
    
    /**
     * Comparación entre dos objetos Concert
     * @param o Objeto a comparar
     * @return True si contienen el mismo performer y actúan en la misma fecha.
     *  False en caso contrario
     */
    @Override
    public boolean equals (Object o) {
        
        if (o instanceof Concert) {
            Concert c = (Concert)o;
            if (!c.getName().equalsIgnoreCase(this.getName())) { // Compruebo si los nombres de los conciertos son los mismos
                if (involvesPerformer(c.getPerformer())) { // Compruebo si el artista participa en ambos conciertos
                    return c.getStartDate().equals(this.startDateConcert); // Compruebo si ambos conciertos son en la misma fecha
                }
                // Los artistas son distintos
                else return false;
            }
            // Los conciertos tienen el mismo nombre
            else return true;
        }
        else return false;
        
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar
     */
    @Override
    public String toString() {
        return "CONCERT\nConcert's name: " + concertName + "\nPerformer's name: " +
                p.getName() + "\nDate: " + startDateConcert.fechaToString() +
                "\nDoor opening: " + doorOpeningTimeConcert.horaToString() + 
                "h\nStart time: " + startTimeConcert.horaToString() + 
                "h\nClosing time: " + closingTimeConcert.horaToString() + 
                "h\n->" + location.toString() + "\n";
    }

    @Override
    public String toXML() {
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("concert", Concert.class);
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
                if (null != fichero){
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