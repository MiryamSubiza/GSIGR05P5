/*
 * This document is part of the lab material for the subject:
 * Gestion de Sistemas de Informacion
 * to be released at the
 * Universidad Publica de Navarra
 * during the first semester of the Academic Year 2015-2016
 */

package GSILabs.BTesting;

import GSILabs.BModel.*;
import GSILabs.BSystem.*;
import GSILabs.persistence.XMLParsingException;
import java.io.File;
import java.util.Iterator;

/**
 * Introducción de datos para comprobar y mostrar por la línea de comandos que
 * se cumplen una serie de sucesos de acuerdo a los requisitos.
 * @author subiza.79082
 * @author izu.78236
 * @version 02/10/2015
 */
public class P01Tester {
    
    //Creación de una instancia de la clase GSILabs.BSystem.BusinessSystem
    private static PublicBussinessSystem bussinessSystem;
    
    private static Artist[] artists = new Artist[11];
    private static Collective[] collectives = new Collective[5];
    private static Location[] locations = new Location[7];
    private static Concert[] concerts = new Concert[7];
    private static Festival[] festivals = new Festival[2];
    private static Exhibition[] exhibitions = new Exhibition[3];
    private static Ticket[] tickets = new Ticket[12];
    private static Client[] clients = new Client[8];
    
    public static void main (String[] args) {
        
        System.out.println("Bienvenido/a a la clase Tester01 para la realización\nde pruebas sobre nuestra práctica 01\n");
        System.out.println("Creación de algunos datos con los que empezar el testeo...");
        bussinessSystem = introduccionDatos();
        mostrarDatos();
        System.out.println("Una vez creados, comienza el testeo:\n");
        comprobacionDatos();
        
    }
    
