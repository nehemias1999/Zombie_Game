package com.zombie.view.console.panels;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.view.console.MainJFrame;

public class WaitingPlayersPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	public WaitingPlayersPanel(
			IView viewAdministrator, 
			MainJFrame frame) {
		this.viewAdministrator = viewAdministrator;
		this.frame = frame; 
	}
	
    private void startEnterActions() {
        frame.setEnterAction(e -> {
        	frame.clearInput();
        });
    }
			
    private void obtenerPanel() {
    	frame.appendLine("=== ESPERANDO JUGADORES ===");
        frame.appendLine("");
        frame.appendLine("Esperando jugadores...");
        frame.appendLine("");
	}
	
	@Override
    public void showPanel() {
		startEnterActions();
        obtenerPanel();
        frame.clearInput();
		frame.setEditabledInput(false);
    }
	
	@Override
	public void showErrorMessage(String message) {
		frame.appendLine(message);
	}
    
}
