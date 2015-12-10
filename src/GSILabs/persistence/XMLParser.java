/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.persistence;

import GSILabs.BModel.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;

/**
 *
 * @author subiza.79082
 * @author izu.78236
 */
public class XMLParser {
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Artista si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto artista.
     * @throws GSILabs.persistence.XMLParsingException
     */
    public static Artist parseArtist(String str) throws XMLParsingException{
        
        // Si el artista no se puede deserializar devolveremos un artista nulo
        // para asegurarme de ello incializo el artista que devuelvo a nulo
        Artist art = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("artist", Artist.class);        
        try{
            art = (Artist)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return art;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Artista si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto artista.
     */
    public static Artist parseArtist(File f)throws XMLParsingException{
        
        // Si el artista no se puede deserializar devolveremos un artista nulo
        // para asegurarme de ello incializo el artista que devuelvo a nulo
        Artist art = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("artist", Artist.class);
        xStream.alias("fechacompleta", FechaCompleta.class);        
        try{
            art = (Artist)xStream.fromXML(f);
        }
        catch (XStreamException e){
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return art;
        
    }    
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Client si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Client.
     */
    public static Client parseClient(String str) throws XMLParsingException{
        
        Client cli = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("client", Client.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        try{
            cli = (Client)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return cli;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Client si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Client.
     */
    public static Client parseClient(File f) throws XMLParsingException{
        
        Client cli = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("client", Client.class);
        xStream.alias("fechacompleta", FechaCompleta.class);        
        try{
            cli = (Client)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return cli;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Collective si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Collective.
     */
    public static Collective parseCollective(String str) throws XMLParsingException{
        
        Collective col = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("collective", Collective.class);
        xStream.alias("artist", Artist.class);
        try{
            col = (Collective)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return col;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Collective si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Collective.
     */
    public static Collective parseCollective(File f) throws XMLParsingException{
        
        Collective col = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("collective", Collective.class);
        xStream.alias("artist", Artist.class);
        try{
            col = (Collective)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return col;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Concert si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Concert.
     */
    public static Concert parseConcert(String str) throws XMLParsingException{
        
        Concert c = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("concert", Concert.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("location", Location.class);
        try{
            c = (Concert)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return c;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Concert si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Concert.
     */
    public static Concert parseConcert(File f) throws XMLParsingException{
        
        Concert c = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("concert", Concert.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("location", Location.class);
        try{
            c = (Concert)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return c;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Exhibition si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Exhibition.
     */
    public static Exhibition parseExhibition(String str) throws XMLParsingException{
        
        Exhibition exh = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("location", Location.class);
        try{
            exh = (Exhibition)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return exh;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Exhibition si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Exhibition.
     */
    public static Exhibition parseExhibition(File f) throws XMLParsingException{
        
        Exhibition exh = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("location", Location.class);
        
        try{
            exh = (Exhibition)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return exh;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto FechaCompleta si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto FechaCompleta.
     */
    public static FechaCompleta parseFechaCompleta(String str) throws XMLParsingException{
        
        FechaCompleta fecha = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("fechacompleta", FechaCompleta.class);        
        try{
            fecha = (FechaCompleta)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return fecha;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto FechaCompleta si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto FechaCompleta.
     */
    public static FechaCompleta parseFechaCompleta(File f) throws XMLParsingException{
        
        FechaCompleta fecha = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("fechacompleta", FechaCompleta.class);       
        try{
            fecha = (FechaCompleta)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return fecha;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Festival si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Festival.
     */
    public static Festival parseFestival(String str) throws XMLParsingException{
        
        Festival fest = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("festival", Festival.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("location", Location.class);
        try{
            fest = (Festival)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return fest;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Festival si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Festival.
     */
    public static Festival parseFestival(File f) throws XMLParsingException{
        
        Festival fest = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("festival", Festival.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("location", Location.class);
        try{
            fest = (Festival)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return fest;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Location si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Location.
     */
    public static Location parseLocation(String str) throws XMLParsingException{
        
        Location l = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("location", Location.class);
        try{
            l = (Location)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return l;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Location si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Location.
     */
    public static Location parseLocation(File f) throws XMLParsingException{
        
        Location l = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("location", Location.class);
        try{
            l = (Location)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return l;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Sales si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Sales.
     */
    public static Sales parseSales(String str) throws XMLParsingException{
        
        Sales s = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("sales", Sales.class);
        xStream.alias("ticket", Ticket.class);
        xStream.alias("client", Client.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("exhibition", Exhibition.class);
        try{
            s = (Sales)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return s;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Sales si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Sales.
     */
    public static Sales parseSales(File f) throws XMLParsingException{
        
        Sales s = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("sales", Sales.class);
        xStream.alias("ticket", Ticket.class);
        xStream.alias("client", Client.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("exhibition", Exhibition.class);
        try{
            s = (Sales)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return s;
        
    }
    
    /**
     * Parsea un string que contenga codigo XML y lo convierte
     * a un objeto Ticket si dicho código está bien escrito
     * @param str string con código XML
     * @return Un objeto Ticket.
     */
    public static Ticket parseTicket(String str) throws XMLParsingException{
        
        Ticket t = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("ticket", Ticket.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("location", Location.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        try{
            t = (Ticket)xStream.fromXML(str);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage());
        }
        return t;
        
    }
    
    /**
     * Parsea un archivo que contenga codigo XML y lo convierte
     * a un objeto Ticket si dicho código está bien escrito
     * @param f fichero con código XML
     * @return Un objeto Ticket.
     */
    public static Ticket parseTicket(File f) throws XMLParsingException{
        
        Ticket t = null;
        // Creo el objeto xStream por el cual convertire el string en una
        // objeto de java
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("ticket", Ticket.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("exhibition", Exhibition.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("location", Location.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("collective", Collective.class);
        try{
            t = (Ticket)xStream.fromXML(f);
        }
        catch (XStreamException e){            
            throw new XMLParsingException(e.getMessage(), f.getName());
        }
        return t;
        
    }
    
}
