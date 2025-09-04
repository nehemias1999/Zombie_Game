package com.zombie.view.console.panels;

import java.util.List;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.model.dto.PlayerDTO;
import com.zombie.view.console.MainJFrame;

public class PersistedGamePlayersNamesPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	private List<PlayerDTO> persistedGamePlayers;
	private int playersQuantity;
	
	public PersistedGamePlayersNamesPanel(
			IView viewAdministrator, 
			MainJFrame frame) {
		this.viewAdministrator = viewAdministrator;
		this.frame = frame;    
	}
	
    private void obtenerDatosPanel() {
    	persistedGamePlayers = viewAdministrator.getPersistedGamePlayers();
    	playersQuantity = persistedGamePlayers.size();
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
					
					if((opcion >= 1) && (opcion <= playersQuantity)) {
						viewAdministrator.getDataPersistedGamePlayers(persistedGamePlayers.get(opcion - 1).getId());
					} else {
						viewAdministrator.showErrorMessage("Invalid option. Choose between 1 and " + playersQuantity);
					}		
		                
				} catch (NumberFormatException ex) {
					viewAdministrator.showErrorMessage("Invalid format: enter a number.");
				}
	             
			}

		frame.clearInput();
	        
		});
	}
        
    private void getPanel() {
    	int playerIndex = 1;
    	frame.appendLine("=== PAUSED GAME PLAYERS NAMES ===");
        frame.appendLine("");
        frame.appendLine("");
        for(PlayerDTO player: persistedGamePlayers) {
        	frame.appendLine("Player " + playerIndex + " : " + player.getName());
        	playerIndex ++;
        }
        frame.appendLine("");
        frame.appendLine("");
		frame.appendLine("Choose the player to recover (Between 1 and " + playersQuantity + "):");
	}
    
	@Override
    public void showPanel() {
		obtenerDatosPanel();
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
