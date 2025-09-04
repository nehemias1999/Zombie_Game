package com.zombie.view.console.panels;

import java.util.List;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.model.dto.PlayerDTO;
import com.zombie.view.console.MainJFrame;


public class LoadedPlayersNamesPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	private List<String> playersNames;
	
	public LoadedPlayersNamesPanel(
			IView administradorVista, 
			MainJFrame frame) {
		this.viewAdministrator = administradorVista;
		this.frame = frame;    
	}
	
    private void getPanelData() {
    	playersNames = viewAdministrator.getPlayersNames();
    }
		
    private void startEnterActions() {
        frame.setEnterAction(e -> {  
            viewAdministrator.startRound(); 
        });
    }
        
    private void getPanel() {
    	int playerIndex = 1;
    	frame.appendLine("=== LOADED PLAYERS ===");
        frame.appendLine("");
        frame.appendLine("");
        for(String playerName: playersNames) {
        	frame.appendLine("Player " + playerIndex + ": " + playerName);
        	playerIndex ++;
        }
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Press Enter to start the game:");
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
