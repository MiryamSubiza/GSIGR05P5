/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.Misc;

import GSILabs.BModel.*;
import GSILabs.BSystem.BussinessSystem;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Alex
 */
public class SSTest05 {        
    
    private static BussinessSystem bSystem;
    
    public static void main(String[] args) throws IOException{
        
        // En este test pongo a prueba el método importTickets de modo que
        // a través de una hoja de cálculo que contiene tickets los añado
        // al sistema
        final File file = new File("P05Ej02.ods");        
        bSystem = new BussinessSystem();
        
        Location l = new Location("Palacio Mestalla",10000,"Valencia");
        Artist a = new Artist("ACDC", "Best Rock Band Ever", "www.acdc.com");
        bSystem.addLocation(l);
        bSystem.addArtist(a);
        Concert c = new Concert("ACDC en VAL 2015", a, new FechaCompleta("02/02/2015","22:00"), new FechaCompleta("02/02/2015","22:00"),
                    new FechaCompleta("02/02/2015","21:00"), new FechaCompleta("03/02/2015","00:00"), l);
        bSystem.addNewConcert(c);
        
        int ticketsOk = bSystem.importTickets(file);
        System.out.println("El numero de tickets introducidos: "+ ticketsOk);
        
    }
        
}
