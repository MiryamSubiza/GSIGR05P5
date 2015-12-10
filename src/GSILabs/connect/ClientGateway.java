/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.connect;

import GSILabs.BModel.Client;
import GSILabs.BModel.Event;
import GSILabs.BModel.Sales;
import GSILabs.BModel.Ticket;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
*	This interface represents a remote gateway to access in read-write mode 
 * 		information about the clients in the system.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)
 */
public interface ClientGateway extends BussinessGateway, EventFinder{
    
    
    /**
     * Retrieves the client by its ID
     * @param id Identifier of the client, expressed as an Integer
     * @return The cliente matching such interger, or null if it does not exist.
     * @throws RemoteException  If some error happens in the remove invocation.
     */
    public Client getClient(Integer id) throws RemoteException;
    
    /**
     * Adds a zero-cost sale to the system. That is, it gives away a ticket to 
     *  some given client. 
     * @param c The receiving client
     * @param t The ticket to the user
     * @return  The sale, if and only if it could be placed. That is, if the Ticket 
     *      and client exist in the system, and the Sale could be stored. Otherwise,
     *      the returned value is null.
     * @throws RemoteException  If some error happens in the remove invocation.
     */
    public Sales addFreeSale(Client c, Ticket t) throws RemoteException;
    
    /**
     * Deletes a sale from the Systems
     * @param s The sale to be deleted
     * @return True if and only if the sale existed and could be deleted.
     * @throws RemoteException  If some error happens in the remove invocation.
     */
    public Boolean removeSale(Sales s) throws RemoteException;
    

    
}
