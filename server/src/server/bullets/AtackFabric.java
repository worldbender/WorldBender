package server.bullets;

import server.players.Player;
import server.connection.GameController;

public class AtackFabric {
    public static void createAtack(Player player, BulletList bulletList, float angle, GameController gameController){
        ABullet newBullet;
        switch (player.getWeaponType()) {
            case "Normal":
                newBullet= BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX(),
                        player.getCenterY(),
                        angle,
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                break;
            case "Triple":
                newBullet = BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX() + (int)(Math.cos(angle + (Math.PI/4.0))*20.0),
                        player.getCenterY() + (int)(Math.sin(angle + (Math.PI/4.0))*20.0),
                        angle,
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                newBullet = BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX() + (int)(Math.cos(angle)*20.0),
                        player.getCenterY() + (int)(Math.sin(angle)*20.0),
                        angle,
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                newBullet = BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX() + (int)(Math.cos(angle + (-Math.PI/4.0))*20.0),
                        player.getCenterY() + (int)(Math.sin(angle + (-Math.PI/4.0))*20.0),
                        angle,
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                break;
            case "SadOnion":
                newBullet = BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX() + (int)(Math.cos(angle + (Math.PI/4.0))*20.0),
                        player.getCenterY() + (int)(Math.sin(angle + (Math.PI/4.0))*20.0),
                        angle + (float)(Math.PI/6.0),
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                newBullet = BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX() + (int)(Math.cos(angle)*20.0),
                        player.getCenterY() + (int)(Math.sin(angle)*20.0),
                        angle,
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                newBullet = BulletFabric.createBullet(
                        player.getBulletType(),
                        player.getCenterX() + (int)(Math.cos(angle + (-Math.PI/4.0))*20.0),
                        player.getCenterY() + (int)(Math.sin(angle + (-Math.PI/4.0))*20.0),
                        angle + (float)(-Math.PI/6.0),
                        false,
                        gameController
                );
                bulletList.addBullet(newBullet);
                break;
        }
    }
}
