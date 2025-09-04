package com.zombie;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import com.zombie.controller.Controller;
import com.zombie.interfaces.IView;
import com.zombie.view.administrators.ConsoleViewAdministrator;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.Cliente;

public class ClientApp {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final Integer SERVER_PORT = 9999;
	private static final String CLIENT_IP = "127.0.0.1";
	
	public static void main(String[] args) throws RemoteException {

		String clientPort = (String) JOptionPane.showInputDialog(
			null, 
			"Seleccione el puerto en el que escuchar� peticiones el cliente", 
			"Puerto del cliente", 
			JOptionPane.QUESTION_MESSAGE,
			null,
			null,
			9998
		);
				
		Controller controller = new Controller();
		IView view = new ConsoleViewAdministrator();
           
        view.setController(controller);
        controller.setView(view);
 
        Cliente c = new Cliente(
			CLIENT_IP, 
			Integer.parseInt(clientPort), 
			SERVER_IP, 
			SERVER_PORT
		);

        try {
            c.iniciar(controller);
        } catch (RMIMVCException e) {
            e.printStackTrace();
            return;
        }

        view.showMainMenuPanel();
    }

}

