package gameclub.service;

import gameclub.domain.Player;
import org.springframework.stereotype.Service;

@Service
public class IdentityManager {

    public Player currentPLayer;

    public Player getCurrentPLayer() {
        return currentPLayer;
    }

    public void setCurrentPLayer(Player currentPLayer) {
        this.currentPLayer = currentPLayer;
    }
}
