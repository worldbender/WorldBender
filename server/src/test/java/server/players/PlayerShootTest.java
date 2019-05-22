package server.players;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.helpers.MockingConfigFileCreation;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerShootTest {
	
	private User user;
	private Player player;
	private GameController gameController;
	private CopyOnWriteArrayList <User> users;
	private BulletList bulletList;
	
	
	@BeforeEach
	void setUp() {
		user = mock(User.class);
		users = new CopyOnWriteArrayList<User>();
		bulletList = new BulletList(users);
		MockingConfigFileCreation.mockingConfigFileCreation(user);
		gameController = mock(GameController.class);
		player = new Player(user, gameController);
		player.setBulletList(bulletList);
		player.setWeaponType("Normal");
		
	}
	
	
	//Checking shoot angle
	@Test
	void checkIfAngleIsProperWhenDownArrowHasBeenClicked(){
		player.DOWN_ARROW = true;
		float expected = (float)(3 * Math.PI/2);
		player.shoot();
		float actual = (float) player.getBulletList().getOneBullet(0).getAngle();
		assertThat(actual).isEqualTo(expected);
	}
	
	
	@Test
	void checkIfAngleIsProperWhenUpArrowHasBeenClicked(){
		player.UP_ARROW = true;
		float expected = (float)Math.PI/2;
		player.shoot();
		float actual = (float) player.getBulletList().getOneBullet(0).getAngle();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfAngleIsProperWhenLeftArrowHasBeenClicked(){
		player.LEFT_ARROW = true;
		float expected = (float)Math.PI;
		player.shoot();
		float actual = (float) player.getBulletList().getOneBullet(0).getAngle();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfAngleIsProperWhenRightArrowHasBeenClicked(){
		player.RIGHT_ARROW = true;
		float expected = 0f;
		player.shoot();
		float actual = (float) player.getBulletList().getOneBullet(0).getAngle();
		assertThat(actual).isEqualTo(expected);
	}
	
	//Checking ability to shoot
	
	@Test
	void checkIfPlayerCanShoot(){
		assertThat(player.canPlayerShoot()).isEqualTo(true);
	}
	
	@Test
	void checkIfPlayerCanNotShoot(){
		boolean firstShoot = player.canPlayerShoot();
		assertThat(player.canPlayerShoot()).isEqualTo(false);
	}
	
	
	@AfterEach
	void tearDown() {
		user = null;
		player = null;
		gameController = null;
		bulletList = null;
		users = null;
	}
}
