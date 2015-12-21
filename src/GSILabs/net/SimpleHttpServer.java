package GSILabs.net;

import GSILabs.BSystem.BussinessSystem;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleHttpServer {

    private BussinessSystem bs;
    private boolean stopped = false;
    private HttpServer server;
    
    public SimpleHttpServer (BussinessSystem bs) {
        this.bs = bs;   
    }
    
    public static void main(String[] args) {
        
        
        
    }
    
    public boolean run (int p, String domain) {
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            System.out.println("Error creando el servidor. " + e);
        }
        server.createContext("/info", new InfoHandler());
        server.createContext("/get", new GetHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("The server is running");
        return true;
    }
    
    public boolean stop () {
        server.stop(1);
        this.stopped = true;
        return stopped;
    } 
    
    

  // http://localhost:8000/info
  static class InfoHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
      String response = "Use /get?hello=word&foo=bar to see how to handle url parameters";
      SimpleHttpServer.writeResponse(httpExchange, response.toString());
    }
  }

  static class GetHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
      StringBuilder response = new StringBuilder();
      Map <String,String>parms = SimpleHttpServer.queryToMap(httpExchange.getRequestURI().getQuery());
      response.append("<html><body>");
      response.append("hello : " + parms.get("hello") + "<br/>");
      response.append("foo : " + parms.get("foo") + "<br/>");
      response.append("</body></html>");
      SimpleHttpServer.writeResponse(httpExchange, response.toString());
    }
  }

  public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
    httpExchange.sendResponseHeaders(200, response.length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }


  /**
   * returns the url parameters in a map
   * @param query
   * @return map
   */
  public static Map<String, String> queryToMap(String query){
    Map<String, String> result = new HashMap<String, String>();
    for (String param : query.split("&")) {
        String pair[] = param.split("=");
        if (pair.length>1) {
            result.put(pair[0], pair[1]);
        }else{
            result.put(pair[0], "");
        }
    }
    return result;
  }

}
