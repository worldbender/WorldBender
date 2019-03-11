package server.opponents.opponentAI;

import server.User;
import server.connection.GameController;
import server.opponents.AOpponent;

import java.awt.*;

public class ChaserAI extends AOpponentAI {

    public ChaserAI(AOpponent opponent, GameController gameController, boolean isChangeable) {
        super(opponent, gameController, isChangeable);
    }

    @Override
    public void behave(double deltaTime){
        if(this.isChangeable() && this.shouldChangeAI()){
            this.changeAI();
        }
        chasePlayer(deltaTime);
        choosePlayerToChaseIfTimeComes();
    }

    @Override
    public boolean shouldChangeAI(){
        if(opponent.getIdOfChasedPlayer().equals("") && !this.isPlayerInRange()){
            return true;
        }
        return false;
    }

    @Override
    public void changeAI(){
        opponent.setOpponentAI(new IdlerAI(opponent, gameController, true));
    }

    private void chasePlayer(double deltaTime){
        float angle;
        double newX;
        double newY;
        for (User user : this.usersInRoom) {
            if(user.getName().equals(opponent.getIdOfChasedPlayer())){
                angle = (float) (Math.atan2(user.getPlayer().getCenterY() - opponent.getCenterY(), opponent.getCenterX() - user.getPlayer().getCenterX()));
                newX = opponent.getX() + (deltaTime * Math.cos(-angle + (float) Math.PI) * opponent.getSpeed());
                newY = opponent.getY() + (deltaTime * Math.sin(-angle + (float) Math.PI) * opponent.getSpeed());
                Rectangle newPosRectangle = new Rectangle((int)newX, (int)newY, opponent.getWidth(), opponent.getHeight());
                if(!opponent.isOpponentCollidesWithMap(newPosRectangle) && !opponent.isOpponentCollidesWithOpponents(newPosRectangle)){
                    opponent.setX(newX);
                    opponent.setY(newY);
                }
            }
        }
    }

    private void choosePlayerToChaseIfTimeComes(){
        double distance;
        if(opponent.shouldOpponentChangeChaseTarget()){
            opponent.setIdOfChasedPlayer("");
            double savedDistance = Float.POSITIVE_INFINITY;
            for (User user : this.usersInRoom) {
                distance = Math.sqrt((Math.abs(user.getPlayer().getCenterY() - opponent.getCenterY())) * (Math.abs(user.getPlayer().getCenterY() - opponent.getCenterY())) +
                        (Math.abs(opponent.getCenterX() - user.getPlayer().getCenterX()) * (Math.abs(opponent.getCenterX() - user.getPlayer().getCenterX()))));
                if (distance < opponent.getViewRange() && distance < savedDistance) {
                    opponent.setIdOfChasedPlayer(user.getName());
                    savedDistance = distance;
                }
            }
        }
    }

    private boolean isPlayerInRange(){
        double distance;
        for (User user : this.usersInRoom) {
            distance = Math.sqrt((Math.abs(user.getPlayer().getCenterY() - opponent.getCenterY())) * (Math.abs(user.getPlayer().getCenterY() - opponent.getCenterY())) +
                    (Math.abs(opponent.getCenterX() - user.getPlayer().getCenterX()) * (Math.abs(opponent.getCenterX() - user.getPlayer().getCenterX()))));
            if (distance < opponent.getViewRange()) {
                return true;
            }
        }
        return false;
    }
}
