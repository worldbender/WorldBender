package server.opponents.opponentai;

import server.User;
import server.connection.GameController;
import server.opponents.AOpponent;

import java.awt.*;
import java.util.Random;

public class IdlerAI extends AOpponentAI {
    private double movementVectorX;
    private double movementVectorY;
    private static final double SPEED_MODIFIER = 0.02;

    private Random randomGenerator = new Random();
    public IdlerAI(AOpponent opponent, GameController gameController, boolean isChangeable) {
        super(opponent, gameController, isChangeable);
    }

    @Override
    public void behave(double deltaTime) {
        if(this.isChangeable && this.shouldChangeAI()){
            this.changeAI();
        }
        if(this.shouldChoseNewVector()){
            choseRandomDestinationPoint();
        }
        moveTowardDestinationPoint(deltaTime);
    }

    @Override
    public boolean shouldChangeAI() {
        return this.isPlayerInRange();
    }

    @Override
    public void changeAI() {
        opponent.setOpponentAI(new ChaserAI(opponent, gameController, true));
    }

    private void moveTowardDestinationPoint(double deltaTime){
        double diffX = deltaTime * this.movementVectorX * (opponent.getSpeed() * this.SPEED_MODIFIER);
        double diffY = deltaTime * this.movementVectorY * (opponent.getSpeed() * this.SPEED_MODIFIER);
        double newX = opponent.getX() + diffX;
        double newY = opponent.getY() + diffY;
        Rectangle newPosRectangle = new Rectangle((int)newX, (int)newY, opponent.getWidth(), opponent.getHeight());
        if(!opponent.isOpponentCollidesWithMap(newPosRectangle) && !opponent.isOpponentCollidesWithOpponents(newPosRectangle)){
            opponent.setX(newX);
            opponent.setY(newY);
            this.movementVectorX -= diffX;
            this.movementVectorY -= diffY;
        }
        else{
            this.movementVectorX = 0.0;
            this.movementVectorY = 0.0;
        }
    }

    private boolean shouldChoseNewVector(){
        return Math.round(Math.abs(this.movementVectorX)) == 0.0 && Math.round(Math.abs(this.movementVectorY)) == 0.0;
    }

    private boolean isPlayerInRange(){
        double distance;
        for (User user : this.usersInRoom) {
            distance = Math.sqrt((double)(Math.abs(user.getPlayer().getCenterY() - opponent.getCenterY())) * (Math.abs(user.getPlayer().getCenterY() - opponent.getCenterY())) +
                    (Math.abs(opponent.getCenterX() - user.getPlayer().getCenterX()) * (Math.abs(opponent.getCenterX() - user.getPlayer().getCenterX()))));
            if (distance < opponent.getViewRange()) {
                return true;
            }
        }
        return false;
    }

    private void choseRandomDestinationPoint(){
        Rectangle newPointRectangle = new Rectangle();
        this.movementVectorX = 0.0;
        this.movementVectorY = 0.0;
        newPointRectangle.width = 1;
        newPointRectangle.height = 1;

        double newDestinationX;
        double newDestinationY;
        for(int i = 0; i < 10; i++){
            newDestinationX = randomGenerator.nextInt() % 30.0;
            newDestinationY = randomGenerator.nextInt() % 30.0;
            newPointRectangle.setLocation((int)(opponent.getX() + newDestinationX), (int)(opponent.getY() + newDestinationY));
            boolean isNewPointCollidesWithMap = map.isRectangleCollidesWithMap(newPointRectangle);
            if(!isNewPointCollidesWithMap){
                this.movementVectorX = newDestinationX;
                this.movementVectorY = newDestinationY;
                break;
            }
        }
    }
}
