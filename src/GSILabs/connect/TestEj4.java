/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.connect;

import GSILabs.BModel.Client;
import GSILabs.BModel.Concert;
import GSILabs.BModel.Event;
import GSILabs.BModel.Sales;
import GSILabs.BModel.Ticket;
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
 *
 * @author Alex
 */
public class TestEj4 {
    
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
                Client cli1 = cGateway.getClient(11111111);
                Client cli2 = cGateway.getClient(11111111);
                System.out.println("PRUEBA DEL APARTADO 1 DEL EJERCICIO 4");
                System.out.println("Los dos clientes son iguales:");
                System.out.println("CLIENTE 1");
                System.out.println(cli1);
                System.out.println("CLIENTE 2");
                System.out.println(cli2);
                System.out.println("Cambio el nombre de cli1 y vuelvo a imprimirlos");
                cli1.setName("PatoLucas");
                System.out.println("CLIENTE 1");
                System.out.println(cli1);
                System.out.println("CLIENTE 2");
                System.out.println(cli2);
                System.out.println("\nPRUEBA DEL APARTADO 3 DEL EJERCICIO 4");
                System.out.println("Si el cli1 fuese un puntero al objeto remoto que está almacenado\n"
                        + " en la misma maquina donde ahora se ejecuta el cliente si realizo\n"
                        + " un getClient del mismo y lo imprimo recibiria los mismos datos\n"
                        + " modificados que he realizado para el apartado anterior.");
                Client cli1Aux = cGateway.getClient(11111111);
                System.out.println("CLIENTE 1");
                System.out.println(cli1);
                System.out.println("CLIENTE 1 EXTRAIDO DE NUEVO DEL SERVIDOR");
                System.out.println(cli1Aux);
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
