package server.LogicMap;

public class EventBlock extends ABlock{
    private int x;
    private int y;
    private String key;
    private String value;
    private boolean happened;
    private boolean active = false;
    public EventBlock(int x, int y, String key, String value){
        super(x, y, 100, 100);
        this.x = x;
        this.y = y;
        this.key = key;
        this.value = value;
        this.happened = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isHappened() {
        return happened;
    }

    public void setHappened(boolean happened) {
        this.happened = happened;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