    //Introducir una cierta cantidad de eventos, clientes, entradas, etc.
    public static PublicBussinessSystem introduccionDatos () {
        
        bussinessSystem = new PublicBussinessSystem();
        
        //Artists
        artists[0] = new Artist("Andrés Suárez", "Cantautor gallego", "www.andressuarez.es");
        artists[1] = new Artist("Funambulista", "Cantautor murciano", "www.funambulista.es");
        artists[2] = new Artist("Rozalén", "Cantautora albaceteña");
        artists[3] = new Artist("Nach", "Rapero alicantino", "www.nach.es");
        artists[4] = new Artist("Kase-O", "Javier Ibarra, MC zaragozano");
        artists[5] = new Artist("Lírico", "David Gilaberte, MC zaragozano");
        artists[6] = new Artist("Sho-Hai", "Sergio Rodríguez, MC zaragozano");
        artists[7] = new Artist("R de Rumba", "Rubén Cuevas, DJ zaragozano");
        artists[8] = new Artist("Alex Papito", "Master BOSS del Karaoke");
        artists[9] = new Artist("Rasking", "Coros del famoso grupo Alex y los Rebujitos");

        for (Artist artist : artists) {
            bussinessSystem.addArtist(artist);
        }
        
        //Collectives
        collectives[0] = new Collective(artists[0], "Cantautores", "Conjunto de cantautores");
        collectives[1] = new Collective(artists[4], "Violadores del verso", "Grupo de RAP", "violadoresdelverso.org");
        collectives[2] = new Collective(artists[8], "Alex y los Rebujitos", "Grupo number ONE en el canto de Karaoke", "www.losrebujitoslapetan.com");
        
        collectives[0].addArtistToCollective(artists[1]);
        collectives[0].addArtistToCollective(artists[2]);
        collectives[1].addArtistToCollective(artists[5]);
        collectives[1].addArtistToCollective(artists[6]);
        collectives[1].addArtistToCollective(artists[7]);
        collectives[2].addArtistToCollective(artists[9]);
        
        for (int i = 0; i < 3; i++) {
            bussinessSystem.addCollective(collectives[i]);
        }
        
        //Locations
        locations[0] = new Location("Interpeñas", 25000, "Zaragoza", "www.interpeñas.es");
        locations[1] = new Location("Palacio de deportes", 15500, "Madrid");
        locations[2] = new Location("BEC", 18000, "Bilbao", "bilbaoexhibitioncentre.com");
        locations[3] = new Location("Palau Olimpic", 12500, "Barcelona");
        locations[4] = new Location("Sala Totem", 200, "Pamplona", "www.salatotem.com");
        locations[5] = new Location("Kursaal", 1800, "San Sebastián", "www.kursaal.eus/es");
        locations[6] = new Location("Carpa Universitaria", 10000, "Pamplona", "www.carpauniversitaria.com");
        
        for (Location location : locations) {
            bussinessSystem.addLocation(location);
        }
        
        //Concerts
        concerts[0] = new Concert("Concierto uno", collectives[0], new FechaCompleta("01/02/2016", "22:00"),
            new FechaCompleta("01/02/2016", "22:00"), new FechaCompleta("01/02/2016", "21:00"),
            new FechaCompleta("01/02/2016", "23:45"), locations[5]);
        concerts[1] = new Concert("Concierto dos", artists[3], new FechaCompleta("14/11/2015", "20:30"),
            new FechaCompleta("14/11/2015", "20:30"), new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("14/11/2015", "23:30"), locations[2]);
        concerts[2] = new Concert("Concierto tres", artists[4], new FechaCompleta("02/02/2016", "21:00"),
            new FechaCompleta("02/02/2016", "21:00"), new FechaCompleta("02/02/2016", "20:30"),
            new FechaCompleta("02/02/2016", "23:00"), locations[0]);
        concerts[3] = new Concert("Concierto cuatro", collectives[1], new FechaCompleta("15/11/2015", "21:15"),
            new FechaCompleta("15/11/2015", "21:15"), new FechaCompleta("15/11/2015", "20:15"),
            new FechaCompleta("15/11/2015", "23:50"), locations[1]);
        concerts[4] = new Concert("Concierto cinco", artists[6], new FechaCompleta("03/02/2016", "21:15"),
            new FechaCompleta("03/02/2016", "21:15"), new FechaCompleta("03/02/2016", "20:15"),
            new FechaCompleta("03/02/2016", "23:50"), locations[2]);
        concerts[5] = new Concert("Concierto seis", artists[7], new FechaCompleta("02/06/2016", "21:15"),
            new FechaCompleta("02/06/2016", "21:15"), new FechaCompleta("02/06/2016", "20:15"),
            new FechaCompleta("02/06/2016", "23:50"), locations[4]);
        concerts[6] = new Concert("We are Back Rebujitos", collectives[2], new FechaCompleta("06/11/2015", "12:00"),
            new FechaCompleta("06/11/2015", "12:00"), new FechaCompleta("06/11/2015", "10:00"),
            new FechaCompleta("06/11/2015", "14:00"), locations[6]);
        
        for (Concert concert : concerts) {
            bussinessSystem.addNewConcert(concert);
        }
        
        //Festivals
        festivals[0] = new Festival("Festival uno", concerts[1], new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("15/11/2015", "23:50"), new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("15/11/2015", "23:50"));
        festivals[1] = new Festival("Festival dos", concerts[0], new FechaCompleta("01/02/2016", "21:00"),
            new FechaCompleta("03/02/2016", "23:50"), new FechaCompleta("01/02/2016", "21:00"),
            new FechaCompleta("03/02/2016", "23:50"));
        
        festivals[0].addConcert(concerts[3]);
        festivals[1].addConcert(concerts[2]);
        festivals[1].addConcert(concerts[4]);
        
        for (Festival festival : festivals) {
            bussinessSystem.addNewFestival(festival);
        }
        
        //Exhibitions
        collectives[3] = new Collective(artists[3], "Raperos", "Varios MC's");
        collectives[3].addArtistToCollective(artists[4]);
        collectives[3].addArtistToCollective(artists[6]);
        bussinessSystem.addCollective(collectives[3]);
        exhibitions[0] = new Exhibition("Exposición uno", "Exposición de RAP", "Eterno miusik",
            new FechaCompleta("15/05/2016", "15:30"), new FechaCompleta("20/05/2016", "20:30"),
            new FechaCompleta("15/05/2016", "15:30"), new FechaCompleta("20/05/2016", "20:30"),
            collectives[3], "www.eternomiusik.org", locations[0]);
        exhibitions[1] = new Exhibition("Exposición dos", "Exposición de Nach", "Universal music",
            new FechaCompleta("21/08/2016", "17:30"), new FechaCompleta("28/08/2016", "20:00"),
            new FechaCompleta("21/08/2016", "17:30"), new FechaCompleta("28/08/2016", "20:00"),
            artists[3], "www.universalmusic.es", locations[5]);
        exhibitions[2] = new Exhibition("Exposición tres", "Exposición de Kase-O", "Rap solo",
            new FechaCompleta("26/09/2016", "16:45"), new FechaCompleta("26/09/2016", "21:00"),
            new FechaCompleta("26/09/2016", "16:45"), new FechaCompleta("26/09/2016", "21:00"),
            artists[4], "www.rapsolozgz.com", locations[2]);
        
        for (Exhibition exhibition : exhibitions) {
            bussinessSystem.addNewExhibition(exhibition);
        }
        
        //Tickets
        tickets[0] = new Ticket(concerts[0], bussinessSystem.getAtomicInteger(1), 1);
        tickets[1] = new Ticket(concerts[1], bussinessSystem.getAtomicInteger(2), 2);
        tickets[2] = new Ticket(concerts[2], bussinessSystem.getAtomicInteger(1), 1);
        tickets[3] = new Ticket(concerts[3], bussinessSystem.getAtomicInteger(1), 1);
        tickets[4] = new Ticket(concerts[4], bussinessSystem.getAtomicInteger(4), 4);
        tickets[5] = new Ticket(concerts[5], bussinessSystem.getAtomicInteger(1), 1);
        tickets[6] = new Ticket(concerts[6], bussinessSystem.getAtomicInteger(1), 1);
        tickets[7] = new Ticket(festivals[0], bussinessSystem.getAtomicInteger(1), 1);
        tickets[8] = new Ticket(festivals[1], bussinessSystem.getAtomicInteger(3), 3);
        tickets[9] = new Ticket(exhibitions[0], bussinessSystem.getAtomicInteger(1), 1);
        tickets[10] = new Ticket(exhibitions[1], bussinessSystem.getAtomicInteger(1), 1);
        tickets[11] = new Ticket(exhibitions[2], bussinessSystem.getAtomicInteger(1), 1);
        
        for (Ticket ticket : tickets) {
            bussinessSystem.addNewTicket(ticket);
        }
        
        //Clients
        clients[0] = new Client(11111111, "Alexandre", "Izu Carmona", new FechaCompleta("25/01/1994", "00:00"),
            "1111 1111 1111 1111");
        clients[1] = new Client(22222222, "Miryam", "Subiza Erro", new FechaCompleta("02/06/1994", "00:00"),
            "2222 2222 2222 2222");
        clients[2] = new Client(33333333, "Ana", "Larráyoz Jiménez", new FechaCompleta("09/07/1994", "00:00"),
            "3333 3333 3333 3333");
        clients[3] = new Client(44444444, "Diego", "Razquin Elcano", new FechaCompleta("24/08/1994", "00:00"),
            "4444 4444 4444 4444");
        clients[4] = new Client(55555555, "Christian", "Goñi Rebollo", new FechaCompleta("14/08/1989", "00:00"),
            "5555 5555 5555 5555");
        clients[5] = new Client(66666666, "Raquel", "Castillo Pérez", new FechaCompleta("26/12/1992", "00:00"),
            "6666 6666 6666 6666");
        clients[6] = new Client(77777777, "José Miguel", "Carrillo", new FechaCompleta("21/09/1993", "00:00"),
            "7777 7777 7777 7777");
        clients[7] = new Client(88888888, "Josu", "Goñi", new FechaCompleta("02/02/1991", "00:00"),
            "8888 8888 8888 8888");
        
        clients[0].addCreditCard("1000 1000 1000 1000");
        clients[1].addCreditCard("2000 2000 2000 2000");
        clients[1].addCreditCard("2111 2111 2111 2111");
        clients[6].addCreditCard("7000 7000 7000 7000");
        
        for (Client client : clients) {
            bussinessSystem.addClient(client);
        }
        
        //Sales        
        bussinessSystem.addSale(tickets[0], clients[0], (float)13.5, "1111 1111 1111 1111");
        bussinessSystem.addSale(tickets[1], clients[0], (float)27, "1000 1000 1000 1000");
        bussinessSystem.addSale(tickets[2], clients[1], (float)17, "2222 2222 2222 2222");
        bussinessSystem.addSale(tickets[3], clients[1], (float)19, "2000 2000 2000 2000");
        bussinessSystem.addSale(tickets[4], clients[1], (float)60.5, "2111 2111 2111 2111");
        bussinessSystem.addSale(tickets[5], clients[2], (float)20.5, "3333 3333 3333 3333");
        
        return bussinessSystem;
    }
    
