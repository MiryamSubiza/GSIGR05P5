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
 * Event that spans over several dates and/or locations.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)    
 */
public interface LastingEvent extends Event {
    
    /**
     * Retrieves the date and time at which the Event starts. 
     * @return Date and time the event is starting
     */
    Date getStartDate();
    
    /**
     * Retrieves the date and time at which the Event ends.
     * @return Date and time the event is ending
     */
    Date getEndingDate();

}
