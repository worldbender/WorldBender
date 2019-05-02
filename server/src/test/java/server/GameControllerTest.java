package server;


import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.ABullet;
import server.bullets.BulletList;
import server.bullets.Tear;
import server.connection.GameController;
import server.logicmap.LogicMapHandler;
import server.rooms.Room;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
	
	GameController gameController;
	
	@BeforeEach
	void setUp(){
		Room room = mock(Room.class);                                           //do konstruktora
		LogicMapHandler logicMapHandler = mock(LogicMapHandler.class);          //do konstruktora
		BulletList bulletList = mock(BulletList.class);                         //do testowanej metody
		
		CopyOnWriteArrayList<ABullet> bullets = getaBullets();                  //metoda tworząca listę przykładowych pocisków
		
		
		when(bulletList.getBullets()).thenReturn(bullets);
		when(room.getBulletList()).thenReturn(bulletList);
		
		gameController = new GameController(room, logicMapHandler);             //inicjalizuje za pomocą nowego konstruktora
																				//Można tak??????????????????????????????
		
	}
	
	
	
	@Test
	void checkBulletsData(){
		JSONArray bulletsData = gameController.getBulletsData(); //Wywołuje testowaną meodę w pramaterze przekazano jej tą samą CopyOnWriteArrayList<ABullet>
		
		CopyOnWriteArrayList<ABullet> bullets = getaBullets();  //Biorę tę samą listę i przerabiam ją na JSONArray
		
		JSONArray bulletsList = new JSONArray();
		
		for (ABullet bullet : bullets) {
			JSONObject bulletData = new JSONObject()
					.put("id", bullet.getId())
					.put("x", bullet.getX())
					.put("y", bullet.getY());
			
			bulletsList.put(bulletData);
		}
		
		
		JSONAssert.assertEquals(bulletsData, bulletsList, JSONCompareMode.STRICT); //Sprawdzam czy Jsony są takie same
		
	
	}
	
	
	/***********************************HELPETS METHODS************************************/
	
	private CopyOnWriteArrayList<ABullet> getaBullets() {
		Tear tear0 = mock(Tear.class);
		when(tear0.getId()).thenReturn(0);
		when(tear0.getX()).thenReturn(0.0);
		when(tear0.getY()).thenReturn(0.0);
		
		Tear tear1 = mock(Tear.class);
		when(tear1.getId()).thenReturn(1);
		when(tear1.getX()).thenReturn(1.0);
		when(tear1.getY()).thenReturn(1.0);
		
		Tear tear2 = mock(Tear.class);
		when(tear2.getId()).thenReturn(2);
		when(tear2.getX()).thenReturn(2.0);
		when(tear2.getY()).thenReturn(2.0);
		
		CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<>();
		bullets.add(tear0);
		bullets.add(tear1);
		bullets.add(tear2);
		return bullets;
	}
	
}