    //Mostrar por pantalla los datos que tenemos actualmente en el sistema
    public static void mostrarDatos() {
        
        //Show data
        System.out.println("\n************* PRESENTACIÓN DE DATOS *************");
        
        //Artists
        Iterator i = bussinessSystem.getArtists().values().iterator();
        Artist artistAux;
        while (i.hasNext()) {
            artistAux = (Artist)i.next();
            System.out.println(artistAux.toString());
        }
        
        //Collectives
        i = bussinessSystem.getCollectives().values().iterator();
        Collective collectiveAux;
        while (i.hasNext()) {
            collectiveAux = (Collective)i.next();
            System.out.println(collectiveAux.toString());
        }
        
        //Locations
        i = bussinessSystem.getLocations().values().iterator();
        Location locationAux;
        while (i.hasNext()) {
            locationAux = (Location)i.next();
            System.out.println(locationAux.toString());
        }
        
        //Concerts
        i = bussinessSystem.getConcerts().values().iterator();
        Concert concertAux;
        while (i.hasNext()) {
            concertAux = (Concert)i.next();
            System.out.println(concertAux.toString());
        }
        
        //Festivals
        i = bussinessSystem.getFestivals().values().iterator();
        Festival festivalAux;
        while (i.hasNext()) {
            festivalAux = (Festival)i.next();
            System.out.println(festivalAux.toString());
        }
        
        //Exhibitions
        i = bussinessSystem.getExhibitions().values().iterator();
        Exhibition exhibitionAux;
        while (i.hasNext()) {
            exhibitionAux = (Exhibition)i.next();
            System.out.println(exhibitionAux.toString());
        }
        
        //Tickets
        i = bussinessSystem.getTickets().values().iterator();
        Ticket ticketAux;
        while (i.hasNext()) {
            ticketAux = (Ticket)i.next();
            System.out.println(ticketAux.toString());
        }
        
        //Clients
        i = bussinessSystem.getClients().values().iterator();
        Client clientAux;
        while (i.hasNext()) {
            clientAux = (Client)i.next();
            System.out.println(clientAux.toString());
        }
        
        //Sales
        i = bussinessSystem.getSales().iterator();
        Sales saleAux;
        while (i.hasNext()) {
            saleAux = (Sales)i.next();
            System.out.println(saleAux.toString());
        }

        System.out.println("***************************************************\n");
        
    }
    
