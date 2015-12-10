/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.connect;

import GSILabs.BModel.Event;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *	This interface represents a remote gateway to access in read mode 
 * 		information about the events in the system.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)
 */
public interface EventFinder extends BussinessGateway {
   
    /**
     * Retrieves an Event that matches EXACTLY (up to the case and/or spacing),
     *  the name.
     * @param name Name of the event
     * @return The event, or a null response
     * @throws RemoteException If some error happens in the remove invocation.
     */
    public Event getEvent(String name) throws RemoteException;
    
    /**
     * Retrieves all the Events that match, either partial o totally (up to the case and/or spacing),
     *  the name given as argument.
     * @param name Complete or partial name of the event
     * @return The list of events, that might eventually contain zero elements
     * @throws RemoteException If some error happens in the remove invocation.
     */
    public Event[] getEvents(String name) throws RemoteException;
    
    
    
}
