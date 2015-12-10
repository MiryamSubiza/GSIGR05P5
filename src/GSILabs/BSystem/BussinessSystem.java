/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BSystem;

import GSILabs.BModel.*;
import GSILabs.Serializable.XMLRepresentable;
import GSILabs.persistence.XMLParsingException;
import GSILabs.connect.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Implementación del interfaz TicketOffice, representando el comportamiento 
 * básico esperado en un sistema informático de acuerdo a las reglas impuestas 
 * en la política de empresa.
 * @author subiza.79082
 * @author izu.78236
 * @version 02/10/2015
 */
public class BussinessSystem implements TicketOffice, XMLRepresentable, Serializable{
    
    private HashMap <Integer, Client> clients;
    private HashSet <Sales> sales;
    private HashMap <Integer, Ticket> tickets;
    private AtomicInteger atomicInteger;
    private HashMap <String, Artist> artists;
    private HashMap <String, Collective> collectives;
    private HashMap <String, Concert> concerts;
    private HashMap <String, Exhibition> exhibitions;
    private HashMap <String, Festival> festivals;
    private HashMap <String, Location> locations;
    private AtomicInteger AITickets;
    
    public BussinessSystem () {
        clients = new HashMap();
        sales = new HashSet();
        tickets = new HashMap();
        atomicInteger = new AtomicInteger();
        artists = new HashMap();
        collectives = new HashMap();
        concerts = new HashMap();
        exhibitions = new HashMap();
        festivals = new HashMap();
        locations = new HashMap();
        AITickets = new AtomicInteger();
    }
    
    public int getAtomicInteger(int numberOfPeople) {
        int firstId = atomicInteger.get();
        for (int i = 1; i <= numberOfPeople; i++)
            atomicInteger.getAndIncrement();
        return firstId;
    }
    
    /**
     * Adds a new concert to the system. Note that setting up the concert should not produce
     *  any kind of clash with the existing concerts, etc.
     * @param c The concert to be added
     * @return True if an only if the concert c was not null, was well formed and did
     *  not clash with the information already in the system. False otherwise.
     */
    @Override
    public boolean addNewConcert(Concert c){
        
        if(c != null){ // Si el concierto c no es nulo compruebo si se puede introducir                               
            if (!(concerts.containsKey(c.getName()) || festivals.containsKey(c.getName()) 
                    || exhibitions.containsKey(c.getName()))){// Si la respuesta es true entonces puedo añadir el concierto
                if(isConcertOK(c)){
                // Dentro del metodo comprueba que las condiciones para añadir el concierto son correctas
                    concerts.put(c.getName(), c);
                    return true;
                }                
                else return false; // El concierto está mal                
            }
            else return false; // El nombre del concierto ya existe
        }
        else return false; // El concierto c es nulo y por tanto no se puede introducir
        
    }
    
    /**
     * Replace an existing concert. The system must be able to indentify the concert
     *  as "already existing", then proceed for its replacement.
     * @param c The concert to be added
     * @return True if an only if the concert c was not null, was well formed and did
     *  not clash with the information already in the system. False otherwise.
     */
    @Override
    public boolean replaceConcert(Concert c){
        
        if ((c != null) && //Si el concierto no es null
                (concerts.containsKey(c.getName())) && //Si encuentra el concierto a reemplazar
                    (isConcertOKToReplace(c))) { //Si es correcto dicho concierto
            
            concerts.replace(c.getName(),c); //Comprueba que las condiciones para reemplazar el concierto son correctas
            return true;
            
        }
        else return false; //El concierto es nulo, no se ha encontrado o no es correcto
        
    }
    
    /**
     * Deletes a concert from the system. In case c did not exist in the system, 
     *  this action has no impact.
     * @param c The concert to be removed
     * @return True if an only if the concert c was not null, and existed 
     *  already in the system. False otherwise.
     */
    @Override
    public boolean deleteConcert(Concert c){ // NO ESTOY SEGURO SI ESTA BIEN HECHO
        
        if ((c !=null) && //Si el concierto c no es nulo
                (concerts.containsKey(c.getName()))) { //Y el concierto existe en el HashMap lo eliminará
                Iterator i = festivals.values().iterator();
                Festival festivalAux;
                while (i.hasNext()) {
                    festivalAux = (Festival)i.next();
                    if (festivalAux.isConcertInFestival(c)) {
                        // Si entro querra decir que hemos encontrado el concierto en un festival
                        festivalAux.removeConcert(c);
                        //festivals.replace(festivalAux.getName(), festivalAux);
                        break;
                    }
                }
                return concerts.remove(c.getName(),c); //Devolvera true si esta y lo borra, false en caso contrario
        }
        else return false;
    }
    
    /**
     * Adds a new festival to the system
     * @param f The festival to be added
     * @return True if an only if f is not null, well formed and could be added to the system
     */
    @Override
    public boolean addNewFestival(Festival f){
        
        if(f != null){ // si el concierto c no es nulo compruebo si se puede introducir                               
            if(!(concerts.containsKey(f.getName()) || festivals.containsKey(f.getName()) || exhibitions.containsKey(f.getName()))){// Si la respuesta es true entonces puedo añadir el concierto
                festivals.put(f.getName(), f); 
                return true;
            }
            else return false; // El nombre del concierto ya existe
        }
        else return false; // el concierto c es nulo y por tanto no se puede introducir
    }
    
    /**
     * Adds a new concert to an existing festival
     * @param f The festival to which the concert is to be added
     * @param c The concert itself
     * @return True if and only if both arguments are not null, both are well formed 
     *  and the information could be added not causing any clash with the existing information
     */
    @Override
    public boolean addConcertToFestival(Festival f, Concert c){
        
        if((f != null) && (c != null)){ // Si los argumentos no son nulos procedo en el metodo
            if(festivals.containsKey(f.getName()) && concerts.containsKey(c.getName())){
                // Si el festival y el concierto existen procedo a mirar si dicho concierto esta en ese festival
                HashSet <Concert> concertsFestival = f.getConcerts();
                if(concertsFestival.contains(c)) return false; // El concierto existe dentro del festival
                else{ // El concierto no existe dentro del festival
                    f.addConcert(c);
                    if(f.getEndingDate().before(c.getClosingTimeConcert())){
                        // Si la fecha del ultimo concierto del festival es antes que el concierto añadido
                        // actualizo la hora de fin del festival
                        f.setClosingDateFestival((FechaCompleta)c.getClosingTimeConcert());
                        f.setClosingTimeFestival((FechaCompleta)c.getClosingTimeConcert());                        
                    }
                    else if(f.getStartDate().after(c.getStartDate())){
                        // Si la fecha del concierto que abre el festival es posterior a la
                        // fecha del nuevo concierto añadido al festival actualizo las fechas
                        f.setStartDateFestival((FechaCompleta)c.getStartDate());
                        f.setStartTimeFestival((FechaCompleta)c.getStartTimeConcert());  
                    }
                    return true;
                }
            }
            else return false; // Si el concierto o el festival no existen en el sistema
        }
        else return false; // Alguno de los argumentos o ambos son nulos
    }
    