    //Comprobar y mostrar por línea de comandos varios sucesos
    public static void comprobacionDatos () {
        
        //pruebaS1 ();
        //pruebaS2 ();
        //pruebaS3 ();
        //pruebaS4 ();
        //pruebaS5 ();
        //pruebaS6 ();
        //pruebaS7 ();
        //pruebaS8 ();
        //pruebaS9 ();
        //pruebaS10();
        System.out.println("PRUEBAS PARA ALGUNOS DE LOS EJERCICIOS DE LA PRACTICA 3");
        pruebaP3_02();
        pruebaP3_05();
        
    }
    
    public static void pruebaS1 () {
                
        // PRUEBA S1)
        System.out.println("PRUEBA S1)");
        System.out.println("Si introduce a un cliente, este puede ser luego localizado a partir de su ID\n");
        System.out.println("Búsqueda del cliente con ID 11111111");
        Client clientS1 = bussinessSystem.retrieveClient(clients[0].getId());
        
        System.out.println("Obtenemos el siguiente cliente:");
        System.out.println(clientS1);
        
    }
    
    public static void pruebaS2 () {
        
        // PRUEBA S2)
        System.out.println("\nPRUEBA S2)");
        System.out.println("Si busca a un cliente que no existe con findClient, el resultado es null\n");
        System.out.println("Búsqueda del cliente con ID 00000000");
        Client clientS2 = bussinessSystem.retrieveClient(00000000);
        
        System.out.println("Obtenemos el siguiente cliente:");
        System.out.println(clientS2);
        
    }
    
