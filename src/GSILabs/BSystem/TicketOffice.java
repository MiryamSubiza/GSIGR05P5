/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BSystem;

import GSILabs.BModel.*;
import java.util.Date;

/**
 * This interface repersents the basic behaviour to be expected in a Computer System 
 * according to the rules in the lab gui
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)    
 */
public interface TicketOffice {
    
    // Event creation and management
    
    /**
     * Adds a new concert to the system. Note that setting up the concert should not produce
     *  any kind of clash with the existing concerts, etc.
     * @param c The concert to be added
     * @return True if an only if the concert c was not null, was well formed and did
     *  not clash with the information already in the system. False otherwise.
     */
    boolean addNewConcert(Concert c);
    
    /**
     * Replace an existing concert. The system must be able to indentify the concert
     *  as "already existing", then proceed for its replacement.
     * @param c The concert to be added
     * @return True if an only if the concert c was not null, was well formed and did
     *  not clash with the information already in the system. False otherwise.
     */
    boolean replaceConcert(Concert c);
    
    /**
     * Deletes a concert from the system. In case c did not exist in the system, 
     *  this action has no impact.
     * @param c The concert to be removed
     * @return True if an only if the concert c was not null, and existed 
     *  already in the system. False otherwise.
     */
    boolean deleteConcert(Concert c);
    
    /**
     * Adds an exhibition to the system.
     * @param e The exhibition to be added.
     * @return True if e is not null, well formed and does not produce a clash with 
     *  the information already in the system.
     */   
    boolean addNewExhibition(Exhibition e);
    
    /**
     * Replaces the information in the sytem related to the Exhibition e.
     * @param e The new version of the exhibition
     * @return True if an only if a previous version of the exhibition existed,
     *  e is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    boolean replaceExhibition(Exhibition e);
    
    /**
     * Deletes an exhibition from the system
     * @param e The exhibition to be deleted
     * @return True if and only if e is not null, e existed in the systema and it could be
     *  removed from the system
     */
    boolean deleteExhibition(Exhibition e);
    
    /**
     * Adds a new festival to the system
     * @param f The festival to be added
     * @return True if an only if f is not null, well formed and could be added to the system
     */
    boolean addNewFestival(Festival f);
    
    /**
     * Adds a new concert to an existing festival
     * @param f The festival to which the concert is to be added
     * @param c The concert itself
     * @return True if and only if both arguments are not null, both are well formed 
     *  and the information could be added not causing any clash with the existing information
     */
    boolean addConcertToFestival(Festival f,Concert c);
    
    /**
     * Replaces the information in the sytem related to the Festival f.
     * @param f The new version of the festival
     * @return True if an only if a previous version of the festival existed,
     *  f is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    boolean replaceFestival(Festival f);
    
    /**
     * Deletes the festival f from the system. It also deletes all of the concerts 
     *      associated with it.
     * @param f The festival to be removed
     * @return True if the festival existed and could be removed, together with all 
     *  the concerts it was composed of.
     */
    boolean deleteFestival(Festival f);
    
    /**
     * Checks whether an event is already registered in the system
     * @param e The event to be checked
     * @return True if and only if e is not null, well formed and did exist in the system
     */
    boolean existsEvent(Event e);
    
    /**
     * Retrieves all the events whose name matches (partially) with the name
     * @param name Full or partial name of the events
     * @return A list of events, potentially being empty
     */
    Event[] retrieveEvents(String name);
    
    /**
     * Retrieves all the events associated with an specific location
     * @param loc Location of interest
     * @return A list of events, potentially being empty
     */
    Event[] retrieveEvents(Location loc);
    
    /**
     * Retrieves all the events at some given date
     * @param d Date of interest
     * @return A list of events, potentially being empty
     */
    Event[] retrieveEvents(Date d);
    
    // Client introduction, update and modification
    
    /**
     * Adds a new client to the system
     * @param c The client to be added
     * @return True if and only if c is not null, well formed and could be added
     *  to the system
     */
    boolean addClient(Client c);
    
    /**
     * Replaces the information in the sytem related to the Client c.
     * @param c The new version of the client
     * @return True if an only if a previous version of the client existed,
     *  c is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    boolean modifyClient(Client c);
    
    /**
     * Adds a new card to the list of cards of a given client
     * @param c     The client
     * @param cCard The identified of the new card
     * @return  True if and only if the client exists, cCard is well formed and 
     *      the card did not exist already for that client.
     */
    boolean addCardToClient(Client c, String cCard);
    
    /**
     * Checks whether a client exists in the system
     * @param c The client to be checked
     * @return True if an only if c is not null, well formed and exists in the system.
     */
    boolean containsClient(Client c);
    
