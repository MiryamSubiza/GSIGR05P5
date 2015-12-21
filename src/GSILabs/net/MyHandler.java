/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.net;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Alex
 */
public class MyHandler implements HttpHandler {
    
    public void handle(HttpExchange t) throws IOException {
        
        //File f = new File("test/index.html");
        //String response = "<html><body><a href=\"enlace/\" title=\"Descripción\">Enlace a otra pagina</a></body></html>";
        String response = "<html><body><a href='/enlace'>Enlace a otra pagina</a></body></html>";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
        
    }
    
}