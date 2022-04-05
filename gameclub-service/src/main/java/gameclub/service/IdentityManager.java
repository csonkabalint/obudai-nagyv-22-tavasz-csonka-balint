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

    public boolean authorizeAs(String role){
        return currentPLayer.getRoles().stream().anyMatch(r -> r.toString().equals(role.toUpperCase()));
    }

}
