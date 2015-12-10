package GSILabs.connect;

import GSILabs.BModel.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 * Clase ejecutable capaz de conectarse a un servidor.
 * @author subiza.79082
 * @author izu.78236
 * @version 25/11/2015
 */
public class CLClient {
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        // Con esta variable quiero poder comprobar cuando la ejecución del cliente
        // ha finalizado correctamente de modo que pueda terminar la ejecución del
        // programa
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
            // Paso 2 - Leer por teclado el puerto remoto de la máquina  
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
                objectTag = "CLGateway";
            }

            try {

                // Paso 4 -  Conectarse al registro remoto
                Registry registry = LocateRegistry.getRegistry(remoteMachine, remotePort);
                // Paso 5 - Unir el objeto remoto como si fuera un objeto local
                ClientGateway cGateway = (ClientGateway) registry.lookup(objectTag);
                // Paso 6 - Usar el objeto
                Client cli = cGateway.getClient(11111111);
                System.out.println("Mostramos el cliente con id 11111111:\n" + cli);

                Event e = cGateway.getEvent("Concierto uno");
                System.out.println("Obtengo mediante el método getEvent el evento con nombre: 'Concierto uno'\n" + e);

                Event[] events = cGateway.getEvents("onci");
                System.out.println("Obtengo mediante el método getEvents los eventos cuyos nombres contengan (parcialmente): 'onci'\n" + Arrays.toString(events));

                Ticket t = new Ticket((Concert)e, 100, 3);
                Sales s = cGateway.addFreeSale(cli, t);
                System.out.println("Verificamos que no se puede añadir una Sale a coste cero para un Ticket inexistente:\n" + s);
                // Pongo terminado a true ya que habremos terminado la ejecución
                terminado = true;

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
                else if(!objectTag.equalsIgnoreCase("CLGateway")){
                    // La etiqueta del objeto al que llama no existe en el servidor
                    System.out.println("La etiqueta del objeto que solicita no existe. Vuelva a intentarlo");
                }
                else{
                    System.out.println("La maquina a la que hace referencia no existe. Vuelva a intentarlo");
                }

            }
            
        }            
        
    }
    
}