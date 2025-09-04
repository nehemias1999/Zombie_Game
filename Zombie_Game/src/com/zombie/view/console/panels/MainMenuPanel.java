package com.zombie.view.console.panels;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.view.console.MainJFrame;

public class MainMenuPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	private boolean isPersistedGame;
	
	 public MainMenuPanel(
			 IView viewAdministrator, 
			 MainJFrame frame) {
		 this.viewAdministrator = viewAdministrator;
		 this.frame = frame;  
	 }
	 
	 private void getPanelData() {
		 isPersistedGame = viewAdministrator.isPersistedGame();
     }	
 
	 private void startEnterActions() {
		 frame.setEnterAction(e -> {
			 String raw = frame.getInputText();
			 String input = raw == null ? "" : raw.trim();
	
			 if (input.isEmpty()) {
				 viewAdministrator.showErrorMessage("Debes ingresar una opción.");
			 } else {
	            
				 frame.appendLine("> " + input);
	
				 try {
	            	
					 int opcion = Integer.parseInt(input);
					 if(!isPersistedGame) {
						 switch (opcion) {
						 	case 1 -> viewAdministrator.startGame();
							case 0 -> viewAdministrator.quitGame();
			                default -> {
			                	viewAdministrator.showErrorMessage("Opción inválida. Elige 1 o 0.");    
			                }
			             }
					 } else {
						 switch (opcion) {
						 	case 1 -> viewAdministrator.startGame();
						 	case 2 -> viewAdministrator.continuePersistedGame();
						 	case 0 -> viewAdministrator.quitGame();
		                    default -> {
		                    	viewAdministrator.showErrorMessage("Opción inválida. Elige 1, 2 o 0.");	  
		                    }
						 }						 
					 }
	                
				 } catch (NumberFormatException ex) {
					 viewAdministrator.showErrorMessage("Formato inválido: ingresa un número.");
				 }
	            
			 }
	
			 frame.clearInput();
	        
		 });
	 }
	
	 private void getPanel() {
		 frame.appendLine("=== BIENVENIDO A ZOMBIE ===");
		 frame.appendLine("1) Iniciar Juego");
		 if(isPersistedGame) {
			 frame.appendLine("2) Continuar partida"); 
		 } 
		 frame.appendLine("0) Salir");
		 frame.appendLine("");
		 frame.appendLine("Elija una opción y presione Enter:");		
	 }
		
	 @Override
	 public void showPanel() {
		 getPanelData();
		 startEnterActions();
		 getPanel();
		 frame.clearInput();
		 frame.setEditabledInput(true);
	 }

	 @Override
	 public void showErrorMessage(String mensaje) {
		frame.appendLine(mensaje);
	 }

}
