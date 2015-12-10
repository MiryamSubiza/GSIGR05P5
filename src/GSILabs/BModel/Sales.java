/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BModel;

import GSILabs.Serializable.XMLRepresentable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Registro de la venta de una entrada a un cliente
 * @author subiza.79082
 * @author izu.78236
 * @version 03/10/2015
 */
public class Sales implements XMLRepresentable, Serializable {
    
    private Ticket ticket;
    private Client client;
    private float price;
    private String cCard;
    private FechaCompleta dateSale;
    
    /**
     * Método constructor, inicialización de variables
     * @param ticket Entrada que se ha comprado
     * @param client Cliente que ha realizado la compra
     * @param price Precio de la entrada que se ha comprado
     * @param cCard Tarjeta de crédito con la que se ha realizado la compra
     * @param dateSale Momento en el que la venta se ha producido
     */
    public Sales (Ticket ticket, Client client, float price, String cCard, FechaCompleta dateSale) {
        
        this.ticket = ticket;
        this.client = client;
        this.price = price;
        this.cCard = cCard;
        this.dateSale = dateSale;
        
    }
    
    /**
     * Establecer cliente
     * @param client Cliente comprador de la entrada
     */
    public void setClient (Client client) {
        this.client = client;
    }
    
    /**
     * Obtener cliente
     * @return Cliente comprador de la entrada
     */
    public Client getClient () {
        return this.client;
    }
    
    /**
     * Establecer entrada
     * @param ticket Entrada comprada
     */
    public void setTicket (Ticket ticket) {
        this.ticket = ticket;
    }
    
    /**
     * Obtener entrada
     * @return Entrada comprada
     */
    public Ticket getTicket () {
        return this.ticket;
    }
    
    /**
     * Establecer precio de la entrada
     * @param price Precio de la entrada
     */
    public void setPrice (float price) {
        this.price = price;
    }
    
    /**
     * Obtener precio de la entrada
     * @return Precio de la entrada
     */
    public float getPrice () {
        return this.price;
    }
    
    /**
     * Establecer tarjeta de crédito
     * @param cCard Número de la tarjeta de crédito con la que se ha realizado la compra
     */
    public void setCCard (String cCard) {
        this.cCard = cCard;
    }
    
    /**
     * Obtener tarjeta de crédito
     * @return Número de la tarjeta de crédito
     */
    public String getCCard () {
        return this.cCard;
    }
    
    /**
     * Establecer fecha de venta
     * @param dateSale Fecha de venta
     */
    public void setDateSale (FechaCompleta dateSale) {
        this.dateSale = dateSale;
    }
    
    /**
     * Obtener fecha de venta
     * @return Fecha de venta
     */
    public FechaCompleta getDateSale () {
        return this.dateSale;
    }
    
    /**
     * Comparación entre dos objetos Sales
     * @param o Objeto a comparar
     * @return True si contienen el mismo ticket y el mismo cliente. 
     *  False en caso contrario.
     */
    @Override
    public boolean equals (Object o) {
        
        if (o instanceof Sales) {
            Sales s = (Sales)o;
            return ((this.getClient().equals(s.getClient())) && (this.getTicket().equals(s.getTicket())));
        }
        else return false;
        
    }
    
    /**
     * Representación por pantalla
     * @return Información a mostrar
     */
    @Override
    public String toString() {
        return "SALE\nClient: " + client.getName() + " " + client.getLastName() + 
                "\nPrice of the ticket: " + price + "€\nCredit card: " +
                cCard + "\nDate: " + dateSale.fechaToString() + "\nHour: " +
                dateSale.horaToString() + "\n";
    }
    
    @Override
    public String toXML() {
        // Creo el objeto xStream por el cual convertire la clase en un
        // datos en XML
        XStream xStream = new XStream(new DomDriver());
        // Cambio el alias de la clase en XML
        xStream.alias("sales", Sales.class);
        xStream.alias("ticket", Ticket.class);
        xStream.alias("client", Client.class);
        xStream.alias("fechacompleta", FechaCompleta.class);
        xStream.alias("artist", Artist.class);
        xStream.alias("concert", Concert.class);
        xStream.alias("festival", Festival.class);
        xStream.alias("exhibition", Exhibition.class);
        String xml = xStream.toXML(this);        
        return xml;
    }

    @Override
    public boolean saveToXML(File f) {
        boolean respuesta = false;
        XStream xStream = new XStream(new DomDriver());
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(f);
            pw = new PrintWriter(fichero);
            pw.println(toXML());            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                    respuesta = true;
                }
                else {
                    respuesta = false;
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return respuesta;
    }

    @Override
    public boolean saveToXML(String filePath) {
        //Creación del fichero en la ruta especificada
        File f = new File(filePath);
        return this.saveToXML(f);
    }
}