    /**
     * Replaces the information in the sytem related to the Festival f.
     * @param f The new version of the festival
     * @return True if an only if a previous version of the festival existed,
     *  f is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    @Override
    public boolean replaceFestival(Festival f){
        
        if(f != null){ // Si el festival a reemplazar no es nulo procedo a su reemplazo
            if(festivals.containsKey(f.getName())){ // Si encuentra el festival a reemplazar lo reemplaza
                festivals.replace(f.getName(), f);
                return true;
            }
            else return false; // El festival que quiere reemplazar no existe
        }
        else return false; // El festival f es nulo
        
    }
    
    /**
     * Deletes the festival f from the system. It also deletes all of the concerts 
     *      associated with it.
     * @param f The festival to be removed
     * @return True if the festival existed and could be removed, together with all 
     *  the concerts it was composed of.
     */
    @Override
    public boolean deleteFestival(Festival f){
        
        if(f != null){ // Si no es null procedo a su eliminacion
            if(festivals.containsValue(f)){ 
                // Si el festival que quiere eliminar existe dentro de mi coleccion
                // procedo a su eliminacion junto a la de sus conciertos
                Festival festivalToRemove = festivals.get(f.getName());
                // Creo un iterador del HashSet que contiene todos los conciertos del festival
                // a eliminar y voy eliminando uno a uno los conciertos de mi coleccion
                Iterator i = festivalToRemove.getConcerts().iterator();
                Concert concertAux = null;
                while(i.hasNext()){
                    concertAux = (Concert)i.next();
                    concerts.remove(concertAux.getName(), concertAux);
                }
                // Una vez he eliminado todos los conciertos del festival f
                // procedo a eliminar el festival de la colección.
                return festivals.remove(f.getName(), f);
            }
            else return false; // El festival no existe dentro de la colección
        }
        else return false; // El festival f es nulo
    }
    
    /**
     * Adds an exhibition to the system.
     * @param e The exhibition to be added.
     * @return True if e is not null, well formed and does not produce a clash with 
     *  the information already in the system.
     */
    @Override
    public boolean addNewExhibition(Exhibition e){
        
        if(e != null){ 
            // Si la exhibicion e no es nula procedo a comprobar si se puede añadir a la colección            
            if(!(concerts.containsKey(e.getName()) || festivals.containsKey(e.getName()) || exhibitions.containsKey(e.getName()))){
                // Si entra aqui quiere decir que el nombre de la exhibicion es
                // único dentro de los eventos del sistema, ahora compruebo si es correcto
                if(isExhibitionOK(e)){
                    // Si entra quiere decir que la exhibicion cumple los requisitos necesarios
                    // para poder ser introducido al sistema
                    exhibitions.put(e.getName(),e);
                    return true;
                }
                else return false; // La exhibition no cumple los requisitos necesarios
            }
            else return false; // La exhibicion tiene el mismo nombre que otro evento por lo tanto no se introduce
        }
        else return false; // La exhibición e es nula
    }
    
    /**
     * Replaces the information in the sytem related to the Exhibition e.
     * @param e The new version of the exhibition
     * @return True if an only if a previous version of the exhibition existed,
     *  e is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    @Override
    public boolean replaceExhibition(Exhibition e){
        
        if(e != null){
            // Si la exhibicion e no es nula paso a reemplazarla
            if(exhibitions.containsKey(e.getName())){
                // Si entra quiere decir que ha encontrado la exhibicion en el sistema
                // ahora procedo a comprobar que la exhibicion e es correcta para poder
                // reemplazarla
                if(isExhibitionOKToReplace(e)){
                    // Si ha pasado por aqui quiere decir que cumple los requisitos
                    // para poder ser reemplazado
                    exhibitions.replace(e.getName(), e);
                    return true;
                }
                else return false; // La exhibicion no es correcta y por lo tanto no se puede reemplazar
            }
            else return false; // La exhibicion a reemplazar no se encuentra en el sistema
        }
        else return false; // La exhibicion a reemplazar es nula
    }
    
    /**
     * Deletes an exhibition from the system
     * @param e The exhibition to be deleted
     * @return True if and only if e is not null, e existed in the systema and it could be
     *  removed from the system
     */
    @Override
    public boolean deleteExhibition(Exhibition e){
        if(e != null){ // Compruebo que la exhibicion e no sea nula
            if(exhibitions.containsValue(e)){
                // Si el sistema contiene la exhibicion a eliminar procedemos a eliminarla
                return exhibitions.remove(e.getName(), e);
            }
            else return false; // El sistema no contiene a la exhibicion
        }
        else return false; // La exhibicion a eliminar es nula
    }
    
    /**
     * Checks whether an event is already registered in the system
     * @param e The event to be checked
     * @return True if and only if e is not null, well formed and did exist in the system
     */
    @Override
    public boolean existsEvent(Event e){
        
        if(e instanceof Concert) return concerts.containsValue(e);
        else if(e instanceof Festival) return festivals.containsValue(e);
        else if(e instanceof Exhibition) return exhibitions.containsValue(e);
        return false;
        
    }
    
    /**
     * Retrieves all the events whose name matches (partially) with the name
     * @param name Full or partial name of the events
     * @return A list of events, potentially being empty
     */
    @Override
    public Event[] retrieveEvents(String name){
        
        //Creamos un ArrayList porque un array no es dinámico
        ArrayList <Event> al = new ArrayList();
        Iterator i = concerts.values().iterator();
        Iterator j = festivals.values().iterator();
        Iterator z = exhibitions.values().iterator();

        // Recorro todos los eventos mirando uno a uno si los nombres de los mismos
        // tiene parcial o totalmente el nombre que me pasan como argumento y los guardo
        // en el array de eventos
        while (i.hasNext()) {
            Concert concertAux = (Concert)i.next();
            if (concertAux.getName().contains(name)) al.add(concertAux);
        }
        while (j.hasNext()){
            Festival festivalAux = (Festival)j.next();
            if (festivalAux.getName().contains(name)) al.add(festivalAux);
            
        }
        while (z.hasNext()) {
            Exhibition exhibitionAux = (Exhibition)z.next();
            if (exhibitionAux.getName().contains(name)) al.add(exhibitionAux);
        }
        return (Event[]) al.toArray(new Event[al.size()]);
        
    }
    
