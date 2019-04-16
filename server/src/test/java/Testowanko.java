import org.junit.jupiter.api.Test;
import server.logicmap.EventBlock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testowanko {

    @Test
    public void testowanko(){
        EventBlock block = new EventBlock(2, 5, "napis", "napis2");
        assertEquals(2, block.getX());
    }
}
