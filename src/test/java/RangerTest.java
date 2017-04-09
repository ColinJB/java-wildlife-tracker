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
    Ranger testRanger = new Ranger("Colin","","");
    testRanger.save();
    Sighting firstSighting = new Sighting("there","1985-04-12T23:20:50.52", testRanger.getId());
    Sighting secondSighting = new Sighting("there","1985-04-12T23:20:50.52", testRanger.getId());
    Sighting thirdSighting = new Sighting("there","1985-04-12T23:20:50.52", testRanger.getId());
    firstSighting.save();
    secondSighting.save();
    thirdSighting.save();
    assertEquals(testRanger.allSightings().size(), 3);
  }

  @Test
  public void allAnimals() {
    Ranger testRanger = new Ranger("Colin","","");
    testRanger.save();
    EndangeredAnimal firstAnimal = new EndangeredAnimal("Deer","","");
    EndangeredAnimal secondAnimal = new EndangeredAnimal("Bear","","");
    EndangeredAnimal thirdAnimal = new EndangeredAnimal("Duck","","");
    Sighting firstSighting = new Sighting("there","1985-04-12T23:20:50.52", testRanger.getId(), firstAnimal, secondAnimal, thirdAnimal);
    firstAnimal.save();
    secondAnimal.save();
    thirdAnimal.save();
    firstSighting.save();
    assertEquals(testRanger.allAnimals().size(), 3);
  }
}
