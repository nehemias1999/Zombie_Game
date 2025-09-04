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
				 viewAdministrator.showErrorMessage("You must enter an option.");
			 } else {
	            
				 frame.appendLine("> " + input);
	
				 try {
	            	
					 int opcion = Integer.parseInt(input);
					 if(!isPersistedGame) {
						 switch (opcion) {
						 	case 1 -> viewAdministrator.startGame();
							case 0 -> viewAdministrator.quitGame();
			                default -> {
			                	viewAdministrator.showErrorMessage("Invalid option. Choose 1 or 0.");    
			                }
			             }
					 } else {
						 switch (opcion) {
						 	case 1 -> viewAdministrator.startGame();
						 	case 2 -> viewAdministrator.continuePersistedGame();
						 	case 0 -> viewAdministrator.quitGame();
		                    default -> {
		                    	viewAdministrator.showErrorMessage("Invalid option. Choose 1, 2 or 0.");	  
		                    }
						 }						 
					 }
	                
				 } catch (NumberFormatException ex) {
					 viewAdministrator.showErrorMessage("Invalid format: Enter a number.");
				 }
	            
			 }
	
			 frame.clearInput();
	        
		 });
	 }
	
	 private void getPanel() {
		 frame.appendLine("=== ZOMBIE GAME ===");
		 frame.appendLine("1) Start game");
		 if(isPersistedGame) {
			 frame.appendLine("2) Continue game"); 
		 } 
		 frame.appendLine("0) Quit");
		 frame.appendLine("");
		 frame.appendLine("Choose an option and press Enter:");		
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
