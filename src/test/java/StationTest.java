import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class StationTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getId_() {
    Station testStation1 = new Station("Station1");
    testStation1.save();
    assertTrue(testStation1.getId() > 0);
  }

  @Test
  public void orderedList_returnsListOfSightingsInOrderFromMostRecent_list() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Station testStation1 = new Station("Station1");
    Station testStation2 = new Station("Station2");
    testStation2.save();
    testStation1.save();
    Location testLocation1 = new Location("Location1", testStation1.getId());
    Location testLocation2 = new Location("Location2", testStation2.getId());
    Location testLocation3 = new Location("Location3", testStation1.getId());
    Location testLocation4 = new Location("Location4", testStation2.getId());
    testLocation4.save();
    testLocation3.save();
    testLocation2.save();
    testLocation1.save();
    Sighting testSighting = new Sighting(testLocation1.getId(),"1985-04-12T23:20:50.52", 1);
    Sighting secondTestSighting = new Sighting(testLocation1.getId(),"1986-04-12T23:20:50.52", 1);
    Sighting thirdTestSighting = new Sighting(testLocation1.getId(),"1986-05-12T23:20:50.52", 1);
    Sighting fourthTestSighting = new Sighting(testLocation2.getId(),"1986-05-15T23:20:50.52", 1);
    Sighting fifthTestSighting = new Sighting(testLocation3.getId(),"1986-05-15T13:20:50.52", 1);
    Sighting sixthTestSighting = new Sighting(testLocation2.getId(),"1986-05-15T13:10:50.52", 1);
    Sighting seventhTestSighting = new Sighting(testLocation3.getId(),"1986-05-15T13:10:55.52", 1);
    Sighting eigthTestSighting = new Sighting(testLocation2.getId(),"1986-05-15T13:10:50.56", 1);
    testSighting.save();
    secondTestSighting.save();
    thirdTestSighting.save();
    fourthTestSighting.save();
    fifthTestSighting.save();
    sixthTestSighting.save();
    seventhTestSighting.save();
    eigthTestSighting.save();
    List<Integer> ids = new ArrayList<Integer>();
    for (Sighting each : testStation1.allSightings()) {
      Integer id = each.getId();
      ids.add(id);
    }
    List<Integer> ids2 = new ArrayList<Integer>();
    for (Sighting each : testStation2.allSightings()) {
      Integer id = each.getId();
      ids2.add(id);
    }
    System.out.println(ids);
    System.out.println(ids2);
    assertEquals(testStation1.allSightings().size(), 5);
    assertEquals(testStation2.allSightings().size(), 3);
  }
}
