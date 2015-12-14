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
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author subiza.79082
 * @author izu.78236
 * @version 10/12/2015
 */
public class TicketWebServer {
    
    ServerSocket sc;
    Socket so;
    DataOutputStream salida;
    String mensajeRecibido;
    
    
    
    
    //BussinessSystem bs = new BussinessSystem();
    
    public TicketWebServer () {
        
        //bs = (BussinessSystem)t;
        
    }
    
    
    /**
     * 
     * @param p Puerto sobre el que se lanza el servidor
     * @param domain Dirección web de la página
     * @return True la primera invocación sobre un puerto. 
     *         False reinvocaciones sobre el mismo puerto, sin surtir efecto.
     */
    boolean run (int p, String domain) {
        
        BufferedReader entrada;
        
        try {
            sc = new ServerSocket(p);/* crea socket servidor que escuchara en puerto 5000*/
            so = new Socket();
            System.out.println("Esperando una conexión:");
            so = sc.accept();
            //Inicia el socket, ahora está esperando una conexión por parte del cliente
            System.out.println("Un cliente se ha conectado.");
            //Canales de entrada y salida de datos
            entrada = new BufferedReader(new InputStreamReader(so.getInputStream()));
            salida = new DataOutputStream(so.getOutputStream());
            System.out.println("Confirmando conexión al cliente....");
            salida.writeUTF("Conexión exitosa...n envia un mensaje :D");
            //Recepcion de mensaje
            mensajeRecibido = entrada.readLine();
            System.out.println(mensajeRecibido);
            salida.writeUTF("Se recibio tu mensaje.n Terminando conexion...");
            salida.writeUTF("Gracias por conectarte, adios!");
            System.out.println("Cerrando conexión...");   
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        
        return true;
        
    }
    
    boolean stop () throws IOException {
        
        sc.close();//Aqui se cierra la conexión con el cliente
        
        return true;
        
    }    
}
