import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class SightingTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void sighting_instantiatesCorrectly_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52",1);
    assertEquals(true, testSighting instanceof Sighting);
  }

  @Test
  public void equals_returnsTrueIfLocationAndDescriptionAreSame_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52",1);
    Sighting testSighting2 = new Sighting(1,"1985-04-12T23:20:50.52",1);
    assertTrue(testSighting.equals(testSighting2));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Sighting() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52",1, testAnimal);
    testSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52",1);
    testSighting.save();
    EndangeredAnimal secondTestAnimal = new EndangeredAnimal("Badge", "", "");
    secondTestAnimal.save();
    Sighting secondTestSighting = new Sighting(1,"1985-04-12T23:20:50.52",1);
    secondTestSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondTestSighting));
  }

  @Test
  public void find_returnsSightingWithSameId_secondSighting() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52",1);
    testSighting.save();
    EndangeredAnimal secondTestAnimal = new EndangeredAnimal("Badger", "", "");
    secondTestAnimal.save();
    Sighting secondTestSighting = new Sighting(1,"1985-04-12T23:20:50.52",1);
    secondTestSighting.save();
    assertEquals(Sighting.find(secondTestSighting.getId()), secondTestSighting);
  }

  @Test
  public void find_returnsNullWhenNoAnimalFound_null() {
    assertTrue(Animal.find(999) == null);
  }

  @Test
  public void allAnimals_returnsAnimalsOfSighting_list() {
    Ranger testRanger = new Ranger("Colin","","",1);
    testRanger.save();
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    EndangeredAnimal secondTestAnimal = new EndangeredAnimal("Badger", "", "");
    secondTestAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52", testRanger.getId(), testAnimal, secondTestAnimal);
    testSighting.save();
    Sighting testSighting2 = new Sighting(1,"1986-04-12T23:20:50.52", testRanger.getId(), testAnimal, secondTestAnimal);
    testSighting2.save();
    System.out.println(testSighting.allAnimals());
    assertEquals(Sighting.find(testSighting.getId()).allAnimals().size(), 2);
  }

  @Test
  public void getTimestamp_(){
    Ranger testRanger = new Ranger("Colin","","",1);
    testRanger.save();
    String time = "1985-04-12T23:20:50.52";
    Sighting testSighting = new Sighting(1, time, testRanger.getId());
    testSighting.save();
    assertTrue(testSighting instanceof Sighting);
  }

  @Test
  public void orderedList_returnsListOfSightingsInOrderFromMostRecent_list() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52", 1);
    testSighting.save();
    Sighting secondTestSighting = new Sighting(1,"1986-04-12T23:20:50.52", 1);
    Sighting thirdTestSighting = new Sighting(1,"1986-05-12T23:20:50.52", 1);
    Sighting fourthTestSighting = new Sighting(1,"1986-05-15T23:20:50.52", 1);
    Sighting fifthTestSighting = new Sighting(1,"1986-05-15T13:20:50.52", 1);
    Sighting sixthTestSighting = new Sighting(1,"1986-05-15T13:10:50.52", 1);
    Sighting seventhTestSighting = new Sighting(1,"1986-05-15T13:10:55.52", 1);
    Sighting eigthTestSighting = new Sighting(1,"1986-05-15T13:10:50.56", 1);
    secondTestSighting.save();
    thirdTestSighting.save();
    fourthTestSighting.save();
    fifthTestSighting.save();
    sixthTestSighting.save();
    seventhTestSighting.save();
    eigthTestSighting.save();
    assertEquals(Sighting.orderedList().size(), 8);
  }

}
