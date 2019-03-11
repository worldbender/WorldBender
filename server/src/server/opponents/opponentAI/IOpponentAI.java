package server.opponents.opponentAI;

public interface IOpponentAI {
    void behave(double deltaTime);
    boolean shouldChangeAI();
    void changeAI();
    void setAdditionalFlag(boolean additionalFlag);
    boolean getAdditionalFlag();
}