    /**
     * Retrieves the event whose name matches with the name
     * @param name Full name of the event
     * @return The event that matches with the name given
     */
    public Event getEvent(String name){
        
        //Creamos un ArrayList porque un array no es dinámico
        Iterator i = concerts.values().iterator();
        Iterator j = festivals.values().iterator();
        Iterator z = exhibitions.values().iterator();

        // Recorro todos los eventos mirando uno a uno si los nombres de los mismos
        // tiene parcial o totalmente el nombre que me pasan como argumento y los guardo
        // en el array de eventos
        while (i.hasNext()) {
            Concert concertAux = (Concert)i.next();
            if (concertAux.getName().contains(name)) return concertAux;
        }
        while (j.hasNext()){
            Festival festivalAux = (Festival)j.next();
            if (festivalAux.getName().contains(name)) return festivalAux;
            
        }
        while (z.hasNext()) {
            Exhibition exhibitionAux = (Exhibition)z.next();
            if (exhibitionAux.getName().contains(name)) return exhibitionAux;
        }
        return null;
    }
    
    /**
     * Retrieves all the events associated with an specific location
     * @param loc Location of interest
     * @return A list of events, potentially being empty
     */
    @Override
    public Event[] retrieveEvents(Location loc){
        
        //Creamos un ArrayList porque un array no es dinámico
        ArrayList <Event> al = new ArrayList();
        Iterator i = concerts.values().iterator();
        Iterator z = exhibitions.values().iterator();
        
        // Miro las localizaciones de todos los conciertos y exhibiciones y las comparo
        // con la localizacion que han pasado como argumento a ver si es la que busco
        // en tal caso lo guardo en mi array de localizaciones
        while (i.hasNext()) {
            Concert concertAux = (Concert)i.next();
            if (concertAux.getLocation().equals(loc)) al.add(concertAux);
        }
        while (z.hasNext()) {
            Exhibition exhibitionAux = (Exhibition)z.next();
            if (exhibitionAux.getLocation().equals(loc)) al.add(exhibitionAux);
        }
        return (Event[]) al.toArray(new Event[al.size()]);
        
    }
    
    /**
     * Retrieves all the events at some given date
     * @param d Date of interest
     * @return A list of events, potentially being empty
     */
    @Override
    public Event[] retrieveEvents(Date d){
        
        //Creamos un ArrayList porque un array no es dinámico
        ArrayList <Event> al = new ArrayList();
        Iterator i = concerts.values().iterator();
        Iterator j = festivals.values().iterator();
        Iterator z = exhibitions.values().iterator();
        
        // Compruebo todos los eventos comparando su fecha de inicio con la fecha
        // que me han pasado como argumento y los que coincidan los introduzco en
        // el array de eventos
        while (i.hasNext()) {
            Concert concertAux = (Concert)i.next();
            if (concertAux.getStartDate().equals(d)) al.add(concertAux);
        }
        while (j.hasNext()) {
            Festival festivalAux = (Festival)j.next();
            if (festivalAux.getStartDate().equals(d)) al.add(festivalAux);
        }
        while (z.hasNext()) {
            Exhibition exhibitionAux = (Exhibition)z.next();
            if (exhibitionAux.getStartDate().equals(d)) al.add(exhibitionAux);
        }
        return (Event[]) al.toArray(new Event[al.size()]);

    }
    
    /**
     * Adds a new artist to the system
     * @param a The new artist
     * @return True if the artist a is not null, is well formed and could be added to the
     *  system
     */
    @Override
    public boolean addArtist(Artist a){
        if(a != null){ // Si el artista a no es nulo procedo a añadirlo
            if(!(artists.containsKey(a.getName())) || collectives.containsKey(a.getName())){
                // Si el nombre del nuevo artista no esta cogido por otro artista
                // o colectivo procedo a añadir
                artists.put(a.getName(), a);
                return true;
            }
            else return false; // El nombre del artista a añadir ya esta cogido
        }
        else return false; // El artista a es nulo
    }
    
    /**
     * Adds a new collective of artists to the system
     * @param c The new collective
     * @return True if the collective c is not null, is well formed and could be added to the
     *  system
     */
    @Override
    public boolean addCollective(Collective c){
        if(c != null){ // Si el colectivo a no es nulo procedo a añadirlo
            if(!(artists.containsKey(c.getName())) || collectives.containsKey(c.getName())){
                // Si el nombre del nuevo colectivo no esta cogido por otro artista
                // o colectivo procedo a añadir
                collectives.put(c.getName(), c);
                return true;
            }
            else return false; // El nombre del colectivo a añadir ya esta cogido            
        }
        else return false; // El colectivo c es nulo
    }
    
    /**
     * Replaces the information in the sytem related to the artist e.
     * @param a The new version of the artist
     * @return True if an only if a previous version of the artist existed,
     *  e is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    @Override
    public boolean modifyArtist(Artist a){
        if(a != null){ // Si el artista a no es nulo procedo a ver si puedo modificarlo            
            if(artists.containsKey(a.getName())){
                // Si el artista a modificar existe en el sistema
                // procedo a modificarlo
                artists.replace(a.getName(), a);
                return true;
            }
            else return false; // El artista a no existe en el sistema
        }
        else return false; // El artista a es nulo
    }
    
    /**
     * Replaces the information in the sytem related to the collective e.
     * @param c The new version of the collective
     * @return True if an only if a previous version of the collective existed,
     *  e is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    @Override
    public boolean modifyCollective(Collective c){
        if(c != null){ // Si el colectivo c no es nulo procedo a ver si puedo modificarlo            
            if(artists.containsKey(c.getName())){
                // Si el colectivo a modificar existe en el sistema
                // procedo a modificarlo
                collectives.replace(c.getName(), c);
                return true;
            }
            else return false; // El colectivo c no existe en el sistema
        }
        else return false; // El colectivo c es nulo
    }
    
    /**
     * Deletes the record of a performer from the system, provided it has no
     *  events to which it is associated
     * @param performerName The name of the performer
     * @return True if and only if the performer with that name existed and could be removed.
     */
    @Override
    public boolean removePerformer(String performerName){
        
        if(artists.containsKey(performerName)){
            // Si entro aqui quiere decir que el performer a eliminar
            // es un artista, ahora procedo a eliminar los eventos
            Artist artista = artists.get(performerName);
            removeEventsOfPerformer(artista);
            // Ahora miro con un iterador a ver si dicho artista pertenecia a algun colectivo
            // en tal caso lo eliminare del colectivo
            Iterator i = collectives.values().iterator();
            Collective collectiveAux = null;
            while(i.hasNext()){
                collectiveAux = (Collective)i.next();
                if(collectiveAux.isArtistInCollective(artista)){
                    // Si el artista pertence al colectivo lo elimino
                    // de su HashSet de artistas
                    collectiveAux.removeArtist(artista);
                    break;
                }                              
            }
            return artists.remove(performerName, artista);
        }
        else if(collectives.containsKey(performerName)){
            // Si entro aqui quiere decir que el performer a eliminar
            // es un colectivo, ahora procedo a eliminar los eventos
            Collective colectivo = collectives.get(performerName);
            removeEventsOfPerformer(colectivo);
            return collectives.remove(performerName, colectivo);
        }
        // El nombre del performer no coincide con ningún performer del sistema, por lo tanto no lo eliminamos
        else return false;         
    }
    
