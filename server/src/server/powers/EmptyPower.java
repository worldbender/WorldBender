package server.powers;

public class EmptyPower extends APower{
    @Override
    public void act(double deltaTime) {

    }

    @Override
    public boolean canAct() {
        return false;
    }
}
