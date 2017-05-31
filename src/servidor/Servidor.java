/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author evolutel
 */
public class Servidor {

    public static final int PORT = 6579;
    public static HashMap<String, Cliente> clientes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket server = new ServerSocket(PORT); //SET PORT NUMBER
            System.out.println("Waiting for clients...");//AT THE START PRINT THIS
            while (true) {
                Socket s = server.accept();//ACCEPT SOCKETS(CLIENTS) TRYING TO CONNECT
                System.out.println("Client connected from " + s.getLocalAddress().getHostName());   //  TELL THEM THAT THE CLIENT CONNECTED
                Cliente chat = new Cliente(s);//CREATE A NEW CLIENT OBJECT
                Thread t = new Thread(chat);//MAKE A NEW THREAD
                t.start();//START THE THREAD
            }
        } catch (Exception e) {
            System.out.println("An error occured.");//IF AN ERROR OCCURED THEN PRINT IT
            e.printStackTrace();
        }
    }

}
