package GSILabs.net;

import java.net.*;
import java.io.*;
import java.util.*;

public class Communication {
    
    public void CrearCliente() {
        Socket conexion=null;
        DataInputStream entrada=null;
        DataOutputStream salida=null;
        String cad="";
        byte datosBytes[]=new byte[256];
        int leido=0;
        String peticion="";

        try
        {
            conexion = new Socket (InetAddress.getByName ("http://localhost/index.html"), 8000);
            System.out.println("Conectando");
            /*Enviamos lo que envía el navegador FireFox al    pedir una página*/
            peticion="GET /index.html HTTP/1.1\n";
            peticion+="Accept: */*\n";
            peticion+="Accept-Language: es\n";
            peticion+="Accept-Encoding: gzip, deflate\n";
            peticion+="User-Agent: Mozilla/4.0 (compatible; MSIE 5.01;    Windows NT)\n";
            peticion+="Host: 127.0.0.1\n";
            peticion+="Connection: Keep-Alive\n\n";
            salida=new DataOutputStream(conexion.getOutputStream());
            salida.write(peticion.getBytes());
            salida.write(("<html>Hola mundo</html>").getBytes());
            //Vemos lo que nos envía el Servidor
            entrada=new DataInputStream(conexion.getInputStream());

                try
                {
                    while ((leido=entrada.read(datosBytes,0,256))!=1)

                    if (leido>0)
                       System.out.println (new String(datosBytes,0,(leido-1)));
                }
                catch (EOFException e) {}

             conexion.close();
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
            System.out.println("Erroronectar");
        }
    }

    public void CrearServidor() {
        //Socket conexion;
        ServerSocket sck;
        BufferedReader datos;
        InputStream Entradadatos;
        String texto="";
        int cliente=0;
        int leido=0;

        try
        {
            //Creamos un servidor
            ServerSocket conexion=new ServerSocket(80,2,InetAddress.getByName("127.0.0.1"));
            DataInputStream entrada=null;
            DataOutputStream salida=null;
            String cad="",body="",respuesta="";
            byte  bytedata[]=new byte[256];


            while (true)
            {
                ++cliente;
                Socket conex=conexion.accept();

                entrada=new DataInputStream(conex.getInputStream());
                System.out.println("Cliente"+Integer.toString(cliente));
                InetAddress direc=conex.getInetAddress();
                System.out.println ("Dirección de llamada"+direc.getHostAddress());
                cad="";
                leido=entrada.read(bytedata);
                if (leido>0)
                    cad=new String(bytedata,0,(leido-1));
                //Sacamos lo que hemos recibido desde el cliente
                System.out.println("Recibidoe cliente");
                System.out.println(cad);
                salida=new DataOutputStream(conex.getOutputStream());
                body+="<html>\n";
                body+="<body>\n";
                body+="<h1>Hola que tal!</h1>\n";
                body+="<image src='barra.gif'>\n";
                body+="</body>\n";
                body+="</html>\n";

                if (cad.indexOf("barra.gif")>=0)
                {
                    respuesta="HTTP/1.0 200 OK\n";
                    respuesta+="Date: Mon, 5 Nov 2001 23:59:59GMT\n";
                    respuesta+="Content-Type: image/gif\n";
                    respuesta+="Content-Length: 112\n\n";
                    //Hay que añadir la imagen
                    //Escribimos la respuesta al cliente
                    salida.write(respuesta.getBytes());
                    salida.flush();
                    try
                    {

                       FileInputStream imagen=new FileInputStream("barra.gif");
                        while((leido=imagen.read(bytedata,0,256))!=-1)
                            salida.write(bytedata,0,leido);
                            salida.flush();
                    }
                    catch (IOException e)
                    {

                    }
                }
                else if (cad.indexOf("hola.html")>=0)
                {
                    respuesta="HTTP/1.0 200 OK\n";
                    respuesta+="Date: Mon, 5 Nov 2001 23:59:59 GMT\n";

                    respuesta+="Content-Type: text/html\n";
                    respuesta+="Content-          Length:"+Integer.toString(body.length())+"\n\n";
                    respuesta+=body;
                    //Escribimos la respuesta al cliente
                    salida.writeBytes(respuesta);
                    salida.flush();
                }
                else
                {
                    respuesta="HTTP/1.0 404 Error no encontrado\n";
                    respuesta+="Date: Mon, 5 Nov 2001 23:59:59 GMT\n";

                    respuesta+="Content-Type: text/html\n";
                    respuesta+="Content-          Length:"+Integer.toString( body.length())+"\n\n";
                    respuesta+="No se ha encontrado el documento";
                    //Escribimos la respuesta al cliente
                    salida.writeBytes(respuesta);
                    salida.flush();
                }


                //Cerramos el Socket
                conex.close();
            }

        }
        catch (IOException e)
        {
            System.out.println(e.toString());
            System.out.println("Erroronectar");
        }
    }
    public static void main(String[] args)
    {
        Communication Comuc =new Communication();
        Comuc.CrearServidor();
        //Comuc.CrearCliente();
        //Comuc.pruebaURL();
    }
}