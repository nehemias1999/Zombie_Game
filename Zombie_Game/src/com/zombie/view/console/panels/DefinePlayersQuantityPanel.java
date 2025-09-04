package com.zombie.view.console.panels;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.view.console.MainJFrame;

public class DefinePlayersQuantityPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
    public DefinePlayersQuantityPanel(
    		IView viewAdministrator, 
    		MainJFrame frame) {
        this.viewAdministrator = viewAdministrator;
        this.frame = frame;        
    }
    
    private void startEnterActions() {
        frame.setEnterAction(e -> {
        	String raw = frame.getInputText();
            String input = raw == null ? "" : raw.trim();
            
            if (input.isEmpty()) {
                viewAdministrator.showErrorMessage("You must enter a number.");
            } else {
            	
            	frame.appendLine("> " + input);
                
                try {
                	
                    int opcion = Integer.parseInt(input);
                    
                    if (opcion >= 2 && opcion <= 4) {
                    	viewAdministrator.getDataDefinePlayersQuantity(input);
                    } else {
                        viewAdministrator.showErrorMessage("Invalid option. Must be between 2 and 4.");
                    }
                    
                } catch (NumberFormatException ex) {
                    viewAdministrator.showErrorMessage("You must enter a valid number.");
                }
                
            }
            
            frame.clearInput();
            
        });
        
    }
    
    private void getPanel() {
    	frame.appendLine("=== QUANTITY OF PLAYERS ===");
        frame.appendLine("");
        frame.appendLine("Choose the number of players to play (between 2 and 4):");	
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
