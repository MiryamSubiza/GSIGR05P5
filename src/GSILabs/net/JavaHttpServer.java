/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GSILabs.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*
 * a simple static http server
*/
public class JavaHttpServer {

    private static HttpServer server;
    
    public static void main(String[] args) throws Exception {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/enlace", new Handler2());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void stop() {
        server.stop(5);
    }
    
}
