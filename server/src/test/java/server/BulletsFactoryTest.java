package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.*;
import server.connection.GameController;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class BulletsFactoryTest {
	
	private GameController gameController;
	private ABullet resultBullet;
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
	}
	

	@Test
	void checkIfBulletIsATear(){
		resultBullet = BulletFactory.createBullet("Tear", 1,1,30.0f,true, gameController);
		assertThat(resultBullet).isInstanceOfAny(Tear.class);
	}
	
	@Test
	void checkIfBulletIsASpectralTear(){
		resultBullet = BulletFactory.createBullet("SpectralTear", 1,1,30.0f,true, gameController);
		assertThat(resultBullet).isInstanceOfAny(SpectralTear.class);
	}
	
	@Test
	void checkIfBulletIsAFireRing(){
		resultBullet = BulletFactory.createBullet("FireRing", 1,1,30.0f,true, gameController);
		assertThat(resultBullet).isInstanceOfAny(FireRing.class);
	}
	
	@Test
	void checkIfIllegalArgumentExceptionExceptionIsThrown(){
		assertThatIllegalArgumentException().isThrownBy(() ->{
			BulletFactory.createBullet("ShotGun", 1,1,30.0f,true, gameController);
		}).withMessage("There is no such type of a bullet!");
	}
	
	@Test
	void checkIfNullPointerExceptionIsThrown(){
		assertThatNullPointerException().isThrownBy(() ->{
			BulletFactory.createBullet(null, 1,1,30.0f,true, gameController);
		});
	}
	
	
	@AfterEach
	void tearDown() {
		gameController = null;
		resultBullet = null;
	}
	
}