    /**
     * Checks whether there exits a client with an identifier matching the argument
     * @param id Identifier of interest
     * @return True if an only if a client has the given identifier
     */
    boolean containsClient(int id);
    
    /**
     * Retrieves the client associated with an identifier
     * @param identifier Idenfifier of interest
     * @return The client, or null if no such Client exists
     */
    Client retrieveClient(int identifier);
    
    /**
     * Retrieves the tickets associated to a given client
     * @param c The client of interest
     * @return The list of tickets that has been sold to the client, if any.
     */
    Ticket[] getListOfTickets(Client c);
    
    /**
     * Retrieves the amount of money spent by the client in tickets
     * @param c The client of interest
     * @return The sum of the prices of the tickets
     */
    float getTotalSpending(Client c);
    
    // Ticket management
    
    /**
     * Adds a new ticket to the pool
     * @param t The new ticket
     * @return True if an only if t is not null, it is well formed and can be added 
     *  to the system
     */
    boolean addNewTicket(Ticket t);
    
    /**
     * Checks whether there exists some collision of the tickets in the system and
     *  the ticket t
     * @param t The candidate ticket
     * @return True if an only if the ticket t contains event id's which are already
     *      part of another ticket.
     */
    boolean hasIDCollision(Ticket t);
    
    /**
     * Checks whether an identifier is not assigned to any ticket.
     * @param e The event of interest
     * @param id    The identifier of interest
     * @return True if and only if the Event e exists in the system, and no ticket
     *      includes the identifier id
     */
    boolean availableTicketID(Event e, int id);
    
    /**
     * Let the system know that the identifier id, as part of the ticket t,
     *  has been used to access the event e.
     * @param t The ticket
     * @param e The event
     * @param id    The identifier
     * @return True if and only if the Ticket exists, is associated with the Event e,
     *      contains the identifier id and it was not used before.
     */
    boolean setIDUsed(Ticket t,Event e, int id);
    
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
    boolean addSale(Ticket t,Client c,Float price,String cCard);
    
    
    // Locations
    
    /**
     * Adds a new location to the system
     * @param loc   The location
     * @return  True if and only if the location is not null, is well formed and 
     *  could be added to the system.
     */
    boolean addLocation(Location loc);
    
    /**
     * Retrieves a location by its exact name
     * @param name  The name of the location
     * @return  The location with that name, or null if it does not exist.
     */
    Location getLocation(String name);
    
    /**
     * Deletes a location from the system. This can only be done if there is no events
     *  associated with that location.
     * @param loc   The location to be removed
     * @return  True if an only if the location loc existed and could be removed.
     */
    boolean deleteLocation(Location loc);
    
    /**
     * Retrieve the locations in the system with, at least, minCapacity attendance limit.
     * @param minCapacity   Attendance threshold
     * @return A list of 0+ locations
     */
    Location[] getLocations(int minCapacity);
    
    // Performer management
    
    /**
     * Adds a new artist to the system
     * @param a The new artist
     * @return True if the artist a is not null, is well formed and could be added to the
     *  system
     */
    boolean addArtist(Artist a);
    
    /**
     * Adds a new collective of artists to the system
     * @param c The new collective
     * @return True if the collective c is not null, is well formed and could be added to the
     *  system
     */
    boolean addCollective(Collective c);
    
    /**
     * Replaces the information in the sytem related to the artist e.
     * @param a The new version of the artist
     * @return True if an only if a previous version of the artist existed,
     *  e is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    boolean modifyArtist(Artist a);
    
    /**
     * Replaces the information in the sytem related to the collective e.
     * @param c The new version of the collective
     * @return True if an only if a previous version of the collective existed,
     *  e is well formed, and it does not produce clashes with the information already
     *  in the system.
     */
    boolean modifyCollective(Collective c);
    
    /**
     * Deletes the record of a performer from the system, provided it has no
     *  events to which it is associated
     * @param performerName The name of the performer
     * @return True if and only if the performer with that name existed and could be removed.
     */
    boolean removePerformer(String performerName);
    
    /**
     * Checkes whether there exist a performer with that name in the system
     * @param performerName Name of interest
     * @return True if and only if it exists
     */
    boolean existsPerformer(String performerName);
    
    /**
     * Checkes whether there exist a performer with that name in the system
     * @param artistName Name of interest
     * @return True if and only if it exists
     */
    boolean existsArtist(String artistName);
    
    /**
     * Checkes whether there exist a performer with that name in the system
     * @param artistName Name of interest
     * @return True if and only if it exists
     */
    boolean existsCollective(String artistName);
    
    /**
     * Retrieves the record of a performer from the system by its name.
     * @param performerName The name of the performer
     * @return The performer, if existing. Null otherwise.
     */
    Performer retrievePerformer(String performerName);
    
        
}
