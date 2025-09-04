package com.zombie.view.console.panels;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.view.console.MainJFrame;

public class EndOfRoundPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	private String lostPlayerName;
	
	public EndOfRoundPanel(
			IView viewAdministrator, 
			MainJFrame frame) {
		this.viewAdministrator = viewAdministrator;
		this.frame = frame;    
	}
	
    private void getPanelData() {
    	lostPlayerName = viewAdministrator.getLostPlayerName();
    }
		
    private void startEnterActions() {
        frame.setEnterAction(e -> {  
        	viewAdministrator.returnToMainMenu();
        });
    }
        
    private void getPanel() {
    	frame.appendLine("=== END OF GAME ===");
        frame.appendLine("");
        frame.appendLine("");
        frame.appendLine("Lost player: " + lostPlayerName);
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Press Enter to go to the main menu:");
	}
    
	@Override
    public void showPanel() {
		getPanelData();
		startEnterActions();
        getPanel();
        frame.clearInput();
        frame.setEditabledInput(false);
    }
	
	@Override
	public void showErrorMessage(String message) {
		frame.appendLine(message);
	}
    
}
