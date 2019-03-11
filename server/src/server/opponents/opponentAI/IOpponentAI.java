package server.opponents.opponentAI;

public interface IOpponentAI {
    public abstract void behave(double deltaTime);
    public abstract boolean shouldChangeAI();
    public abstract void changeAI();
}