    /**
     * Checkes whether there exist a performer with that name in the system
     * @param performerName Name of interest
     * @return True if and only if it exists
     */
    @Override
    public boolean existsPerformer(String performerName){
        // Si el performerName coincide con algún nombre de algún artista o colectivo entro aquí
        if(artists.containsKey(performerName) || collectives.containsKey(performerName)) return true;
        else return false; // El performer no existe
    }
    
    /**
     * Checkes whether there exist a performer with that name in the system
     * @param artistName Name of interest
     * @return True if and only if it exists
     */
    @Override
    public boolean existsArtist(String artistName){
        // Si el performerName coincide con algún nombre de algún artista o colectivo entro aquí
        if(artists.containsKey(artistName)) return true;
        else return false; // El performer no existe
    }
    
    /**
     * Checkes whether there exist a performer with that name in the system
     * @param artistName Name of interest
     * @return True if and only if it exists
     */
    public boolean existsCollective(String artistName){
        // Si el performerName coincide con algún nombre de algún artista o colectivo entro aquí
        if(collectives.containsKey(artistName)) return true;
        else return false; // El performer no existe
    }
    
    /**
     * Retrieves the record of a performer from the system by its name.
     * @param performerName The name of the performer
     * @return The performer, if existing. Null otherwise.
     */
    @Override
    public Performer retrievePerformer(String performerName){
        // Si el performer es un artista
        if(artists.containsKey(performerName)) return artists.get(performerName);
        // Si el performer es un colectivo
        else if(collectives.containsKey(performerName)) return collectives.get(performerName);
        // Si el performer no existe envio un performer nulo
        else return null;        
    }
    
    // Client introduction, update and modification
    
    /**
     * Adds a new client to the system
     * @param c The client to be added
     * @return True if and only if c is not null, well formed and could be added
     *  to the system
     */
    @Override
    public boolean addClient(Client c) {
        Date actualDate = new Date();
        if ((c != null) && ((actualDate.getYear() + 1900) - (c.getBirthday().getAnio()) >= 18)) {
            clients.put(c.getId(), c);
            return true;
        }
        else return false;
    }
    
    /**
     * Replaces the information in the sytem related to the Client c.
     * @param c The new version of the client
     * @return True if an only if a previous version of the client existed,
     *  c is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    public boolean modifyClient(Client c) {
        Date actualDate = new Date();
        if (clients.containsKey(c.getId())) { //Si una versión previa del cliente existe
            if ((actualDate.getYear() + 1900) - (c.getBirthday().getYear() + 1900) >= 18) {
                clients.replace(c.getId(), clients.get(c.getId()), c);
                return true;
            }
            else return false;
        }
        else return false; 
    }
    
    /**
     * Adds a new card to the list of cards of a given client
     * @param c     The client
     * @param cCard The identified of the new card
     * @return  True if and only if the client exists, cCard is well formed and 
     *      the card did not exist already for that client.
     */
    public boolean addCardToClient(Client c, String cCard) {

        if (clients.containsKey(c.getId())) { //Si el cliente existe
            clients.get(c.getId()).addCreditCard(cCard);
            return true;
        }
        else return false;
        
    }
    
    /**
     * Checks whether a client exists in the system
     * @param c The client to be checked
     * @return True if an only if c is not null, well formed and exists in the system.
     */
    public boolean containsClient(Client c) {
        if (c != null) return clients.containsKey(c.getId());
        else return false;
    }
    
    /**
     * Checks whether there exits a client with an identifier matching the argument
     * @param id Identifier of interest
     * @return True if an only if a client has the given identifier
     */
    public boolean containsClient(int id) {
        return clients.containsKey(id);
    }
    
    /**
     * Retrieves the client associated with an identifier
     * @param identifier Idenfifier of interest
     * @return The client, or null if no such Client exists
     */
    public Client retrieveClient(int identifier) {
        if (clients.containsKey(identifier)) return clients.get(identifier);
        else return null;
    }
    
    /**
     * Retrieves the tickets associated to a given client
     * @param c The client of interest
     * @return The list of tickets that has been sold to the client, if any.
     */
    public Ticket[] getListOfTickets(Client c) {
        //Metemos todos los tickets en un ArrayList porque es dinámico y después
        //pasamos todos los tickets contenidos en el ArrayList a un array
        ArrayList <Ticket> alTickets = new ArrayList();
        
        Iterator i = sales.iterator();
        Sales saleAux;
        while (i.hasNext()) {
            saleAux = (Sales)i.next();
            if (saleAux.getClient().equals(c)) {
                alTickets.add(saleAux.getTicket());
            }
        }
        //Si no hay tickets vendidos al cliente dado, devolvemos null
        if (alTickets.isEmpty()) return null;
        else return (Ticket[]) alTickets.toArray(new Ticket[alTickets.size()]);
    }
    
    /**
     * Retrieves the amount of money spent by the client in tickets
     * @param c The client of interest
     * @return The sum of the prices of the tickets
     */
    public float getTotalSpending(Client c) {
        float totalSpent = 0;
        
        Iterator i = sales.iterator();
        Sales saleAux = null;
        while (i.hasNext()) {
            saleAux = (Sales)i.next();
            if (saleAux.getClient().equals(c)) {
                totalSpent += saleAux.getPrice();
            }
        }
        return totalSpent;
    }
    
