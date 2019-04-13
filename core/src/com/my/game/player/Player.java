package com.my.game.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.my.game.Properties;
import com.my.game.screens.GameplayScreen;
import com.my.game.utilitysingletons.StringDrawer;
import org.json.JSONObject;


public abstract class Player extends APlayer {
    public static Animation<TextureRegion> downWalkAnimation;
    public static Animation<TextureRegion> upWalkAnimation;
    public static Animation<TextureRegion> rightWalkAnimation;
    public static Animation<TextureRegion> leftWalkAnimation;
    private String activeMovementKey = "DOWN";
    private String headDirection = "DOWN";
    public static TextureRegion headRegion;
    private boolean isMoving = false;
    public boolean KEY_W = false;
    public boolean KEY_S = true;
    public boolean KEY_A = false;
    public boolean KEY_D = false;
    public boolean UP_ARROW = false;
    public boolean DOWN_ARROW = false;
    public boolean LEFT_ARROW = false;
    public boolean RIGHT_ARROW = false;
    public boolean KEY_SPACE = false;
    private static final String RIGHT = "RIGHT";
    private static final String LEFT = "LEFT";
    private static final String UP = "UP";
    private static final String DOWN = "DOWN";

    public Player(String name) {
        this(name, 0, 0);
    }

    public Player(String name, int x, int y) {
        super();
        this.name = name;
        float scale = java.lang.Float.parseFloat(Properties.loadConfigFile("PLAYER_SCALE"));
        this.setSize((int) (GameplayScreen.PLAYER_TEXTURE_WIDTH * scale), (int) (GameplayScreen.PLAYER_TEXTURE_HEIGHT * scale));
        this.setPosition(x, y);
    }

    public void draw(SpriteBatch batch, float stateTime) {
        this.drawCharacter(batch, stateTime);
        this.drawHead(batch);
        this.drawName(batch);
    }

    private void drawCharacter(SpriteBatch batch, float stateTime) {
        if (this.isMoving) {
            this.drawMoving(batch, stateTime);
        } else {
            this.drawStanding(batch, stateTime);
        }
    }

    private void drawMoving(SpriteBatch batch, float stateTime) {
        switch (activeMovementKey) {
            case UP:
                drawAnimationCharacter(batch, upWalkAnimation.getKeyFrame(stateTime, true));
                break;
            case DOWN:
                drawAnimationCharacter(batch, downWalkAnimation.getKeyFrame(stateTime, true));
                break;
            case LEFT:
                drawAnimationCharacter(batch, leftWalkAnimation.getKeyFrame(stateTime, true));
                break;
            case RIGHT:
                drawAnimationCharacter(batch, rightWalkAnimation.getKeyFrame(stateTime, true));
                break;
            default:
                break;
        }
    }

    private void drawStanding(SpriteBatch batch, float stateTime) {
        switch (activeMovementKey) {
            case UP:
                drawAnimationCharacter(batch, upWalkAnimation.getKeyFrames()[0]);
                break;
            case DOWN:
                drawAnimationCharacter(batch, downWalkAnimation.getKeyFrames()[0]);
                break;
            case LEFT:
                drawAnimationCharacter(batch, leftWalkAnimation.getKeyFrames()[5]);
                break;
            case RIGHT:
                drawAnimationCharacter(batch, rightWalkAnimation.getKeyFrames()[5]);
                break;
            default:
                break;
        }
    }

    private void drawHead(SpriteBatch batch) {
        switch (this.headDirection) {
            case UP:
                drawAnimationCharacter(batch, this.getHeads().getKeyFrames()[2]);
                break;
            case DOWN:
                drawAnimationCharacter(batch, this.getHeads().getKeyFrames()[0]);
                break;
            case LEFT:
                drawAnimationCharacter(batch, this.getHeads().getKeyFrames()[3]);
                break;
            case RIGHT:
                drawAnimationCharacter(batch, this.getHeads().getKeyFrames()[1]);
                break;
            default:
                break;
        }
    }

    private void drawAnimationCharacter(SpriteBatch batch, TextureRegion textureRegion) {
        batch.draw(
                textureRegion,
                (int) this.getX(),
                (int) this.getY(),
                this.width,
                this.height
        );
    }

    private void drawName(SpriteBatch batch) {
        StringDrawer.drawHp(batch, this.getName(), (int) this.getX(), (int) this.getY() + (int) this.getHeight());
    }

    public JSONObject getPlayerState() {
        return new JSONObject()
                .put("isMoving", this.isMoving)
                .put("activeMovementKey", this.activeMovementKey)
                .put("headDirection", this.headDirection)
                .put("specialKeys", new JSONObject()
                        .put("space", this.KEY_SPACE)
                )
                .put("wsad", new JSONObject()
                        .put("w", this.KEY_W)
                        .put("s", this.KEY_S)
                        .put("d", this.KEY_D)
                        .put("a", this.KEY_A)
                        .put("up", this.UP_ARROW)
                        .put("down", this.DOWN_ARROW)
                        .put("left", this.LEFT_ARROW)
                        .put("right", this.RIGHT_ARROW)
                );
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public String getName() {
        return name;
    }

    public String getActiveMovementKey() {
        return activeMovementKey;
    }

    public void setActiveMovementKey(String activeMovementKey) {
        if (!activeMovementKey.equals(""))
            this.activeMovementKey = activeMovementKey;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void resetKeys() {
        this.KEY_W = false;
        this.KEY_S = false;
        this.KEY_A = false;
        this.KEY_D = false;
        this.UP_ARROW = false;
        this.DOWN_ARROW = false;
        this.LEFT_ARROW = false;
        this.RIGHT_ARROW = false;
        this.KEY_SPACE = false;
    }

    public void setHeadDirection(String headDirection) {
        this.headDirection = headDirection;
    }

    protected abstract Animation<TextureRegion> getHeads();
}
