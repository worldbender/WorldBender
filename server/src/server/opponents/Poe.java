package server.opponents;

import server.connection.GameController;

public class Poe extends AOpponent{
    public Poe(GameController gameController){
        super(gameController);
        this.setType("Poe");
        this.setWidth(60);
        this.setHeight(75);
        this.setHp(100);
        this.setBulletType("SpectralTear");
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        this.chasePlayer(deltaTime);
        this.handleOpponentShoot();
        this.choosePlayerToChaseIfTimeComes();
    }
    @Override
    protected void handleOpponentDeath(){
        super.handleOpponentDeath();
        this.dropRandomPickup();
    }
}
