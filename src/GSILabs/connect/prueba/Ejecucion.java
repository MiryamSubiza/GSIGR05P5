/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GSILabs.connect.prueba;

/**
 *
 * @author mirya
 */
public class Ejecucion {
    
    public static void main(String[] args) {
    
        EjemploOracle server = new EjemploOracle(9500);
        new Thread(server).start();

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("Stopping Server");
        //server.stop();
        
    }
    
}