    // Ticket management
    
    /**
     * Adds a new ticket to the pool
     * @param t The new ticket
     * @return True if an only if t is not null, it is well formed and can be added 
     *  to the system
     */
    public boolean addNewTicket(Ticket t) {
        
        boolean respuesta;
        // Si el ticket o el evento asociado al mismo es nulo no lo añado
        if ((t != null) && (t.getEvent() != null)) {
            // Si el evento al que esta asociado no existe no se añade dicho ticket
            if((concerts.containsKey(t.getEvent().getName())) || (festivals.containsKey(t.getEvent().getName())) || (exhibitions.containsKey(t.getEvent().getName()))){
                
                if(!hasIDCollision(t)){
                    // Si los id's del ticket no coinciden con ningún otro ticket
                    // del sistema lo introduzco al mismo
                    tickets.put(AITickets.getAndIncrement(), t);
                    respuesta = true;
                }
                else{
                    // El ticket contiene id's ya existentes en el sistema
                    respuesta = false;
                }
                
            }
            else{
                // El evento asociado al ticket no existe en el sistema
                // por lo tanto no lo añado
                respuesta = false;
            }
        }
        else respuesta =  false; // El ticket t o el evento asociado al mismo son nulos
        return respuesta;
        
    }
    
    /**
     * Checks whether there exists some collision of the tickets in the system and
     *  the ticket t
     * @param t The candidate ticket
     * @return True if an only if the ticket t contains event id's which are already
     *      part of another ticket.
     */
    public boolean hasIDCollision(Ticket t) {
        
        int[] identifiers = t.getIdentifiers();
        boolean respuesta;
        for (int j = 0; j < identifiers.length; j++) {
            Iterator i = tickets.values().iterator();
            Ticket ticketAux = null;
            while (i.hasNext()) {
                ticketAux = (Ticket)i.next();
                if ((ticketAux.checkIdentifierInTicket(identifiers[j])) && (ticketAux.getEvent().equals(t.getEvent()))) {
                    respuesta = true;
                }
            }
        }
        respuesta = false;
        
        return respuesta;
    }
    
    /**
    * Checks whether an identifier is not assigned to any ticket.
    * @param e The event of interest
    * @param id    The identifier of interest
    * @return True if and only if the Event e exists in the system, and no ticket
    *      includes the identifier id
    */
    public boolean availableTicketID(Event e, int id) {

        boolean respuesta;
        if (existsEvent(e)) {
            Iterator i = tickets.values().iterator();
            Ticket ticketAux = null;            
            while (i.hasNext()) {
                ticketAux = (Ticket)i.next();
                if (ticketAux.getEvent().equals(e)) {
                    if (ticketAux.checkIdentifierInTicket(id)) respuesta = false;
                }
            }
            respuesta = true;
        }
        else respuesta = false;
        
        return respuesta;
        
    }
    
