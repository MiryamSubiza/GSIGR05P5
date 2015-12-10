package GSILabs.connect;

import GSILabs.BModel.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Clase ejecutable capaz de conectarse a un servidor.
 * @author subiza.79082
 * @author izu.78236
 * @version 25/11/2015
 */
public class EVClient {
    
    public static void main(String[] args) throws IOException, RemoteException, NotBoundException {
        
        boolean terminado = false;
        while(!terminado){
            // Paso 1- Leer por teclado la dirección remota de la máquina        
            System.out.print("Por favor introduzca la dirección de la máquina: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String remoteMachine;
            try {
                remoteMachine = br.readLine();
            } catch (IOException ioe) {
                System.out.println("Exception when reading : "+ioe.getMessage());
                remoteMachine="localhost";
            }
            // Paso 2 - Leer por teclado la puerto remota de la máquina  
            System.out.print("Por favor introduzca el puerto de la máquina: ");
            int remotePort;
            try {
                remotePort = Integer.parseInt(br.readLine());
            } catch (IOException ioe) {
                System.out.println("Exception when reading : "+ioe.getMessage());
                remotePort = 1099;
            }
            // Paso 3 - Leer por teclado la etiqueta del objeto remoto a contactar  
            System.out.print("Por favor introduzca la etiqueta del objeto remoto: ");
            String objectTag;
            try {
                objectTag = br.readLine();
            } catch (IOException ioe) {
                System.out.println("Exception when reading : "+ioe.getMessage());
                objectTag = "EVGateway";
            }

            try {
                // Paso 4 -  Conectarse al registro remoto
                Registry registry = LocateRegistry.getRegistry(remoteMachine, remotePort);
                // Paso 5 - Unir el objeto remoto como si fuera un objeto local
                EventGateway eGateway = (EventGateway) registry.lookup(objectTag);
                // Paso 6 - Usar el objeto
                boolean fin  = false;
                while(!fin){
                    System.out.println("\nEVENT CLIENT MENU");
                    System.out.println("   1- Ejemplo de todos los métodos del a interfaz");                
                    System.out.println("   2- Remove event");
                    System.out.println("   3- Update event");
                    System.out.println("   4- Salir");
                    System.out.print("Elija una de las siguientes opciones: ");
                    int respuesta;
                    respuesta = Integer.parseInt(br.readLine());
                    switch(respuesta){
                        case 1:
                            pruebasMetodosInterfaz(eGateway);
                            break;

                        case 2:
                            System.out.println("\nElimino el concierto de nombre 'Concierto seis'");                        
                            if(eGateway.removeEvent(eGateway.getConcert("Concierto seis"))){
                                System.out.println("Ha sido eliminado correctamente\n");
                            }
                            else{
                                System.out.println("ERROR: No se ha podido eliminar correctamente\n");
                            }
                            break;

                        case 3:
                            System.out.println("\nActualizo el concierto de nombre 'Concierto seis' cambiandole la localizacion");
                            Artist art = new Artist("R de Rumba", "Rubén Cuevas, DJ zaragozano");
                            Location loc1 = new Location("Palacio de deportes", 15500, "Madrid");
                            // Este concierto es el mismo que el tres pero le cambio la localizacion
                            Concert conUpdated = new Concert("Concierto seis", art, new FechaCompleta("02/06/2016", "21:15"),
                            new FechaCompleta("02/06/2016", "21:15"), new FechaCompleta("02/06/2016", "20:15"),
                            new FechaCompleta("02/06/2016", "23:50"), loc1);
                            if(eGateway.updateEvent(conUpdated)){
                                System.out.println("Ha sido actualizado correctamente\n");
                            }
                            else{
                                System.out.println("ERROR: No se ha podido actualizar correctamente\n");
                            }
                            break;

                        case 4:
                            // De este modo saldremos del bucle while
                            fin = true;
                            terminado = true;
                            break;

                        default:
                            System.out.println("Opción incorrecta, vuelva a intentarlo.");
                            break;

                    }

                }
            } catch (RemoteException | NotBoundException ex) {
                System.out.println("Exception in connection : " + ex.getMessage());
                // Compruebo primero si la maquina no es accesible haciendole un ping
                InetAddress ping = InetAddress.getByName(remoteMachine);
                if(!ping.isReachable(5000)){
                    // La maquina no es accesible
                    System.out.println("El servidor no está disponible en estos momentos.\nEspere unos minutos y vuelva a intentarlo"); 
                }else if(remotePort != 1099){ 
                    // La maquina es accesible
                    System.out.println("El puerto que ha introducido es incorrecto.Vuelva a intentarlo");
                }
                else if(!objectTag.equalsIgnoreCase("EVGateway")){
                    // La etiqueta del objeto al que llama no existe en el servidor
                    System.out.println("La etiqueta del objeto que solicita no existe. Vuelva a intentarlo");
                }
                else{
                    System.out.println("La maquina a la que hace referencia no existe. Vuelva a intentarlo");
                }
            }
            
        }
        
    }
    
    private static void pruebasMetodosInterfaz(EventGateway ev) throws RemoteException{
        
        Concert c1 = ev.getConcert("Concierto uno");
        System.out.println("Obtengo mediante el método getConcert el concierto con nombre: 'Concierto uno'\n" + c1);

        Festival f = ev.getFestival("Festival uno");
        System.out.println("Obtengo mediante el método getFestival el festival con nombre: 'Festival uno'\n" + f);

        System.out.println("Elimino mediante el método removeConcert el concierto 'Concierto uno'\n" + ev.removeConcert(c1));

        System.out.println("Añado un concierto a un festival en el que ya se encuentra:\n" + ev.addConcertToFestival("Festival uno", ev.getConcert("Concierto dos")));

        System.out.println("Añado un concierto a un festival en el que no se encuentra:\n" + ev.addConcertToFestival("Festival uno", ev.getConcert("Concierto tres")));

        Concert c2 = new Concert("Concierto uno", c1.getPerformer(), new FechaCompleta("01/02/2020", "22:00"),
                    new FechaCompleta("01/02/2020", "22:00"), new FechaCompleta("01/02/2020", "21:00"),
                    new FechaCompleta("01/02/2020", "23:45"), c1.getLocation());
        System.out.println("Actualizo la fecha de 'Concierto uno' de 2016 a 2020\n" + ev.updateEvent(c2));

        System.out.println("Elimino mediante el método removeEvent el concierto 'Concierto tres'\n" + ev.removeEvent(ev.getConcert("Concierto tres")));
        
    }
    
}
