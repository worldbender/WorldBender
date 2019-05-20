package server.factories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.User;
import server.bullets.AttackFactory;
import server.bullets.BulletFactory;
import server.bullets.BulletList;
import server.connection.GameController;
import server.connection.TcpClientThread;
import server.helpers.MockingConfigFileCreation;
import server.players.Player;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AttackFactoryTest {
	private User user;
	private GameController gameController;
	private Player player;
	private BulletList bulletList;
	private CopyOnWriteArrayList<User> users;
	private TcpClientThread thread;
	private static final float Angle = 30f;
	
	
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
		user = mock(User.class);
		thread = mock(TcpClientThread.class);
		when(user.getThread()).thenReturn(thread);
		MockingConfigFileCreation.mockingConfigFileCreation(user);
		player = new Player(user);
		users = new CopyOnWriteArrayList<User>();
		users.add(user);
		bulletList = new BulletList(users);
	}
	
	
	//Normal
	@Test
	void checkIfNormalBulletHasBeenAdded() {
		int before = bulletList.getSize();
		player.setWeaponType("Normal");
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int after = bulletList.getSize();
		assertThat(after).isEqualTo(before+1);
	}
	
	@Test
	void checkIfNewXIsProperWhenBulletIsNormal() {
		player.setWeaponType("Normal");
		int expected = player.getCenterX();
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(0).getX();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewYIsProperWhenBulletIsNormal() {
		player.setWeaponType("Normal");
		int expected = player.getCenterY();
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(0).getY();
		assertThat(actual).isEqualTo(expected);
	}
	
	//Triple
	@Test
	void checkIfThreeBulletHasBeenAdded() {
		int before = bulletList.getSize();
		player.setWeaponType("Triple");
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int after = bulletList.getSize();
		assertThat(after).isEqualTo(before+3);
		
	}
	
	@Test
	void checkIfNewXInTheFirstBulletIsProperWhenBulletIsTriple() {
		player.setWeaponType("Triple");
		int expected = player.getCenterX() + (int)(Math.cos(Angle + (Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(0).getX();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewXInTheSecondBulletIsProperWhenBulletIsTriple() {
		player.setWeaponType("Triple");
		int expected = player.getCenterX() + (int)(Math.cos(Angle)*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(1).getX();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewXInTheThirdBulletIsProperWhenBulletIsTriple() {
		player.setWeaponType("Triple");
		int expected = player.getCenterX() + (int)(Math.cos(Angle + (-Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(2).getX();
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	void checkIfNewYInTheFirstBulletIsProperWhenBulletIsTriple() {
		player.setWeaponType("Triple");
		int expected = player.getCenterY() + (int)(Math.sin(Angle + (Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(0).getY();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewYInTheSecondBulletIsProperWhenBulletIsTriple() {
		player.setWeaponType("Triple");
		int expected = player.getCenterY() + (int)(Math.sin(Angle)*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(1).getY();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewYInTheThirdBulletIsProperWhenBulletIsTriple() {
		player.setWeaponType("Triple");
		int expected = player.getCenterY() + (int)(Math.sin(Angle + (-Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(2).getY();
		assertThat(actual).isEqualTo(expected);
		
	}
	
	//SadOnion
	@Test
	void checkIfThreeBulletHasBeenAddedWhenBulletIsSadOnion() {
		int before = bulletList.getSize();
		player.setWeaponType("SadOnion");
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int after = bulletList.getSize();
		assertThat(after).isEqualTo(before+3);
		
	}
	
	@Test
	void checkIfNewXInTheFirstBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		int expected = player.getCenterX() + (int)(Math.cos(Angle + (Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(0).getX();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewXInTheSecondBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		int expected = player.getCenterX() + (int)(Math.cos(Angle)*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(1).getX();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewXInTheThirdBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		int expected = player.getCenterX() + (int)(Math.cos(Angle + (-Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(2).getX();
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	void checkIfNewYInTheFirstBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		int expected = player.getCenterY() + (int)(Math.sin(Angle + (Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(0).getY();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewYInTheSecondBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		int expected = player.getCenterY() + (int)(Math.sin(Angle)*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(1).getY();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewYInTheThirdBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		int expected = player.getCenterY() + (int)(Math.sin(Angle + (-Math.PI/4.0))*20.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		int actual = (int) bulletList.getOneBullet(2).getY();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewAngleInTheFirstBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		float expected = Angle + (float)(Math.PI/6.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		float actual = (float)bulletList.getOneBullet(0).getAngle();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void checkIfNewAngleInTheThirdBulletIsProperWhenBulletIsSadOnion() {
		player.setWeaponType("SadOnion");
		float expected = Angle + (float)(-Math.PI/6.0);
		AttackFactory.createAttack(player, bulletList,Angle, gameController);
		float actual = (float)bulletList.getOneBullet(2).getAngle();
		assertThat(actual).isEqualTo(expected);
	}
	
	
	//Exceptions
	@Test
	void checkIfIllegalArgumentExceptionExceptionIsThrown(){
		player.setWeaponType("SadCelery");
		assertThatIllegalArgumentException().isThrownBy(() ->{
			AttackFactory.createAttack(player, bulletList,Angle, gameController);
		}).withMessage("There is no such type of a bullet!");
	}
	
	@Test
	void checkIfNullPointerExceptionIsThrown(){
		player.setWeaponType(null);
		assertThatNullPointerException().isThrownBy(() ->{
			BulletFactory.createBullet(null, 1,1,30.0f,true, gameController);
		});
	}
	
	
	@AfterEach
	void tearDown() {
		gameController = null;
		user = null;
		player = null;
		bulletList = null;
		thread = null;
	}
	
}