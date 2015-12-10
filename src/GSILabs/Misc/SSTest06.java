package GSILabs.Misc;

import GSILabs.BModel.Artist;
import GSILabs.BModel.Concert;
import GSILabs.BModel.FechaCompleta;
import GSILabs.BModel.Location;
import GSILabs.BSystem.BussinessSystem;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Alex
 */
public class SSTest06 {
              
    private static BussinessSystem bSystem;
    
    public static void main(String[] args) throws IOException{
        
        // En este test pongo a prueba el método importFestivals, de manera que le paso
        // una hoja de calculo con festivales y este los introduce correctamente al sistema
        
        final File file = new File("festivales.ods");
        bSystem = new BussinessSystem();
        
        Location l = new Location("Palacio Mestalla",10000,"Valencia");
        Artist a = new Artist("ACDC", "Best Rock Band Ever", "www.acdc.com");
        bSystem.addLocation(l);
        bSystem.addArtist(a);
        Concert c = new Concert("ACDC en VAL 2015", a, new FechaCompleta("02/02/2015","22:00"), new FechaCompleta("02/02/2015","22:00"),
                    new FechaCompleta("02/02/2015","21:00"), new FechaCompleta("03/02/2015","00:00"), l);
        bSystem.addNewConcert(c);
        
        int festivalsOk = bSystem.importFestivals(file);
        System.out.println("El numero de festivales introducidos: "+ festivalsOk);
        
    }
        
}   