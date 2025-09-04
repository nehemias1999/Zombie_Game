package com.zombie.view.console.panels;

import java.util.List;

import com.zombie.interfaces.IPanel;
import com.zombie.interfaces.IView;
import com.zombie.model.dto.CardDTO;
import com.zombie.view.console.MainJFrame;

public class SpectatorPlayerPanel implements IPanel {
	
	private IView viewAdministrator;
	private MainJFrame frame;
	
	private String actualPlayerName;
	private List<CardDTO> couplesDeck;
	private List<CardDTO> playerDeck;
	
	public SpectatorPlayerPanel(
			IView viewAdministrator, 
			MainJFrame frame) {
		this.viewAdministrator = viewAdministrator;
		this.frame = frame;
	}
	
    private void getPanelData() {
    	actualPlayerName = viewAdministrator.getActualPlayerName();
    	couplesDeck = viewAdministrator.getLastTwoCardsFromCouplesDeck();
    	playerDeck = viewAdministrator.getPlayerDeck();
    }
		
    private void startEnterActions() {
        frame.setEnterAction(e -> {
        	frame.clearInput();
        }); 
    }
        
    private void getPanel() {
    	frame.appendLine("=== SPECTATOR PLAYER ===");
        frame.appendLine("");
        frame.appendLine("Player's turn: " + actualPlayerName);
        frame.appendLine("");
        frame.appendLine("");
        if(!couplesDeck.isEmpty()) {
        	frame.appendLine("Couples deck: " + couplesDeck.get(0).toString() + ", " + couplesDeck.get(1).toString() + "");
        } else {
        	frame.appendLine("Couples deck: there're no cards");
        }
        frame.appendLine("");
        frame.appendLine("");
        frame.appendLine("Player's deck: " + playerDeck);
        frame.appendLine("");
        frame.appendLine("Wait your turn to play");
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
