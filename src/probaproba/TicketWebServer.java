/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probaproba;

import GSILabs.BModel.Artist;
import GSILabs.BModel.Concert;
import GSILabs.BModel.Exhibition;
import GSILabs.BModel.FechaCompleta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class TicketWebServer {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("MiniServer active " + PORT);
            while (true) {
                new ThreadSocket(server.accept());
            }
        } 
        catch (Exception e) {
            /*if(isStopped()) {
                System.out.println("Server Stopped.") ;
                return;
            }*/
            throw new RuntimeException("Error accepting client connection", e);
        }
    }
}
class ThreadSocket extends Thread {
    private Socket clientSocket;
    ThreadSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.start();
    }
    @Override
    public void run() {
        try {
            InputStream is = clientSocket.getInputStream();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
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
                System.out.println(request_method.substring(5, request_method.length() - 8));
                Map <String,String> parms = queryToMap(request_method.substring(5, request_method.length() - 8));
                System.out.println("parms es " + parms);
            }
            
            System.out.println("1.HTTP-HEADER: " + line);
            line = "";
            // looks for post data
            /*int postDataI = -1;
            while ((line = in.readLine()) != null && (line.length() != 0)) {
                System.out.println("2.HTTP-HEADER: " + line);
                if (line.indexOf("Content-Length:") > -1) {
                    postDataI = new Integer(line.substring(line.indexOf("Content-Length:") + 16,line.length())).intValue();
                }
            }
            String postData = "";
            // read the post data
            if (postDataI > 0) {
                char[] charArray = new char[postDataI];
                in.read(charArray, 0, postDataI);
                postData = new String(charArray);
            }*/
            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html; charset=utf-8");
            out.println("Server: MINISERVER");
            // this blank line signals the end of the headers
            out.println("");
            // Send the HTML page
            out.println("<H1>List of events</H1>");
            out.println("<H2>Request Method->" + request_method + "</H2>");
            /*out.println("<H2>Post->" + postData + "</H2>");*/
            //out.println("<form name=\"input\" action=\"form_submited\" method=\"post\">");
            //out.println("Username: <input type=\"text\" name=\"user\"><input type=\"submit\" value=\"Submit\"></form>");
            out.println("<H3><a href='/concerts'>Concerts</a></H3>");
            out.println("<H3><a href='/exhibitions'>Exhibitions</a></H3>");
            out.println("<H3><a href='/festivals'>Festivals</a></H3>");
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, String> queryToMap (String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }
            else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
    
    public String concertToHTML (Concert c) {
        String html;
        html = "<html><head>Concert</head><body>Name: " + c.getName() + "\nPerformer: ";
        if (c.getPerformer() instanceof Artist)
            html.concat("<a href='/artist?artistName=" + c.getPerformer().getName() + "'>" 
                    + c.getPerformer().getName() + "</a>\n");
        else
            html.concat("<a href='/collective?collectiveName=" + c.getPerformer().getName() 
                    + "'>" + c.getPerformer().getName() + "</a>\n");
        html.concat("Date: " + ((FechaCompleta)c.getStartDate()).fechaToString() + "\nDoor opening: " 
                + ((FechaCompleta)c.getDoorOpeningTimeConcert()).horaToString() + "h\nStart time: " 
                + ((FechaCompleta)c.getStartTimeConcert()).horaToString() + "h\nClosing time: " 
                + ((FechaCompleta)c.getClosingTimeConcert()).horaToString() + "h\nLocation: " 
                + "<a href='/location?locationName=" + c.getLocation().getName() + "'>" + 
                c.getLocation().getName() + "</a>\n" + "</body></html>");
        return html;
    }
    
    public String exhibitionToHTML (Exhibition e) {
        String html;
        html = "<html><head>Exhibition</head><body>Name: " + e.getName() + "\nTitle: "
                + e.getTitle() + "\nOrganizer: " + e.getOrganizerName() + "Start date: " 
                + ((FechaCompleta)e.getStartDate()).fechaToString() + "\nStart time: " 
                + ((FechaCompleta)e.getStartTimeExhibition()).horaToString() + "h\nClosing time: " 
                + ((FechaCompleta)e.getClosingTimeExhibition()).horaToString() + "h\nClosing date: "
                + ((FechaCompleta)e.getEndingDate()).fechaToString();
        if (e.getPerformer() instanceof Artist)
            html.concat("<a href='/artist?artistName=" + e.getPerformer().getName() + "'>" + 
                    e.getPerformer().getName() + "</a>\nWeb links: ");
        else
            html.concat("<a href='/collective?collectiveName=" + e.getPerformer().getName() + "'>" 
                    + e.getPerformer().getName() + "</a>\nWeb links: ");
        Iterator i = e.getWebLinks().iterator();
        while (i.hasNext()) {
            html.concat(i.next() + "  ");
        }
        html.concat("\nLocation: <a href='/location?locationName=" + e.getLocation().getName() + "'>" + 
                e.getLocation().getName() + "</a>\n</body></html>");
        return html;
    }
}
