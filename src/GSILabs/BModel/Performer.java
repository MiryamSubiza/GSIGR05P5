/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BModel;

/**
 * A performer is any kind of person or group of person in charge of an event for which a 
 * ticket can be purchased.
 * @author carlos.lopez
 * @version 1.0 (04/09/2015)    
 */
public interface Performer {
    
    /**
     * Return the name of the performer. It must be unique, meaning that different Performer
     * cannot have a repeated name. This holds regardless of the type (class) of performer
     * they are
     * @return the artistic name of the performer
     */
    String getName();
    
    /**
     * Retrieves a textual representation of the work by the performer.
     * @return Description of the work of the performer
     */
    String getWorkDescription();
    
    /**
     * Overwrites the description for the given performer. This is done regardless
     * of the description that was previously associated with the performer (if any).
     * @param description New description for the work of the performer
     */
    void setWorkDescription(String description);
    
}