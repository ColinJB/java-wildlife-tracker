import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class LocationTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getStationId_() {
    Station testStation1 = new Station("Station1");
    testStation1.save();
    Location testLocation = new Location("Here", testStation1.getId());
    testLocation.save();
    assertEquals(testLocation.getStationId(), testStation1.getId());
  }

  @Test
  public void allAnimals_returnsAllAnimalsInLocationInOrder_list() {
    EndangeredAnimal firstAnimal = new EndangeredAnimal("","Deer","");
    firstAnimal.save();
    EndangeredAnimal secondAnimal = new EndangeredAnimal("","Black Bear","");
    secondAnimal.save();
    EndangeredAnimal thirdAnimal = new EndangeredAnimal("","Dog","");
    thirdAnimal.save();
    Station station = new Station("Base");
    station.save();
    Ranger ranger = new Ranger("Colin","","",station.getId());
    ranger.save();
    Location location = new Location("Woods", station.getId());
    location.save();
    Sighting testSighting = new Sighting(location.getId(),"1985-04-12T23:20:50.52",ranger.getId(), firstAnimal, secondAnimal, thirdAnimal);
    testSighting.save();
    List<String> animals = new ArrayList<String>();
    for (Animal each : location.allAnimals()) {
      String animalName = each.getSpecies();
      animals.add(animalName);
    }
    System.out.println(animals);
    assertEquals(location.allAnimals().size(), 2);
  }
}
