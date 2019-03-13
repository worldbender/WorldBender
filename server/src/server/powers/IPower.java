package server.powers;

public interface IPower {
    void act(double deltaTime);
    boolean canAct();
}
