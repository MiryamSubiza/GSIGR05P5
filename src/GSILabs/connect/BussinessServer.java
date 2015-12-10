package GSILabs.connect;

import GSILabs.BSystem.PublicBussinessSystem;
import GSILabs.persistence.XMLParsingException;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Clase ejecutable que crea una instancia de PublicBusinessSystem,
 * puebla dicha instancia vía xml y lo publica en un registro RMI 
 * (puerto 1099) bajo dos identificadores diferentes CLGateway y 
 * EVGateway.
 * @author subiza.79082
 * @author izu.78236
 * @version 23/11/2015
 */
public class BussinessServer {
    
    private static int RMI_PORT1=1099;
    private static int RMI_PORT2=1100;
    
    public static void main(String[] args) throws RemoteException, XMLParsingException {
        
        // Paso 1- crear un Security Manager
        //  Comment if having troubles in publishing the object
        //  "Comment" equals "Comment under your own responsability"
        //if (System.getSecurityManager() == null) {
        //    System.setSecurityManager(new SecurityManager());
        //}
        
        // Paso 2 - Crear un objeto y hacer un publicable de él.    
        // Voy a poblar la instancia de clase PublicBussinessSystem con un XML
        File f = new File("publicBussinessSystem.xml");
        PublicBussinessSystem pBSystem = new PublicBussinessSystem();
        try {
            pBSystem = PublicBussinessSystem.parseXMLFile(f);
        }
        catch (XMLParsingException e) {
            System.out.println("Se ha capturado una XMLParsingException:");
            if(e.getFileName() != null){
                System.out.println(e.getFileNameDescription());
            }
            System.out.println(e.getMessage()); 
        }        
        BussinessGateway stub1 =(BussinessGateway) UnicastRemoteObject.exportObject(pBSystem,RMI_PORT1);        
        
        // Step 3- Creamos un registro en el puerto deseado y publicamos ahí los objetos,
        //  que serán accesibles en términos de interfaz bajo la etiqueta de "BussinessSystem"
        
        try {
            System.out.println("About to create the registries");
            Registry reg1 = LocateRegistry.createRegistry(RMI_PORT1);            
            System.out.println("Registry 1 create");            
            reg1.rebind("CLGateway", stub1);
            reg1.rebind("EVGateway", stub1);
            System.out.println("Stub rebind done");
        }
        catch (RemoteException re) {
            System.out.println("RMI Error in publishing the stub: "+re.getMessage());
        }
        
    }
    
}
