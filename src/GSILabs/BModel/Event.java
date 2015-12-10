/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BModel;

import java.util.Date;

/**
 * An event is any happening for which a ticket can be purchased.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)    
 */
public interface Event {
    
    /**
     * Returns the name associated with the event
     * @return The name of the event.
     */
    String getName();
    
    /**
     * Returns the date or dates the Event takes place at.
     * @return return the 1+ dates trhough which the event spans
     */
    Date[] getDates();
    
    /**
     * Checks whether the performer p is somehow involved in the Event, either individually
     * or as part of a collective.
     * @param p The performer
     * @return true if an only if the event is correct and the performer is involved in it.
     */
    boolean involvesPerformer(Performer p);
    
    /**
     * Returns a list of the performers involved in the Event, either they take
     *  part in all of its activities or not. 
     * @return An array with the performers that take part in the event.
     */
    Performer[] getPerformers();
    
}