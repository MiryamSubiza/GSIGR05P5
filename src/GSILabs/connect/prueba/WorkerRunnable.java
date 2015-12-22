/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.connect.prueba;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + this.serverText + " - " + time + "").getBytes());
            //output.close();
            //input.close();
            System.out.println("Request processed: " + time);
            System.out.println("hola");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("adios");
            
            List<String> headers = new ArrayList<String>();
            String str;
            while ((str = reader.readLine()) != null)
            {
               headers.add(str);
               if (str.startsWith("Content-Length: "))
               {
                  break; // and don't get the next line!
               }
            }
            System.out.println(" headers es " + headers);
            
        } catch (IOException e) {
            //report exception somewhere.
            System.out.println("Salta excepción");
            e.printStackTrace();
        }
    }
}