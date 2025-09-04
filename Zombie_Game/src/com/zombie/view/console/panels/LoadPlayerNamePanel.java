package com.zombie.view.console.panels;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.view.console.MainJFrame;

public class LoadPlayerNamePanel implements IPanel {
	
	private IView wiewAdministrator;
	private MainJFrame frame;
	
	public LoadPlayerNamePanel(
			IView wiewAdministrator, 
			MainJFrame frame) {
		this.wiewAdministrator = wiewAdministrator;
		this.frame = frame;     
	}
	
    private void startEnterActions() {
        frame.setEnterAction(e -> {
        	String raw = frame.getInputText();
            String input = raw == null ? "" : raw.trim();
            
            if (input.isEmpty()) {
                wiewAdministrator.showErrorMessage("Debes ingresar un nombre.");
            } else {
            	
            	frame.appendLine("> " + input);
                
            	wiewAdministrator.getDataLoadPlayer(input);
                
            }
            
            frame.clearInput();
            
        });
        
    }
    
    private void getPanel() {
    	frame.appendLine("=== CARGAR NOMBRE DE JUGADOR ===");
        frame.appendLine("");
        frame.appendLine("Ingrese el nombre del jugador:");
	}
    
	@Override
	public void showPanel() {
		startEnterActions();
        getPanel();
        frame.clearInput();
        frame.setEditabledInput(true);
    }
	
	@Override
	public void showErrorMessage(String message) {
		frame.appendLine(message);
	}
		    
}