    public static void pruebaS3 () {
        
        // PRUEBA S3)
        System.out.println("\n\nPRUEBA S3)");
        System.out.println("No se pueden introducir un artista y un colectivo con el mismo nombre\n");
        System.out.println("Introducción del siguiente artista:");
        artists[10] = new Artist("Stardust", "Cantante de rock");
        System.out.println(artists[10]);
        
        if (bussinessSystem.addArtist(artists[10])) System.out.println("El artista ha sido añadido correctamente");
        else System.out.println("Artista no añadido, el nombre del artista ya existe en otro artista o colectivo");
        
        System.out.println("\nIntroducción del siguiente colectivo:");
        collectives[4] = new Collective(artists[10], "Stardust", "Cantante de rock");
        System.out.println(collectives[4]);
        if (bussinessSystem.addCollective(collectives[4])) System.out.println("El colectivo ha sido añadido correctamente");
        else System.out.println("Colectivo no añadido, el nombre del colectivo ya existe en otro artista o colectivo");
        
    }
        
    public static void pruebaS4 () {
        
        // PRUEBA S4)
        System.out.println("\n\nPRUEBA S4)");
        System.out.println("Si se añade un artista, y se elimina posteriormente, se puede introducir un colectivo con "
                + "el mismo nombre\n");
        System.out.println("Borrado del artista añadido en S3)");
        System.out.println("El artista a eliminar es:");
        System.out.println(artists[10]);
        
        if (bussinessSystem.removePerformer(artists[10].getName())) System.out.println("El artista ha sido eliminado correctamente\n");
        else System.out.println("El artista no ha sido eliminado\n");
        
        System.out.println("Introducción del siguiente colectivo:");        
        System.out.println(collectives[4]);
        
        if (bussinessSystem.addCollective(collectives[4])) System.out.println("El colectivo ha sido añadido correctamente");
        else System.out.println("El colectivo no ha sido añadido, el nombre del colectivo ya existe en otro artista o colectivo");
        
    }
    
