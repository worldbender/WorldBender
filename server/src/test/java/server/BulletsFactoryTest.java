package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.BulletFactory;
import server.connection.GameController;

import static org.mockito.Mockito.mock;

public class BulletsFactoryTest {
	
	private GameController gameController;
	
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
	}
	

	@Test
	checkIfBulletIsATear(){
		BulletFactory.createBullet()
	}
	
	
	@AfterEach
	void tearDown() {
		gameController = null;
	}
	
}
