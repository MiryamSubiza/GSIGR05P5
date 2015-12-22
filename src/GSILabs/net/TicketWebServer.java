/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GSILabs.net;

import GSILabs.BSystem.BussinessSystem;
import GSILabs.BSystem.TicketOffice;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.PortUnreachableException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Context;

/**
 * 
 * @author subiza.79082
 * @author izu.78236
 * @version 10/12/2015
 */
public class TicketWebServer {
    
    private BussinessSystem bs;
    private ServerSocket sc; //Socket servidor
    private Socket so; //Socket cliente
    private boolean stopped;
    
    
    DataOutputStream salida;
    String mensajeRecibido;
    
    
    
    public TicketWebServer (BussinessSystem bs) {
        
        this.bs = bs;
        this.sc = null;
        this.stopped = false;
        
    }
    
    /**
     * 
     * @param p Puerto sobre el que se lanza el servidor
     * @param domain Dirección web de la página
     * @return True la primera invocación sobre un puerto. 
     *         False reinvocaciones sobre el mismo puerto, sin surtir efecto.
     */
    public boolean run (int p, String domain) {
    
        try {
            //Creación del socket servidor que escuchará en puerto p
            this.sc = new ServerSocket(p);
        }
        catch (PortUnreachableException ex) {
            System.out.println("Puerto " + p + " inalcanzable. " + ex);
        }
        catch (ConnectException ex) {
            System.out.println("->Error en la escucha sobre el puerto " + p);
            System.out.println(ex);
        }
        catch (IOException ex) {
            System.out.println("Error en la escucha sobre el puerto " + p);
            System.out.println(ex);
        }
        System.out.println("Esperando una conexión: ");
        this.so = new Socket();
        try {
            //Listens for a connection to be made to this socket and accepts it
            so = sc.accept();
            //Conexión por parte del cliente
            System.out.println("Un cliente se ha conectado");
        }
        catch (SocketException ex) {
            System.out.println("Error accediendo al socket. " + ex);
        }
        catch (IOException ex) {
            System.out.println("Error estableciendo la conexión con el cliente: " + ex);
            if (stopped) 
                System.out.println("Se ha detenido el servidor");
        }
        
        
        
        BufferedReader entrada;
        
        try {
            
            
            //Canales de entrada y salida de datos
            entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));
            salida = new DataOutputStream(so.getOutputStream());
            System.out.println("Confirmando conexión al cliente....");
            salida.writeUTF("Conexión exitosa...n envia un mensaje :D");
            //Recepcion de mensaje
            //mensajeRecibido = entrada.readLine();
            //System.out.println("El cliente manda: " + mensajeRecibido);
            salida.write(("<html>Hola mundo</html>").getBytes()); 
            //salida.writeUTF("Se recibio tu mensaje.n Terminando conexion...");
            //salida.writeUTF("Gracias por conectarte, adios!");                       
            System.out.println("Cerrando conexión...");
            
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        
        return true;
        
    }
    
    /**
     * 
     * @return True si se ha cerrado correctamente la conexión con el cliente.
     *         False en caso contrario.
     */
    public boolean stop () {
        
        try {
            this.sc.close(); //Closes this socket
            this.stopped = true;
        } catch (IOException ex) {
            System.out.println("Error cerrando el servidor. " + ex);
        }
        return stopped;
    }    
}
