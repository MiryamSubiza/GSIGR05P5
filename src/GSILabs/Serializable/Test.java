/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.Serializable;

/**
 *
 * @author Alex
 */

import GSILabs.persistence.XMLParser;
import GSILabs.BModel.*;
import GSILabs.persistence.XMLParsingException;
import java.io.File;


public class Test {
    
    private static Artist a[] = new Artist[2];
    private static Collective col;
    private static Location l3;
    private static Concert con;
    private static Exhibition ex;
    private static Festival fest;
    private static Client cli;
    private static Ticket t;
    private static Sales s;
    
    
    public static void main(String[] args) throws XMLParsingException{

        a[0] = new Artist("Nach", "Rapero alicantino", "www.nach.es");
        a[1] = new Artist("Thor Papito", "Master BOSS del Karaoke");
        col = new Collective(a[0], "Violadores del verso", "Grupo de RAP", "violadoresdelverso.org");
        col.addArtistToCollective(a[1]);
        l3 = new Location("BEC", 18000, "Bilbao", "bilbaoexhibitioncentre.com");
        con = new Concert("Concierto uno", a[1], new FechaCompleta("01/02/2016", "22:00"),
            new FechaCompleta("01/02/2016", "22:00"), new FechaCompleta("01/02/2016", "21:00"),
            new FechaCompleta("01/02/2016", "23:45"), l3);        
        ex = new Exhibition("Exposición dos", "Exposición de Nach", "Universal music",
            new FechaCompleta("21/08/2016", "17:30"), new FechaCompleta("28/08/2016", "20:00"),
            new FechaCompleta("21/08/2016", "17:30"), new FechaCompleta("28/08/2016", "20:00"),
            a[0], "www.universalmusic.es", l3);
        fest = new Festival("Festival uno", con, new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("15/11/2015", "23:50"), new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("15/11/2015", "23:50"));
        cli = new Client(73115003, "Alex", "Izu", new FechaCompleta("25/01/1994", "04:00"), "0080909808");
        t = new Ticket(ex,1,4);
        s = new Sales(t,cli,15.0f,"00838u39", new FechaCompleta("14/11/2015", "20:00"));
        
        // Comienzo con las pruebas de los ejercicio 1, 3 y 4 de la practica 3
        pruebaP3_01();
        pruebaP3_03();
        pruebaP3_04();
        
    }
    
    public static void pruebaP3_01() {
        System.out.println("\nEJERCICO 1\n");
        System.out.println("Prueba del ejercicio 1 de la práctica 3:");
        System.out.println(col.toXML());
        File f = new File("collective.xml");
        col.saveToXML(f);
    }
    
    public static void pruebaP3_03(){
        
        System.out.println("\nEJERCICO 3\n");
        System.out.println("Esto es una prueba del ejercico 3 de la practica 3:");
        File f = new File("festivals.xml");
        fest.saveToXML(f);
        Festival aux1 = null;
        Festival aux2 = null;
        // Pruebo con los dos metodos de parseo
        try{
            aux1 = XMLParser.parseFestival(f);
        }
        catch (XMLParsingException e){
            System.out.println("Se ha capturado una XMLParsingException:");
            if(e.getFileName() != null){
                System.out.println(e.getFileNameDescription());
            }
            System.out.println(e.getMessage());            
        }
        try{
            aux2 = XMLParser.parseFestival(fest.toXML());
        }
        catch (XMLParsingException e){
            System.out.println("Se ha capturado una XMLParsingException:");
            if(e.getFileName() != null){
                System.out.println(e.getFileNameDescription());
            }
            System.out.println(e.getMessage());            
        } 
        System.out.println("Objeto devuelto del parseo de XML desde fichero:");
        System.out.println(aux1.toString());
        System.out.println("Objeto devuelto del parseo de XML desde un string:");
        System.out.println(aux2.toString());
        
    }
    
    public static void pruebaP3_04(){
        System.out.println("\nEJERCICO 4\n");
        System.out.println("Esto es una prueba del ejercicio 4 de la practica 3");
        // El fichero artist.xml esta mal hecho a posta para que salte la excepcion
        File fichero = new File("artist.xml");
        Artist aux = null;        
        try{
            aux = XMLParser.parseArtist(fichero);
        }
        catch (XMLParsingException e){
            System.out.println("Se ha capturado una XMLParsingException:");
            if(e.getFileName() != null){
                System.out.println(e.getFileNameDescription());
            }
            System.out.println(e.getMessage());            
        }
    }
    
}
