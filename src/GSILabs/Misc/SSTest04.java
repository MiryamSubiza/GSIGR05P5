/*
 * Gestion de Sistemas de Informacion
 * Universidad Publica de Navarra
 * First semester of the Academic Year 2015-2016
 */

package GSILabs.Misc;

import GSILabs.BModel.*;
import GSILabs.BSystem.BussinessSystem;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Almacenar instancias de las clases que implementen Event en un fichero ODS.
 * @author subiza.79082
 * @author izu.78236
 * @version 18/10/2015
 */

public class SSTest04 {
    
    private static BussinessSystem bSystem;    
    private static Artist[] artists = new Artist[3];    
    private static Location[] locations = new Location[7];
    private static Concert[] concerts = new Concert[5];
    private static Exhibition[] exhibitions = new Exhibition[4];
    private static Festival festival;
    
    public static void main(String[] args) throws IOException {
        
        bSystem = new BussinessSystem();
        
        // Creo las instancias de los artistas
        artists[0] = new Artist("Bob Dylan", "Músico estadounidense", "www.bobdylan.com/es");
        artists[1] = new Artist("Alex Turner", "Cantante del grupo Arctic Monkeys");
        artists[2] = new Artist("Michael Jackson", "Rey del pop", "www.MJ.com");
        
        for (int i = 0; i < artists.length; i++) {
            bSystem.addArtist(artists[i]);
        }
        
        // Creo las instacias de las localizaciones
        locations[0] = new Location("Palau Sant Jordi", 24000, "Barcelona", "www.palausantjordi.cat/es");
        locations[1] = new Location("Madrid Arena", 12000, "Madrid");
        locations[2] = new Location("Estadio de Mestalla", 55000, "Valencia");
        locations[3] = new Location("TATE London", 35000, "Londres");
        locations[4] = new Location("MOMA NY", 15000, "New York");
        locations[5] = new Location("Burj-al-Arab Dubai", 5000, "Dubai");
        locations[6] = new Location("Museo de Arte Reina Sofia", 21000, "Madrid", "www.museoreinasofia.es");
        
        for (int i = 0; i < locations.length; i++) {
            bSystem.addLocation(locations[i]);
        }
        
        // Creo las instacias de los conciertos
        concerts[0] = new Concert("Bdylan Tour 15 BCN", artists[0], new FechaCompleta("01/02/2016", "22:00"),
            new FechaCompleta("01/02/2016", "22:00"), new FechaCompleta("01/02/2016", "21:00"),
            new FechaCompleta("01/02/2016", "23:45"), locations[0]);
        concerts[1] = new Concert("Bdylan Tour 15 MAD", artists[0], new FechaCompleta("14/11/2015", "20:30"),
            new FechaCompleta("14/11/2015", "20:30"), new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("14/11/2015", "23:30"), locations[1]);
        concerts[2] = new Concert("Bdylan Tour 15 VAL", artists[0], new FechaCompleta("02/02/2016", "21:00"),
            new FechaCompleta("02/02/2016", "21:00"), new FechaCompleta("02/02/2016", "20:30"),
            new FechaCompleta("02/02/2016", "23:00"), locations[2]);
        concerts[3] = new Concert("MJ Tour 16 BCN", artists[2], new FechaCompleta("02/06/2016", "21:00"),
            new FechaCompleta("02/06/2016", "21:00"), new FechaCompleta("02/06/2016", "20:30"),
            new FechaCompleta("02/06/2016", "23:00"), locations[0]);
        concerts[4] = new Concert("MJ Tour 16 MAD", artists[2], new FechaCompleta("07/07/2016", "21:00"),
            new FechaCompleta("07/07/2016", "21:00"), new FechaCompleta("07/07/2016", "20:30"),
            new FechaCompleta("07/07/2016", "23:00"), locations[1]);
        
        for (int i = 0; i < concerts.length; i++) {
            bSystem.addNewConcert(concerts[i]);
        }
        
        // Creo las instancias de las exposiciones
        exhibitions[0] = new Exhibition("Dylan Thomas", "Dylan Thomas by Dylan", "Bob Dylan",
            new FechaCompleta("01/05/2012", "15:30"), new FechaCompleta("01/11/2012", "20:30"),
            new FechaCompleta("01/05/2012", "15:30"), new FechaCompleta("01/11/2012", "20:30"),
            artists[0], "www.rollingstone.com", locations[3]);
        exhibitions[0].addWebLink("www.elpais.com");
        
        exhibitions[1] = new Exhibition("Cubist", "Make it cubist", "Bob Dylan",
            new FechaCompleta("03/01/2015", "16:00"), new FechaCompleta("06/04/2015", "20:00"),
            new FechaCompleta("03/01/2015", "16:00"), new FechaCompleta("06/04/2015", "20:00"),
            artists[0], "www.nymag.com/dylan", locations[4]);
        
        exhibitions[2] = new Exhibition("Landscapes", "Nighly landscapes", "Bob Dylan",
            new FechaCompleta("18/08/2012", "17:30"), new FechaCompleta("31/12/2015", "21:00"),
            new FechaCompleta("18/08/2012", "17:30"), new FechaCompleta("31/12/2015", "21:00"),
            artists[0], "www.nymag.com/agenda2012/exh", locations[5]);
        
        exhibitions[3] = new Exhibition("Duluth", "Duluth Unknown", "Bob Dylan",
            new FechaCompleta("05/04/2016", "16:45"), new FechaCompleta("08/06/2016", "19:45"),
            new FechaCompleta("05/04/2016", "16:45"), new FechaCompleta("08/06/2016", "19:45"),
            artists[0], "", locations[6]);
                
        for (int i = 0; i < exhibitions.length; i++) {
            bSystem.addNewExhibition(exhibitions[i]);
        }
        
        // Creo la instancia de los festivales
        festival = new Festival("MJ Experiencie", concerts[3], new FechaCompleta("02/06/2016","20:30"),
            new FechaCompleta("07/07/2016","23:00"), new FechaCompleta("02/06/2016","20:30"),
            new FechaCompleta("07/07/2016","23:00"));
        
        festival.addConcert(concerts[4]);
        bSystem.addNewFestival(festival);
                       
        // Creo la tabla donde voy a almacenar las instacias de los conciertos
        DefaultTableModel table = new DefaultTableModel(3,7);
        final File file = new File("test03.ods");     
        SpreadSheet spreadSheet = null;
        
        try{
            spreadSheet = SpreadSheet.createEmpty(table);
            spreadSheet.saveAs(file);
        }
        catch (IOException e){
            System.out.println("An error with the IO system appeared");
        }
        
        // Creo las hojas de Concierto, Festival y Exposición de mi hoja de cálculo
        spreadSheet.getFirstSheet().setName("Concert");
        
        // Configuro la hoja de festivales para que pueda almacenarlos
        spreadSheet.addSheet(1,"Exhibition");
        spreadSheet.getSheet(1).setRowCount(4);
        spreadSheet.getSheet(1).setColumnCount(10);
        
        // Configuro la hoja de exposiciones para que pueda almacenarlos
        spreadSheet.addSheet(2,"Festival");
        spreadSheet.getSheet(2).setRowCount(4);
        spreadSheet.getSheet(2).setColumnCount(10);
        
        // Relleno las hojas de mi hoja de cálculo        
        /* Almacenar en el fichero test03.ods una matriz strings con la información
        * sacada de los conciertos de los cuales hemos hecho una instancia
        */
        Iterator i = bSystem.getConcerts().values().iterator();
        Concert concertAux;
        int j = 0;
        while (i.hasNext()) {
            concertAux = (Concert)i.next();
            if (concertAux.getPerformer().getName().equals(artists[0].getName())) {
                spreadSheet.getSheet(0).setValueAt(concertAux.getName(), 0, j);
                spreadSheet.getSheet(0).setValueAt(concertAux.getPerformer().getName(), 1, j);
                spreadSheet.getSheet(0).setValueAt(((FechaCompleta)concertAux.getStartDate()).fechaToString(), 2, j);
                spreadSheet.getSheet(0).setValueAt(((FechaCompleta)concertAux.getStartTimeConcert()).horaToString(), 3, j);
                spreadSheet.getSheet(0).setValueAt(((FechaCompleta)concertAux.getDoorOpeningTimeConcert()).horaToString(), 4, j);
                spreadSheet.getSheet(0).setValueAt(((FechaCompleta)concertAux.getClosingTimeConcert()).horaToString(), 5, j);
                spreadSheet.getSheet(0).setValueAt(concertAux.getLocation().getName(), 6, j);
                j++;
            }
        }
        
        // Almacenamiento de datos en la hoja Exhibition
        i = bSystem.getExhibitions().values().iterator();
        Exhibition exhibitionAux;
        j = 0;
        while (i.hasNext()) {
            exhibitionAux = (Exhibition)i.next();
            spreadSheet.getSheet(1).setValueAt(exhibitionAux.getTitle(), 0, j);
            spreadSheet.getSheet(1).setValueAt(exhibitionAux.getPerformer().getName(), 1, j);
            spreadSheet.getSheet(1).setValueAt(exhibitionAux.getLocation().getName(), 2, j);
            spreadSheet.getSheet(1).setValueAt(((FechaCompleta)exhibitionAux.getStartDate()).fechaToString(), 3, j);
            spreadSheet.getSheet(1).setValueAt(((FechaCompleta)exhibitionAux.getStartTimeExhibition()).horaToString(), 4, j);
            spreadSheet.getSheet(1).setValueAt(((FechaCompleta)exhibitionAux.getEndingDate()).fechaToString(), 5, j);
            spreadSheet.getSheet(1).setValueAt(((FechaCompleta)exhibitionAux.getClosingTimeExhibition()).horaToString(), 6, j);
            String[] webLinks;
            webLinks = (String[])(exhibitionAux.getWebLinks().toArray(new String[exhibitionAux.getWebLinks().size()]));
            for (int k=0; k<webLinks.length; k++) {
                spreadSheet.getSheet(1).setValueAt(webLinks[k], 7+k, j);
            }
            j++;
        }
        
        // Almaceno la información de festivales en su hoja correspondiente
        i = bSystem.getFestivals().values().iterator();
        Festival festivalAux;
        j = 0;
        int k = 1;
        while (i.hasNext()) {
            festivalAux = (Festival)i.next();
            spreadSheet.getSheet(2).setValueAt(festival.getName(), 0, j);
            Iterator c = festival.getConcerts().iterator();
            Concert cAux;
            while (c.hasNext()) {
                cAux = (Concert)c.next();
                spreadSheet.getSheet(2).setValueAt(cAux.getName(), 1, k);
                spreadSheet.getSheet(2).setValueAt(cAux.getPerformer().getName(), 2, k);
                spreadSheet.getSheet(2).setValueAt(((FechaCompleta)cAux.getStartDate()).fechaToString(), 3, k);
                spreadSheet.getSheet(2).setValueAt(((FechaCompleta)cAux.getStartTimeConcert()).horaToString(), 4, k);
                spreadSheet.getSheet(2).setValueAt(((FechaCompleta)cAux.getDoorOpeningTimeConcert()).horaToString(), 5, k);
                spreadSheet.getSheet(2).setValueAt(((FechaCompleta)cAux.getClosingTimeConcert()).horaToString(), 6, k);
                spreadSheet.getSheet(2).setValueAt(cAux.getLocation().getName(), 7, k);
                k++;
            }
            spreadSheet.getSheet(2).setValueAt(((FechaCompleta)festival.getStartDate()).fechaToString(), 1, j);
            spreadSheet.getSheet(2).setValueAt(((FechaCompleta)festival.getStartTimeFestival()).horaToString(), 2, j);
            spreadSheet.getSheet(2).setValueAt(((FechaCompleta)festival.getEndingDate()).fechaToString(), 3, j);
            spreadSheet.getSheet(2).setValueAt(((FechaCompleta)festival.getClosingTimeFestival()).horaToString(), 4, j);
            k++;
        } 
        
        //Guardar la nueva tabla en el fichero file (test03.ods) y abrirlo
        OOUtils.open(spreadSheet.saveAs(file));

    }
}
