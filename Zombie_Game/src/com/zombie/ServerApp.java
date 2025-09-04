package com.zombie;

import java.rmi.RemoteException;

import com.zombie.model.Model;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;

public class ServerApp {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final Integer SERVER_PORT = 9999;

	public static void main(String[] args) {
		
		Model model = new Model();
		Servidor server = new Servidor(SERVER_IP, SERVER_PORT);
		
		try {
			server.iniciar(model);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (RMIMVCException e) {
			e.printStackTrace();
		}
		
	}

}

