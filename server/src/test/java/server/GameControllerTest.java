package server;

import com.badlogic.gdx.utils.Json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.ABullet;
import server.bullets.Tear;
import server.connection.GameController;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
	GameController gameController;
	
	@BeforeEach
	void setUp(){
		gameController = mock(GameController.class);
		JSONArray bulletsList = new JSONArray();
		
		for (int i=0;i<3;i++) {
			JSONObject bulletData = new JSONObject()
					.put("id", i)
					.put("x", (double) i)
					.put("y", (double) i);
			
			bulletsList.put(bulletData);
		}
		when(gameController.getBulletsData()).thenReturn(bulletsList);
		
	}
	
	@Test
	void checkBulletsData(){
		Tear tear0 = mock(Tear.class);
		when(tear0.getId()).thenReturn(0);
		when(tear0.getX()).thenReturn((double) 0.0);
		when(tear0.getY()).thenReturn((double) 0.0);
		
		Tear tear1 = mock(Tear.class);
		when(tear1.getId()).thenReturn(1);
		when(tear1.getX()).thenReturn((double) 1.0);
		when(tear1.getY()).thenReturn((double) 1.0);
		
		Tear tear2 = mock(Tear.class);
		when(tear2.getId()).thenReturn(2);
		when(tear2.getX()).thenReturn((double) 2.0);
		when(tear2.getY()).thenReturn((double) 2.0);
		
		CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<>();
		bullets.add(tear0);
		bullets.add(tear1);
		bullets.add(tear2);
		
		JSONArray bulletsList = new JSONArray();
		
		for (ABullet bullet : bullets) {
			JSONObject bulletData = new JSONObject()
					.put("id", bullet.getId())
					.put("x", bullet.getX())
					.put("y", bullet.getY());
			
			bulletsList.put(bulletData);
		}
		
		
		
		assertThat(bulletsList, is(equalTo(gameController.getBulletsData())));
		
	
	}
	
}