    public static void pruebaS5 () {
    
        // PRUEBA S5)
        System.out.println("\n\nPRUEBA S5)");
        System.out.println("No se pueden añadir dos eventos diferentes del mismo artista el mismo día\n");
        System.out.println("Concierto existente:");
        System.out.println(concerts[1]);
        System.out.println("Introducción del siguiente concierto:");
        Concert concertS5 = new Concert("Hola hola", bussinessSystem.retrievePerformer("Nach"), new FechaCompleta("14/11/2015", "20:30"),
            new FechaCompleta("14/11/2015", "20:30"), new FechaCompleta("14/11/2015", "20:00"),
            new FechaCompleta("14/11/2015", "23:30"), bussinessSystem.getLocation("Carpa Universitaria"));
        System.out.println(concertS5);
        
        // Compruebo si el concierto se puede introducir o no
        if (bussinessSystem.addNewConcert(concertS5)) System.out.println("El concierto ha sido añadido correctamente");
        else System.out.println("El concierto no ha sido añadido, el performer (artista) actúa el mismo día en otro evento");
        
    }
    
    public static void pruebaS6 () {
        
        // PRUEBA S6)
        System.out.println("\n\nPRUEBA S6)");
        System.out.println("El sistema calcula de manera adecuada el gasto de cada cliente en entradas (probar con más de \n"
            + "dos entradas, así como con entradas asociadas a diferentes clientes)\n");
        System.out.println("Cálculo del gasto en entradas del cliente 22222222. Historial de entradas compradas:");
        Ticket[] historial;
        historial = bussinessSystem.getListOfTickets(clients[1]);
        for (int i = 0; i < historial.length; i++) {
            System.out.println(historial[i]);
        }
        System.out.println("El gasto del cliente 22222222 en entradas es de " + bussinessSystem.getTotalSpending(clients[1]) + "€");

    }
    
    public static void pruebaS7 () {
        
        // PRUEBA S7)
        System.out.println("\n\nPRUEBA S7)");
        System.out.println("No se puede asociar un evento a una localización que no existe\n");
        System.out.println("Introducción del siguiente concierto en una supuesta localización llamada 'S7'");
        Concert concertS7 = new Concert("Hola hola", bussinessSystem.retrievePerformer("Alex y los Rebujitos"), 
            new FechaCompleta("14/11/2015", "20:30"), new FechaCompleta("14/11/2015", "20:30"), 
            new FechaCompleta("14/11/2015", "20:00"), new FechaCompleta("14/11/2015", "23:30"), 
            new Location("S7", 100, "Navarra"));
        System.out.println(concertS7);
        
        // Compruebo si el concierto se puede introducir o no
        if (bussinessSystem.addNewConcert(concertS7)) System.out.println("El concierto ha sido añadido correctamente");
        else System.out.println("El concierto no ha sido añadido, la localización no existe en el sistema");
        
    }
    
    public static void pruebaS8 () {
        
        // PRUEBA S8)
        System.out.println("\n\nPRUEBA S8)");
        System.out.println("No se puede añadir a un festival un concierto que ya se le hubiera añadido\n");
        System.out.println("Introducción del siguiente concierto al siguiente festival:");
        System.out.println(concerts[4]);
        System.out.println(bussinessSystem.getEvent("Festival dos"));
        
        if (bussinessSystem.addConcertToFestival((Festival)bussinessSystem.getEvent("Festival dos"), concerts[4])) 
            System.out.println("El concierto ha sido añadido al festival");
        else System.out.println("El concierto no ha sido añadido al festival porque ya existe dentro del mismo");
        
    }
    
    public static void pruebaS9 () {
        
        // PRUEBA S9)
        System.out.println("\n\nPRUEBA S9)");
        System.out.println("No se puede asignar una venta a un cliente que no existe\n");
        System.out.println("Introducción de la siguiente venta a un supuesto cliente de ID 00000000:");
        Sales salesS9 = new Sales(tickets[9], new Client(00000000, "Perico", "De Andrés", new FechaCompleta("02/01/1990", "00:00"), 
            "1111 1111 1111 1111"), 10, "0000 0000 0000 0000", new FechaCompleta("03/10/2015", "14:00"));
        System.out.println(salesS9);
        if (bussinessSystem.addSale(tickets[9], new Client(00000000, "Perico", "De Andrés", new FechaCompleta("02/01/1990", "00:00"), 
            "1111 1111 1111 1111"), (float)10, "1111 1111 1111 1111")) 
            System.out.println("La venta ha sido asignada al cliente");
        else System.out.println("La venta no ha sido asignada, el cliente no existe");
        
    }
    
