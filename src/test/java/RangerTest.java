import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class RangerTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void allSightings() {
    Ranger testRanger = new Ranger("Colin");
    testRanger.save();
    Sighting firstSighting = new Sighting("There", testRanger.getId(), "11:11");
    Sighting secondSighting = new Sighting("Here", testRanger.getId(), "11:12");
    Sighting thirdSighting = new Sighting("Outside", testRanger.getId(), "11:13");
    firstSighting.save();
    secondSighting.save();
    thirdSighting.save();
    assertEquals(testRanger.allSightings().size(), 3);
  }


}