    /**
     * Let the system know that the identifier id, as part of the ticket t,
     *  has been used to access the event e.
     * @param t The ticket
     * @param e The event
     * @param id    The identifier
     * @return True if and only if the Ticket exists, is associated with the Event e,
     *      contains the identifier id and it was not used before.
     */
    public boolean setIDUsed(Ticket t,Event e, int id) {
        if (tickets.containsValue(t)) { //Si el ticket existe
            if (t.getEvent().equals(e)) { //Si el ticket está asociado al evento e
                if (t.checkIdentifierInTicket(id)) { //Si el ticket contiene el identificador
                    return (t.setIDUsed(id)); //Devuelve verdadero si el id no había sido usado previamente
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }
    
    //Sales and so
    /**
     * Registers the sale of an existing ticket to an existing client.
     * @param t     The ticket to be sold
     * @param c     The client who buys the ticket
     * @param price The price of the sale
     * @param cCard The card the transaction was performed with
     * @return  True if and only if the ticket, client and card existed, and the 
     *      sale could be registered (e.g. the ticket was not sold in beforehand, etc.)
     */
    public boolean addSale(Ticket t,Client c,Float price,String cCard) {
        Calendar actualDate = Calendar.getInstance();
        actualDate.setTime(new Date());
        if (tickets.containsValue(t) && clients.containsValue(c) && c.isCreditCard(cCard) && !t.isSold()) {
            //c.addSaleToClient(t);
            t.setSold(true);
            sales.add(new Sales(t, c, price, cCard, new FechaCompleta(actualDate.get(Calendar.DAY_OF_MONTH), 
                actualDate.get(Calendar.MONTH)+1, actualDate.get(Calendar.YEAR), 
                actualDate.get(Calendar.HOUR), actualDate.get(Calendar.MINUTE))));
            return true;
        }
        else return false;
    }
    
    public boolean deleteSale(Sales s){
        
        // Como no se nos indica nada, voy a considerar que si se anula una Sale
        // el ticket vuelve a esta de nuevo a la venta de modo que no elimino el
        // ticket asociado.
        
        // Pongo el ticket asociado de nuevo a la venta
        Ticket t = s.getTicket();
        t.setSold(false);
        // Si se elimina correctamente devolvera true en caso
        // contrario devolvera false
        return sales.remove(s);
        
    }
    
    // Locations
    
    /**
     * Adds a new location to the system
     * @param loc   The location
     * @return  True if and only if the location is not null, is well formed and 
     *  could be added to the system.
     */
    public boolean addLocation(Location loc) {
        if (loc != null) {
            locations.put(loc.getName(), loc);
            return true;
        }
        else return false;
    }
    
    /**
     * Retrieves a location by its exact name
     * @param name  The name of the location
     * @return  The location with that name, or null if it does not exist.
     */
    public Location getLocation(String name) {
        if (locations.containsKey(name)) return locations.get(name);
        else return null;
    }
    
    /**
     * Deletes a location from the system. This can only be done if there is no events
     *  associated with that location.
     * @param loc   The location to be removed
     * @return  True if an only if the location loc existed and could be removed.
     */
    public boolean deleteLocation(Location loc) {
        //exhibition y concert
        if (locations.containsKey(loc.getName())) { //If loc exists
            Iterator i = concerts.values().iterator();
            Concert concertAux;
            while (i.hasNext()) {
                concertAux = (Concert)i.next();
                //If there is a concert associated with loc
                if (concertAux.getLocation().equals(loc)) {
                    return false;
                }
            }
            Iterator j = exhibitions.values().iterator();
            Exhibition exhibitionAux;
            while (i.hasNext()) {
                exhibitionAux = (Exhibition)j.next();
                //If there is an exhibition associated with loc
                if (exhibitionAux.getLocation().equals(loc)) {
                    return false;
                }
            }
            return locations.remove(loc.getName(), loc);
        }
        else return false;        
    }
    
    /**
     * Retrieve the locations in the system with, at least, minCapacity attendance limit.
     * @param minCapacity   Attendance threshold
     * @return A list of 0+ locations
     */
    public Location[] getLocations(int minCapacity) {
        
        ArrayList <Location> al = new ArrayList();
        Iterator i = locations.values().iterator();
        Location locationAux = null;
        while (i.hasNext()) {
            locationAux = (Location)i.next();
            if (locationAux.getMaxCapacity() >= minCapacity) al.add(locationAux);
        }
        return (Location[]) al.toArray(new Location[al.size()]);
        
    }
    
    // Mira si un concierto cumple los requisitos para poder ser añadido al sistema
    private boolean isConcertOK(Concert c){
        
        // Si el performer pertenece o a un colectivo o a un artista y ademas la localizacion existe procedo a añadir el concierto
        if( (artists.containsValue(c.getPerformer()) || collectives.containsValue(c.getPerformer())) && (locations.containsValue(c.getLocation())) ){        

            ArrayList al = new ArrayList(concerts.values());
            Iterator i = al.iterator();
            while(i.hasNext()){
                Concert concertAux = (Concert)i.next();
                if(concertAux.getPerformer().getName().equalsIgnoreCase(c.getPerformer().getName()) && concertAux.getStartDate().equals(c.getStartDate())){
                    //Quiere decir que el performer de dicho concierto actua el mismo dia
                    //por lo tanto no puede introducirse el concierto
                    return false;
                }
                else if(concertAux.getLocation().getName().equalsIgnoreCase(c.getLocation().getName()) && concertAux.getStartDate().equals(c.getStartDate())){
                    //Quiere decir que ambos conciertos tienen lugar en la
                    //misma localizacion en la misma fecha
                    return false;
                }
            }
            // Si ha salido del bucle y el programa sigue leyendo quiere decir no ha encontrado
            // un concierto donde el performer del concierto c actue el mismo día 
            return true;
        }
        else return false; // El performer o la localizacion del concierto no existen en el sistema        
    }
    
    // Mira si un concierto cumple los requisitos para poder ser reemplazado al sistema
    private boolean isConcertOKToReplace(Concert c){
        
        // Si el performer pertenece o a un colectivo o a un artista y ademas la localizacion existe procedo a añadir el concierto
        if( (artists.containsValue(c.getPerformer()) || collectives.containsValue(c.getPerformer())) && (locations.containsValue(c.getLocation())) ){        

            ArrayList al = new ArrayList(concerts.values());
            Iterator i = al.iterator();
            while(i.hasNext()){
                Concert concertAux = (Concert)i.next();
                if(!concertAux.getName().equalsIgnoreCase(c.getName())){
                    // Si no es el concierto que quiero reemplazar miro si es correcta
                    if(concertAux.getPerformer().equals(c.getPerformer()) && concertAux.getStartDate().equals(c.getStartDate())){
                        //Quiere decir que el performer de dicho concierto actua el mismo dia
                        //por lo tanto no puede introducirse el concierto
                        return false;
                    }
                    else if(concertAux.getLocation().equals(c.getLocation()) && concertAux.getStartDate().equals(c.getStartDate())){
                        //Quiere decir que ambos conciertos tienen lugar en la
                        //misma localizacion en la misma fecha
                        return false;
                    }
                }
            }
            // Si ha salido del bucle y el programa sigue leyendo quiere decir no ha encontrado
            // un concierto donde el performer del concierto c actue el mismo día 
            return true;

        }
        else return false; // El performer o la localizacion del concierto no existen en el sistema
    }

    // Mira si un exhibición cumple los requisitos para poder ser añadido al sistema
    private boolean isExhibitionOK(Exhibition e) {
       
        // Si el performer pertenece o a un colectivo o a un artista y ademas la localizacion existe
        // procedo a seguir comprobando la exhibicion
        if( (artists.containsValue(e.getPerformer()) || collectives.containsValue(e.getPerformer())) && (locations.containsValue(e.getLocation())) ){        

            // Creo un iterador para recorrer todas las exhibiciones del sistema
            // y poder comprobar que la exhibicion e es correcta para poder ser
            // añadida al sistema
            Iterator i = exhibitions.values().iterator();
            while(i.hasNext()){
                Exhibition exhibitionAux = (Exhibition)i.next();
                if(exhibitionAux.getPerformer().equals(e.getPerformer()) && exhibitionAux.getStartDate().equals(e.getStartDate())){
                    //Quiere decir que el performer de dicha exhibicion actua el mismo dia
                    //por lo tanto no puede introducirse la exhibicion
                    return false;
                }
                else if(exhibitionAux.getLocation().equals(e.getLocation()) && exhibitionAux.getStartDate().equals(e.getStartDate())){
                    //Quiere decir que ambas exhibiciones tienen lugar en la
                    //misma localizacion en la misma fecha
                    return false;
                }
            }
            // Si ha salido del bucle y el programa sigue leyendo quiere decir no ha encontrado
            // una exhibicion donde el performer de la exhibicion e actue el mismo día o la localizacion
            // donde se situa se encuentra ocupada el mismo dia
            return true;

        }
        else return false; // El performer o la localizacion de la exhibicion no existen en el sistema
    }
    
    // Mira si un exhibición cumple los requisitos para poder ser reemplazado al sistema
    private boolean isExhibitionOKToReplace(Exhibition e) {
       
        // Si el performer pertenece o a un colectivo o a un artista y ademas la localizacion existe
        // procedo a seguir comprobando la exhibicion
        if( (artists.containsValue(e.getPerformer()) || collectives.containsValue(e.getPerformer())) && (locations.containsValue(e.getLocation())) ){        

            // Creo un iterador para recorrer todas las exhibiciones del sistema
            // y poder comprobar que la exhibicion e es correcta para poder ser
            // añadida al sistema
            Iterator i = exhibitions.values().iterator();
            while(i.hasNext()){
                Exhibition exhibitionAux = (Exhibition)i.next();
                if(!exhibitionAux.getName().equalsIgnoreCase(e.getName())){ 
                    // Si no es la exhibicion que quiero reemplazar miro si es correcta
                    if(exhibitionAux.getPerformer().equals(e.getPerformer()) && exhibitionAux.getStartDate().equals(e.getStartDate())){
                        //Quiere decir que el performer de dicha exhibicion actua el mismo dia
                        //por lo tanto no puede introducirse la exhibicion
                        return false;
                    }
                    else if(exhibitionAux.getLocation().equals(e.getLocation()) && exhibitionAux.getStartDate().equals(e.getStartDate())){
                        //Quiere decir que ambas exhibiciones tienen lugar en la
                        //misma localizacion en la misma fecha
                        return false;
                    }
                }
            }
            // Si ha salido del bucle y el programa sigue leyendo quiere decir no ha encontrado
            // una exhibicion donde el performer de la exhibicion e actue el mismo día o la localizacion
            // donde se situa se encuentra ocupada el mismo dia
            return true;

        }
        else return false;// El performer o la localizacion de la exhibicion no existen en el sistema
    }

    private void removeEventsOfPerformer(Performer p) {
        
        // Creo los iteradores para poder recorrer mis colecciones en busca
        // de los eventos que contengan al performer para eliminarlos del sistema.
        Iterator i = concerts.values().iterator();        
        Iterator j = exhibitions.values().iterator(); 
        
        // Voy a eliminar solo conciertos y exhibiciones ya que los festivales al
        // tener mas de un concierto no se puede suspende todo el festival porque se vaya
        // un concierto, además al eliminar el concierto este se elimina en cascada
        // del festival al que perteneciese en el caso que estuviera en algún festival
        Concert concertAux = null;
        Festival festivalAux = null;
        Exhibition exhibitionAux = null;
        // Elimino conciertos
        while(i.hasNext()){
            concertAux = (Concert)i.next();
            // Si el concierto involucra el performer elimino dicho concierto
            if(concertAux.involvesPerformer(p)) deleteConcert(concertAux);
        }
        // Elimino exhibiciones
        while(j.hasNext()){
            exhibitionAux = (Exhibition)j.next();
            // Si el concierto involucra el performer elimino dicho concierto
            if(exhibitionAux.involvesPerformer(p)) deleteExhibition(exhibitionAux);
        }
    }
    
    //Métodos para tener acceso a los atributos privados     
    public HashMap getArtists() {
        return artists;
    }
    public HashMap getCollectives() {
        return collectives;
    }
    public HashMap getLocations() {
        return locations;
    }
    public HashMap getConcerts() {
        return concerts;
    }
    public HashMap getFestivals() {
        return festivals;
    }
    public HashMap getExhibitions() {
        return exhibitions;
    }
    public HashMap getTickets() {
        return tickets;
    }
    public HashMap getClients() {
        return clients;
    }
    public HashSet getSales() {
        return sales;
    }
    
    /**
     * Lee de un fichero una serie de tickets y los introduce al sistema
     * @param f   Fichero del cual voy a leer los tickets
     * @return El numero de tickets que he introducido correctamente
     * @throws java.io.IOException
     */
    public int importTickets(File f) throws IOException{
        
        int numTicketsOk = 0;
        //Hay que leer el fichero f y fila a fila ir añadiendo los tickets al sistema
                
        Sheet sheet;
        sheet = SpreadSheet.createFromFile(f).getSheet(0);
                       
        Ticket ticketAux = null;
        String USED = "Used";
        String NOTUSED = "Not used";
        // Elimino espacios para luego poder compararlos
        USED = USED.replace(" ", "");
        NOTUSED = NOTUSED.replace(" ", "");
        // Voy a recorrer todas las filas de la hoja de calculo, para ir añadiendo
        // los tickets uno a uno        
        
        for (int i = 0; i < sheet.getRowCount(); i++) {
            
            for (int j = 0; j < sheet.getColumnCount(); j++) {
                
                // Si entra en el if quiere decir que estamos en la primera posicion
                // de la fila y contiene el nombre del evento al que esta asociado el ticket
                if((!sheet.getCellAt(j,i).isEmpty()) && (j == 0)){
                                        
                    String nameEvent = sheet.getCellAt(j,i).getTextValue();
                    ticketAux = new Ticket(getEvent(nameEvent));
                    
                }
                // Ahora ya se seguro que la celda contiene algo que va a ser o id o si
                // esta usado o no la entrada
                else{
                    // Si se encuentra en columna par (j impar porque empieza en 0) quiere decir                   
                    // que contiene un ID si ademas la siguiente columna no esta vacia, quiere
                    // decir que almacena si la entrada es usada o no, dicho caso procedemos a
                    // añadirla.
                    // Si se da el caso que la primera casilla de la fila no esta, no creare dicho
                    // ticket porque la información recogida de la hoja de cálculo es inconsistente.                   
                                        
                    if((j%2 != 0)&&(!sheet.getCellAt(j+1,i).isEmpty())&&(!sheet.getCellAt(j,i).isEmpty())){
                                                
                        String usedOrNot = sheet.getCellAt(j+1,i).getTextValue();
                        // Elimino los espacios de la casilla para poder hacer una
                        // comparacion limpia.
                        usedOrNot = usedOrNot.replace(" ", "");
                        if(usedOrNot.equals(NOTUSED)){
                            // La entrada no esta usada
                            ticketAux.addTicketToTicket(Integer.parseInt(sheet.getCellAt(j,i).getTextValue()), false);                            
                        }
                        else if(usedOrNot.equals(USED)){
                            // La entrada esta usada
                            ticketAux.addTicketToTicket(Integer.parseInt(sheet.getCellAt(j,i).getTextValue()), true);
                        }
                    }
                }
            }
            
            // Ahora que ya he rellenado el ticketAux con todos los identificadores
            // lo introduzco en el BussinessSystem
            if(addNewTicket(ticketAux)){                
                numTicketsOk++;
                System.out.println(ticketAux.toString());
                // Lo vuelvo a poner a null para añadir otro ticket
                // en la siguiente iteración
                ticketAux = null;
            }
           
        }
        return numTicketsOk;
        
    }

    /**
     * Lee de un fichero una serie de conciertos y los introduce al sistema
     * @param f   Fichero del cual voy a leer los conciertos
     * @return El numero de conciertos que he introducido correctamente
     * @throws java.io.IOException
     */
    public int importConcerts(File f) throws IOException {
        
        Sheet sheet;
        sheet = SpreadSheet.createFromFile(f).getSheet(0);
        Concert concertAux = null;
        int numConcertsOk = 0;
        
        // Recorro la hoja de calculo fila a fila y voy creando
        // los conciertos con los datos de cada una de esas lineas
        for (int i = 0; i < sheet.getRowCount(); i++) {
            
            concertAux = new Concert(sheet.getCellAt(0,i).getTextValue(), retrievePerformer(sheet.getCellAt(1,i).getTextValue()), new FechaCompleta(sheet.getCellAt(2,i).getTextValue(),sheet.getCellAt(3,i).getTextValue()),
                new FechaCompleta(sheet.getCellAt(4,i).getTextValue(),sheet.getCellAt(5,i).getTextValue()),new FechaCompleta(sheet.getCellAt(6,i).getTextValue(),sheet.getCellAt(7,i).getTextValue()),
                new FechaCompleta(sheet.getCellAt(8,i).getTextValue(),sheet.getCellAt(9,i).getTextValue()), getLocation(sheet.getCellAt(10,i).getTextValue()));
            if(addNewConcert(concertAux)){
                numConcertsOk++;
            }
            concertAux = null;
            
        }
        return numConcertsOk;
        
    }
    
    /**
     * Lee de un fichero una serie de festivales y los introduce al sistema
     * @param f   Fichero del cual voy a leer los festivales
     * @return El numero de festivales que he introducido correctamente
     * @throws java.io.IOException
     */
    public int importFestivals(File f) throws IOException{
        
        Sheet sheet;
        sheet = SpreadSheet.createFromFile(f).getSheet(0);
        Festival festivalAux = null;
        int numFestivalesOk = 0;
        
        // Recorro la hoja de calculo fila a fila y voy creando
        // los festivales con los datos de cada una de esas lineas
        for (int i = 0; i < sheet.getRowCount(); i++) {
            
            // Este array de strings almacenara la informacion básica sobre
            // el festival salvo los conciertos, es decir, nombre y hora de
            // apertura, cierre, etc.
            String datosFestival[] = new String[9];
            // Esta variable cuenta el numero de conciertos añadido al festival
            // si no se añade ningún concierto al festival, este no se añade al sistema
            int numConciertosAdded = 0;
            
            for(int j = 0; j < sheet.getColumnCount(); j++){                                
                
                // Los primeros 9 datos de la hoja de calculo contendras el nombre del
                // festival asi como su fecha y hora de apertura, cierre, etc. Por eso
                // en la 9 primeras iteraciones del for entraremos y guardare dicha información
                // en un array de String para  cuando haya cogido todos los datos crear
                // el objeto Festival y ir añadiendo los conciertos
                if((!sheet.getCellAt(j,i).isEmpty()) && (j < 9)){
                    
                    datosFestival[j] = sheet.getCellAt(j,i).getTextValue();
                    if(j == 8){
                        // En j = 8 quiere decir que ya he almacenado todos los datos
                        // necesarios para crear el objeto festival
                        festivalAux = new Festival(datosFestival[0], new FechaCompleta(datosFestival[1],datosFestival[2]),
                            new FechaCompleta(datosFestival[3],datosFestival[4]), new FechaCompleta(datosFestival[5],datosFestival[6]),
                            new FechaCompleta(datosFestival[7],datosFestival[8]));
                    }
                    
                }
                // A partir de ahora todo lo que siga van a ser nombres de conciertos
                // asociados al festival que acabamos de crear de modo que ire añadiendolos
                // uno a uno.
                else{
                    // Si la casilla no esta vacia intento añadir dicho concierto
                    if(!sheet.getCellAt(j,i).isEmpty()){
                        
                        // Capto el nombre del concierto a introducir
                        String concertName = sheet.getCellAt(j,i).getTextValue();
                        // Voy a mirar que dicho concierto exista en el sistema
                        // en caso de que no exista no añadire dicho concierto al festival
                        if((getEvent(concertName) != null) && (getEvent(concertName) instanceof Concert)){
                                                    
                            // Si no es nulo y es un instancia de concierto 
                            // quiere decir que el concierto existe por tanto
                            // lo añado al festival.
                            if (festivalAux.addConcert((Concert)getEvent(concertName))) numConciertosAdded++;
                            
                        }
                        
                    }                    
                                        
                }
                
            }
            
            // Ahora que ya he rellenado el festivalAux compruebo primero si contiene conciertos
            // en caso de que no los haya no añadira el festival al sistema. Si contiene al menos
            // uno intentara añadirlo si la política del sistema permite añadir el festival, este
            // será añadido al sistema
            if(numConciertosAdded > 0){
                if(addNewFestival(festivalAux)){                
                    numFestivalesOk++;
                    System.out.println(festivalAux.toString());
                    // Lo vuelvo a poner a null para añadir otro ticket
                    // en la siguiente iteración
                    festivalAux = null;
                }
                numConciertosAdded = 0;
            }
            
        }
        return numFestivalesOk;
        
    }
    
    /* IMPLEMENTACIÓN DEL INTERFAZ XMLRepresentable */
    
    @Override
    public String toXML() {        
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("bussinessSystem", BussinessSystem.class);
        xStream.alias("publicBussinessSystem", PublicBussinessSystem.class);
        xStream.alias("sales", Sales.class);
        xStream.alias("ticket", Ticket.class);
        xStream.alias("client", Client.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("exhibition", Exhibition.class); 
        xStream.alias("collective", Collective.class);
        xStream.alias("location", Location.class);
        String xml2 = xStream.toXML(this);

        return xml2;
    }
    
    
    //Si el fichero ya existe, se sobreescribe
    @Override
    public boolean saveToXML(File f) {
        boolean respuesta = false;
        XStream xStream = new XStream(new DomDriver());
        FileWriter fichero = null;
        PrintWriter pw;
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
                // asegurarnos de que se cierra el fichero
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
    
    /**
     * Método para la lectura de ficheros
     * @param f Fichero del cual voy a leer la información para crear los objetos
     * @return Objeto BusinessSystem que contiene todos los objetos contenidos 
     * en el fichero (si han podido ser deserializados)
     * @throws XMLParsingException
     */
    public static BussinessSystem parseXMLFile(File f) throws XMLParsingException {
    
        BussinessSystem bs;
        
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("bussinessSystem", BussinessSystem.class);       
        xStream.alias("client", Client.class);
        xStream.alias("sales", Sales.class);
        xStream.alias("ticket", Ticket.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("location", Location.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        
        try {
            bs = (BussinessSystem)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return bs;
        
    }
   
}