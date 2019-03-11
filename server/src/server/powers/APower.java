package server.powers;

import java.util.Date;

public abstract class APower implements IPower {
    protected long cooldown;
    protected long lastTimePowerWasUsed;

    @Override
    abstract public void act(double deltaTime);

    @Override
    abstract public boolean canAct();

    protected boolean hasCoolDownPassed(){
        boolean result = false;
        Date date = new Date();
        long time = date.getTime();
        if (time - this.getLastTimePowerWasUsed() > this.getCooldown()) {
            result = true;
            this.setLastTimePowerWasUsed(time);
        }
        return result;
    }

    public void setCooldown(long cooldown){
        this.cooldown = cooldown;
    }

    public long getCooldown(){
        return cooldown;
    }

    public void setLastTimePowerWasUsed(long lastTimePowerWasUsed){
        this.lastTimePowerWasUsed = lastTimePowerWasUsed;
    }

    public long getLastTimePowerWasUsed(){
        return lastTimePowerWasUsed;
    }
}
