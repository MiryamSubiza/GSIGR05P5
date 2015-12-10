/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.connect;

import GSILabs.BModel.Concert;
import GSILabs.BModel.Event;
import GSILabs.BModel.Festival;
import GSILabs.BModel.Location;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
*	This interface represents a remote gateway to access in read-write mode 
 * 		information about the events in the system.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)
 */
public interface EventGateway extends BussinessGateway, EventFinder{
    
    /**
     * Retrives a concert from the system by exact match of the name.
     * @param name The name of the concert
     * @return The Concert, if existing, or null otherwise.
     * @throws RemoteException   If some error happens in the remove invocation.
     */   
    public Concert getConcert(String name) throws RemoteException;
    
    /**
     * Retrives a festival from the system by exact match of the name.
     * @param name The name of the concert
     * @return The Festival, if existing, or null otherwise.
     * @throws RemoteException   If some error happens in the remove invocation.
     */
    public Festival getFestival(String name) throws RemoteException;
    
    /**
     * Deletes a concert from the System
     * @param c The concert to be deleted
     * @return True if and only if existed in the system and could be deleted 
     * @throws RemoteException  If some error happens in the remove invocation.
     */
    public Boolean removeConcert(Concert c) throws RemoteException;
    
    /**
     * Deletes a festival from the System
     * @param f The festival to be deleted
     * @return True if and only if existed in the system and could be deleted 
     * @throws RemoteException  If some error happens in the remove invocation.
     */
    public Boolean removeFestival(Festival f) throws RemoteException;
    
    /**
     * Adds a concert to the festival
     * @param festivalName  Name of the festival to which the concert is to be added
     * @param c The concert itself
     * @return Returns the new version of the festival, whether it not contains the concert or not.
     *  If the festival did not exist, the value is null.
     * @throws RemoteException If some error happens in the remove invocation.
     */
    public Festival addConcertToFestival(String festivalName,Concert c) throws RemoteException;
    
    /**
     * Updates an event
     * @param ev New version of the event
     * @return True if the Event existed, and could be updated according to the 
     *  rules of the bussiness
     * @throws RemoteException  If some error happens in the remove invocation. 
     */
    public Boolean updateEvent(Event ev) throws RemoteException;
    
    /**
     * Deletes an event from the System
     * @param ev The event to be deleted
     * @return True if and only if existed and could be deleted.
     * @throws RemoteException   If some error happens in the remove invocation.
     */
    public Boolean removeEvent(Event ev) throws RemoteException;
    
}
