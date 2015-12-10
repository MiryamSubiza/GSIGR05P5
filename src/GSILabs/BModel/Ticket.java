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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Representa el derecho mono o multipersonal para entrar a un evento, validando
 * el acceso de una o más personas
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class Ticket implements XMLRepresentable, Serializable {
    
    private Event event;
    //Clave: Identificador para una persona
    //Valor: False si no ha entrado y True si ha entrado al evento
    private HashMap <Integer,Boolean> people;
    private int numberOfPeople;
    //True si el ticket ha sido vendido a un cliente, false en caso contrario
    private boolean sold;
    
    /**
     * Método constructor, inicialización de variables
     * @param event Evento al que se accede, puede ser Concierto, Festival o Exposición
     * @param atomicInteger Identificador único
     * @param numberOfPeople Cantidad de personas que pueden acceder al evento con esta entrada (una o varias)
     */
    public Ticket (Event event, int atomicInteger, int numberOfPeople) {
        
        this.event = event;
        people = new HashMap();
        for (int i = 1; i <= numberOfPeople; i++) {
            people.put(atomicInteger+(i-1), false);
        }
        this.numberOfPeople = numberOfPeople;
        this.sold = false;
        
    }
    
    /**
     * Método constructor, inicialización de variables
     * @param event Evento al que se accede, puede ser Concierto, Festival o Exposición
     */
    public Ticket (Event event){
        
        this.event = event;
        people = new HashMap();
        this.numberOfPeople = 0;
        this.sold = false;
    }
    
    /**
     * Establecer evento
     * @param event Evento al que se accede
     */
    public void setEvent (Event event) {
        this.event = event;
    }
    
    /**
     * Obtener evento
     * @return Evento al que se accede
     */
    public Event getEvent () {
        return event;
    }
    
    /**
     * Establecer cantidad de personas 
     * @param numberOfPeople Cantidad de personas que pueden entrar con esta entrada
     */
    public void setNumberOfPeople (int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    
    /**
     * Obtener cantidad de personas
     * @return Cantidad de personas que pueden entrar con esta entrada
     */
    public int getNumberOfPeople () {
        return numberOfPeople;
    }
    
    /**
     * Modificar entrada a vendida 
     * @param sold True si se ha vendido. False en caso contrario
     */
    public void setSold (boolean sold) {
        this.sold = sold;
    }
    
    /**
     * Comprobación de entrada vendida o no vendida
     * @return True si se ha vendido. False en caso contrario
     */
    public boolean isSold () {
        return sold;
    }
    
    /**
     * Comprobación de identificador en entrada
     * @param identifier Identificador a buscar
     * @return True si la entrada contiene este identificador.
     *  False en caso contrario
     */
    public boolean checkIdentifierInTicket (int identifier) {
        return people.containsKey(identifier);
    }
    
    /**
     * Comprobación de identificador utilizado
     * @param identifier Identificador a comprobar
     * @return True si el identificador ha sido utilizado.
     *  False en caso contrario
     */
    public boolean checkIdentifierIsUsed (int identifier) {
        return (boolean)people.get(identifier); //Le pasas una Key y te devuelve el Value
    }
    
    /**
     * Modificar un identificador no utilizado a utilizado
     * @param id Identificador a modificar
     * @return True si el identificador no había sido utilizado previamente y se ha
     * cambiado a utilizado. False en caso contrario
     */
    public boolean setIDUsed (int id) {
        if (!people.get(id)) { //No había sido usado
            people.put(id, true);
            return true;
        }
        else return false;
    }
    
    /**
     * Obtener los identificadores que componen esta entrada
     * @return Array de enteros con los identificadores que pertenecen a esta entrada
     */
    public int[] getIdentifiers () {
        Set ids = people.keySet();
        int[] idsArray;
        idsArray = new int[people.size()];
        Iterator i = ids.iterator();
        int idAux;
        int j = 0;
        while (i.hasNext()) {
            idAux = (int)i.next();
            idsArray[j] = idAux;
            j++;
        }
        return idsArray;
        
    }
    
    /**
     * Añade una entrada a este Ticket que contiene una o varias entradas
     * @param idTicket Identificador de la entrada a introducir
     * @param usedOrNot True si esta usado o falso si no esta usado
     * @return True si la entrada ha sido añadido dentro del objeto Ticket
     */
    public Boolean addTicketToTicket(int idTicket, Boolean usedOrNot){
        
        // Añado a mi HashMap de identificadores de este ticket la nueva entrada
        this.numberOfPeople++;        
        Boolean respuesta = people.put(idTicket, usedOrNot);
        return respuesta;
        
    }
    
    /**
     * Comparación entre dos objetos Ticket
     * @param o Objeto a comparar
     * @return True si contienen un mismo identificador. False en caso contrario
     */
    @Override
    public boolean equals (Object o) {
        
        if (o instanceof Ticket) {
            Ticket t = (Ticket)o;
            int[] ids = this.getIdentifiers();
            return t.checkIdentifierInTicket(ids[0]);
        }
        else return false;
        
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar
     */
    @Override
    public String toString() {
        String availability;
        if (sold) availability = "Sold";
        else availability = "Not sold";
        
        return "TICKET\nName of the event: " + event.getName() + 
                "\nNumber of people: " + numberOfPeople + "\nAvailability: "
                + availability + "\nIdentifiers: " + people.toString() + "\n";
    }

    @Override
    public String toXML() {
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("ticket", Ticket.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("location", Location.class);
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