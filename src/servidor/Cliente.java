/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import cliente.Decorador.Componente;
import cliente.Decorador.Decorador;
import cliente.Decorador.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import cliente.fachada.Linea;
import cliente.fachada.Punto;

public class Cliente implements Runnable {

    private Socket socket;//SOCKET INSTANCE VARIABLE
    private ObjectOutputStream response;
    private ObjectInputStream request;

    public Cliente(Socket s) {
        socket = s;//INSTANTIATE THE SOCKET
        try {
            this.response = new ObjectOutputStream(socket.getOutputStream());
            this.request = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() //(IMPLEMENTED FROM THE RUNNABLE INTERFACE)
    {
        try {
            while (true) {
                Object peticion = this.request.readObject();//IF THERE IS INPUT THEN MAKE A NEW VARIABLE input AND READ WHAT THEY TYPED
                if (peticion != null) {
                    Linea m = null;
                    try {
                        m = (Linea) peticion;
                    } catch (Exception e) {
//                        Componente c = null;
//                        while (!(c instanceof Linea)) {
//                            c = ((Decorador) peticion).getComponente();
//                        }
//                        m = (Linea) c;
                    }
                    Mensaje m2 = null;
                    try {
                        m2 = (Mensaje) peticion;
                        if (m2.getTo().equals("SERVIDOR")) {
                            switch (m2.getAccion().toUpperCase()) {
                                case "LOGIN":
                                    System.out.println("conectado =>" + m2.getContenido());
                                    Servidor.clientes.put(m2.getContenido(), this);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                    }
                    System.out.println(peticion);
                    if (m != null) {
                        for (Cliente c : Servidor.clientes.values()) {
                            if (!c.socket.isClosed()) {
                                System.out.println("Enviando => " + peticion.toString());
                                c.response.writeObject(peticion);
                                c.response.flush();
                            }
                        }
                    }
//                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Desconectando  ");
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

}
