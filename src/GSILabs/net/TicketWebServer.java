/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.net;

import GSILabs.BModel.*;
import GSILabs.BSystem.BussinessSystem;
import GSILabs.BTesting.P01Tester;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TicketWebServer extends Thread{
    
    private final Socket clientSocket;
    private final ToHTML toHTML = new ToHTML();
    private final BussinessSystem bs;
    //private static int port;
    //private static String domain;
    
    public TicketWebServer(Socket clientSocket, BussinessSystem bSystem){       
        this.clientSocket = clientSocket;
        this.bs = bSystem;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("-----Servidor Web Java BussinessSystem-----");
        System.out.println("Introduzca el puerto donde desea lanzar el servidor: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int puerto = Integer.parseInt(br.readLine());
        System.out.println("Introduzca el dominio donde desea lanzar el servidor: ");
        String dominio = br.readLine();
        run(puerto,dominio);
        
    }
    
    /**
     * Con este metodo lanzamos el servidor en el puerto y dominio indicado
     * a su vez se queda a la espera de nuevos clientes para servirles
     * @param serverPort puerto donde se lanza el servidor
     * @param serverDomain dominio donde se lanza el servidor
     * @return
     */
    public static boolean run(int serverPort, String serverDomain) {
              
        try {
            ServerSocket server = new ServerSocket(serverPort);
            BussinessSystem bSystem = P01Tester.getBussinessSystem();
            System.out.println("MiniServer active " + serverPort);
            while (true) {
                TicketWebServer ticketWebServer = new TicketWebServer(server.accept(), bSystem);
                ticketWebServer.start();
            }
        } 
        catch (Exception e) {
            /*if(isStopped()) {
                System.out.println("Server Stopped.") ;
                return;
            }*/
            throw new RuntimeException("Error accepting client connection", e);
        }
        finally{
            return true;
        }
        
    }
    
    @Override
    public void run() {
        try {
            InputStream is = clientSocket.getInputStream();
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line;
                line = in.readLine();
                String request_method = line;
                // Entre todas las peticiones HTTP que llegan al servidor solo vamos a analizar aquellas que pidan un
                // archivo HTML, es decir, con formato GET /"dominio" HTTP/1.1 el resto de peticiones las desecharemos
                // por eso en el if compruebo que comienza y finaliza con dicho formato anterior.
                // Además a base de ejecutar muchas veces este método y analizar las peticiones que envian los navegadores
                // hemos comprobado que Chrome envia una petición que no nos aporta nada, esta petición es: GET /favicon.ico HTTP/1.1
                // es por ello que si contiene la secuencia favicon.ico desechamos también dicha petición porque no nos es útil
                // analizarla.
                if ((request_method != null) && (request_method.startsWith("GET /")) && (request_method.endsWith("HTTP/1.1")) && (!request_method.contains("favicon.ico"))){
                    // Le paso la subsecuencia que contiene unicamente el dominio que me pide el cliente
                    // de modo que lo analizo y separo en trozos para poder ver en más profundidad que me
                    // el cliente que le devuelva
                    System.out.println(request_method.substring(5, request_method.length() - 9));
                    Map <String,String> parms = queryToMap(request_method.substring(5, request_method.length() - 9));
                    System.out.println("parms es " + parms);
                    // En funcion de que palabra clave saco de los argumentos que me proporciona la URL
                    // cogida mediante la peticion GET, realizaremos un accion u otra
                    if ((parms.size() == 1)) {
                        if (parms.containsKey("") || parms.containsKey("events"))
                            // Mostramos lista de eventos
                            out.println(toHTML.listOfEvents());
                        else if (parms.containsKey("concerts"))
                            // Mostramos lista de conciertos
                            out.println(toHTML.listOfConcerts(bs.getConcerts()));
                        else if (parms.containsKey("exhibitions"))
                            // Mostramos lista de exibiciones
                            out.println(toHTML.listOfExhibitions(bs.getExhibitions()));
                        else if (parms.containsKey("festivals"))
                            // Mostramos lista de festivales
                            out.println(toHTML.listOfFestivals(bs.getFestivals()));
                        else if (parms.containsKey("concertName"))
                            // Mostramos un concierto
                            out.println(toHTML.concertToHTML((Concert)bs.getEvent(ponerEspacios(parms.get("concertName")))));
                        else if (parms.containsKey("exhibitionName"))
                            // Mostramos una exibicion
                            out.println(toHTML.exhibitionToHTML((Exhibition)bs.getEvent(ponerEspacios(parms.get("exhibitionName")))));
                        else if (parms.containsKey("festivalName"))
                            // Mostramos un festival
                            out.println(toHTML.festivalToHTML((Festival)bs.getEvent(ponerEspacios(parms.get("festivalName")))));
                        else if (parms.containsKey("locationName"))
                            // Mostramos una localizacion
                            out.println(toHTML.locationToHTML((Location)bs.getLocation(ponerEspacios(parms.get("locationName")))));
                        else if (parms.containsKey("artistName"))
                            // Mostramos un artista
                            out.println(toHTML.artistToHTML((Artist)bs.getArtists().get(ponerEspacios(parms.get("artistName")))));
                        else if (parms.containsKey("collectiveName"))
                            // Mostramos un colectivo con todos sus artistas
                            out.println(toHTML.collectiveToHTML((Collective)bs.getCollectives().get(ponerEspacios(parms.get("collectiveName")))));
                        else
                            out.println("<html><body><h4>Website not found</h4></body></html>");
                    }
                }
                System.out.println("1.HTTP-HEADER: " + line);
            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("An IOException has been occured");
        }
    }
    
    public static Map<String, String> queryToMap (String query) {
        Map<String, String> result = new HashMap<>();
        String[] queries;
        if (query.contains("?")) {
            queries = query.split("\\?");
            query = queries[1];
        }
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1)
                result.put(pair[0], pair[1]);
            else
                result.put(pair[0], "");
        }
        return result;
    }
    
    //Sustituir los guiones bajos que habíamos puesto en los enlaces por espacios de nuevo
    public String ponerEspacios (String enlace) {
        return enlace.replaceAll("_", " ");
    }
}
