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
    	frame.appendLine("=== FIN DEL JUEGO ===");
        frame.appendLine("");
        frame.appendLine("");
        frame.appendLine("Jugador perdedor: " + lostPlayerName);
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Presione Enter para ir al menu principal:");
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