    public static void pruebaS10 () {
        
        // PRUEBA S10)
        System.out.println("\n\nPRUEBA S10)");
        System.out.println("No se puede introducir un usuario menor de edad\n");
        System.out.println("Introducción del siguiente supuesto:");
        Client clientS10 = new Client(12345678, "Maria", "De la o", new FechaCompleta("16/02/2000", "00:00"),
            "1234 1234 1234 1234");
        System.out.println(clientS10);
        if (bussinessSystem.addClient(clientS10)) System.out.println("El cliente ha sido introducido\n");
        else System.out.println("El cliente no ha sido introducido, es menor de edad\n");
        
    }        
    
    // PRUEBA P03Ej02
    public static void pruebaP3_02() {
        System.out.println("\nEJERCICO 2\n");
        System.out.println("Esto es una prueba del ejercicio 2 de la practica 3:");
        System.out.println(bussinessSystem.toXML());
        File fichero = new File("publicBussinessSystem.xml");
        bussinessSystem.saveToXML(fichero);
    }        
    
    public static void pruebaP3_05() {
        
        System.out.println("\nEJERCICO 5 PRIMER METODO\n");
        File f = new File("bussinessSystem.xml");
        BussinessSystem bs = new BussinessSystem();
        try {
            bs = BussinessSystem.parseXMLFile(f);
        }
        catch (XMLParsingException e) {
            System.out.println("Se ha capturado una XMLParsingException:");
            if(e.getFileName() != null){
                System.out.println(e.getFileNameDescription());
            }
            System.out.println(e.getMessage()); 
        }
        /*
        ** Para comprobar si se ha creado bien el objeto BusinessSystem
        
        //Locations
        Iterator i = bs.getLocations().values().iterator();
        Location locationAux;
        while (i.hasNext()) {
            locationAux = (Location)i.next();
            System.out.println(locationAux.toString());
        }
        //Artists
        i = bs.getArtists().values().iterator();
        Artist artistAux;
        while (i.hasNext()) {
            artistAux = (Artist)i.next();
            System.out.println(artistAux.toString());
        }
        //Collectives
        i = bs.getCollectives().values().iterator();
        Collective collectiveAux;
        while (i.hasNext()) {
            collectiveAux = (Collective)i.next();
            System.out.println(collectiveAux.toString());
        }
        //Concerts
        i = bs.getConcerts().values().iterator();
        Concert concertAux;
        while (i.hasNext()) {
            concertAux = (Concert)i.next();
            System.out.println(concertAux.toString());
        }
        //Exhibitions
        i = bs.getExhibitions().values().iterator();
        Exhibition exhibitionAux;
        while (i.hasNext()) {
            exhibitionAux = (Exhibition)i.next();
            System.out.println(exhibitionAux.toString());
        }
        //Festivals
        i = bs.getFestivals().values().iterator();
        Festival festivalAux;
        while (i.hasNext()) {
            festivalAux = (Festival)i.next();
            System.out.println(festivalAux.toString());
        }
        //Clients
        i = bs.getClients().values().iterator();
        Client clientAux;
        while (i.hasNext()) {
            clientAux = (Client)i.next();
            System.out.println(clientAux.toString());
        }
        //Tickets
        i = bs.getTickets().values().iterator();
        Ticket ticketAux;
        while (i.hasNext()) {
            ticketAux = (Ticket)i.next();
            System.out.println(ticketAux.toString());
        }
        //Sales
        i = bs.getSales().iterator();
        Sales saleAux;
        while (i.hasNext()) {
            saleAux = (Sales)i.next();
            System.out.println(saleAux.toString());
        }
        */
    }
    
    public static BussinessSystem getBussinessSystem () {
        introduccionDatos();
        return bussinessSystem;
    }
    
}
