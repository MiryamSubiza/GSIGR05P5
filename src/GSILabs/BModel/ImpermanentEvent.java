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
 * An impermanent event is an event that has a limited time span, typically 
 * restricted to minutes or hours. Although these events are somehow 
 *  time-lasting, they are considered to be punctual.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)    
 */
public interface ImpermanentEvent extends Event{
    
    /** 
     * Retrieves the date and time at which the Event starts. 
     * @return Date and time the event is starting
     */
    Date getStartDate();
    
    /**+
     * Location (unique) at which the event takes place
     * @return Date and time the event is ending
     */
    Location getLocation();
   
}
