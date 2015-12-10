/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */
package GSILabs.BSystem;

import GSILabs.BModel.*;
import GSILabs.connect.*;
import GSILabs.persistence.XMLParsingException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Implementación de los interfaces ClientGateway y EventGateway, 
 * representando el acceso remoto a la información en modo de lectura
 * y escritura de los clientes y eventos en el sistema.
 * @author subiza.79082
 * @author izu.78236
 * @version 23/11/2015
 */
public class PublicBussinessSystem extends BussinessSystem implements EventGateway, ClientGateway{        
    
    public PublicBussinessSystem(){
        super();
    }
    
    @Override
    public Client getClient(Integer id) throws RemoteException {
        
        // Uso el metodo ya implementado en bussinessSystem para eliminar el
        // cliente
        return super.retrieveClient(id);
        
    }

    @Override
    public Sales addFreeSale(Client c, Ticket t) throws RemoteException {
        
        // Añado la sale al sistema si es posible
        if(super.addSale(t, c, 0.0f, c.getFirstCreditCard())){
            
            // Busco la sale que acabo de introducir al sistema para
            // que el metodo la devuelvo
            HashSet sales = super.getSales();
            Iterator i = sales.iterator();
            boolean salida = false;
            Sales s = null;
            while(i.hasNext() && !salida){
                s = (Sales)i.next();
                if(s.getTicket().equals(t) && s.getClient().equals(c) && s.getPrice() == 0.0f){
                    salida = true;
                }
            }
            return s;
        }
        else{
            return null;
        }
        
    }

    @Override
    public Boolean removeSale(Sales s) throws RemoteException {
        
        // Uso el metodo ya implementado en bussinessSystem para eliminar el
        // Sales
        return super.deleteSale(s);
        
    }
    
    @Override
    public Event[] getEvents(String name) throws RemoteException {
        
        // Uso el metodo ya implementado en bussinessSystem para obtener el
        // listado de eventos que coincidan parcialmente con el string dado
        return super.retrieveEvents(name);
        
    }
    

    @Override
    public Concert getConcert(String name) throws RemoteException {
        
        // Obtengo el evento con dicho nombre
        Event e = super.getEvent(name);
        if(e instanceof Concert){
            // Con este if me aseguro que el evento con dicho nombre
            // sea un concierto y lo devuelvo
            return (Concert)e;
        }
        else{
            // En caso contrario devuelvo nulo
            return null;
        }
        
    }

    @Override
    public Festival getFestival(String name) throws RemoteException {
        
        // Obtengo el evento con dicho nombre
        // Uso el metodo ya implementado en bussinessSystem para eliminar el
        // concierto
        Event e = super.getEvent(name);
        if(e instanceof Festival){
            // Con este if me aseguro que el evento con dicho nombre
            // sea un festival y lo devuelvo
            return (Festival)e;
        }
        else{
            // En caso contrario devuelvo nulo
            return null;
        }
        
    }

    @Override
    public Boolean removeConcert(Concert c) throws RemoteException {
        
        // Uso el metodo ya implementado en bussinessSystem para eliminar el
        // concierto
        return deleteConcert(c);
        
    }

    @Override
    public Boolean removeFestival(Festival f) throws RemoteException {
        
        // Uso el metodo ya implementado en bussinessSystem para eliminar el
        // festival
        return deleteFestival(f);
        
    }

    @Override
    public Festival addConcertToFestival(String festivalName, Concert c) throws RemoteException {
        
        Event e = super.getEvent(festivalName);        
        if(e instanceof Festival){
            // Si el festival existe intento añadir el concierto y devuelvo
            // el festival actualizado
            this.addConcertToFestival((Festival)e, c);
            return (Festival)super.getEvent(festivalName);
        } 
        else {
            // En caso contrario devuelvo nulo
            return null;
        }
        
    }

    @Override
    public Boolean updateEvent(Event ev) throws RemoteException {
        
        // Distingo que tipo de evento es y una vez lo se uso el método
        // de reemplazo o update correspondiente a dicho evento ya creado
        // en la clase BussinessSystem
        if (ev instanceof Concert) {
            return super.replaceConcert((Concert)ev);
        }
        else if (ev instanceof Exhibition) {
            return super.replaceExhibition((Exhibition)ev);
        }
        else if (ev instanceof Festival) {
            return super.replaceFestival((Festival)ev);
        }
        else
            return false;
        
    }

    @Override
    public Boolean removeEvent(Event ev) throws RemoteException {
        
        if(ev instanceof Concert){
            // Lo elimino con el método de eliminar conciertos
            // de la propia clase
            return super.deleteConcert((Concert)ev);
        }
        else if(ev instanceof Festival){
            // Lo elimino con el método de eliminar festivales
            // de la propia clase
            return super.deleteFestival((Festival)ev);
        }
        else if(ev instanceof Exhibition){
            // Lo elimino con el método de eliminar exhibiciones
            // de la propia clase
            return super.deleteExhibition((Exhibition)ev);
        }
        else{
            return false;
        }
        
    }
    
    /**
     * Método para la lectura de ficheros
     * @param f Fichero del cual voy a leer la información para crear los objetos
     * @return Objeto BusinessSystem que contiene todos los objetos contenidos 
     * en el fichero (si han podido ser deserializados)
     * @throws XMLParsingException
     */
    public static PublicBussinessSystem parseXMLFile(File f) throws XMLParsingException {
    
        PublicBussinessSystem bs;
        
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("bussinessSystem", BussinessSystem.class);
        xStream.alias("publicBussinessSystem", PublicBussinessSystem.class);
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
            bs = (PublicBussinessSystem)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return bs;
        
    }
   
}

