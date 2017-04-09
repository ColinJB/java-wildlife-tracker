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
    Sighting testSighting = new Sighting("there", 1, "Ranger Avery");
    assertEquals(true, testSighting instanceof Sighting);
  }

  @Test
  public void equals_returnsTrueIfLocationAndDescriptionAreSame_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting("there", 1, "Ranger Avery");
    Sighting anotherSighting = new Sighting("there", 1, "Ranger Avery");
    assertTrue(testSighting.equals(anotherSighting));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Sighting() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting ("there", 1, "Ranger Avery");
    testSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting ("there", 1, "Ranger Avery");
    testSighting.save();
    EndangeredAnimal secondTestAnimal = new EndangeredAnimal("Badge", "", "");
    secondTestAnimal.save();
    Sighting secondTestSighting = new Sighting ("there", 1, "Ranger Reese");
    secondTestSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondTestSighting));
  }

  @Test
  public void find_returnsSightingWithSameId_secondSighting() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting ("there", 1, "Ranger Avery");
    testSighting.save();
    EndangeredAnimal secondTestAnimal = new EndangeredAnimal("Badger", "", "");
    secondTestAnimal.save();
    Sighting secondTestSighting = new Sighting ("there", 1, "Ranger Reese");
    secondTestSighting.save();
    assertEquals(Sighting.find(secondTestSighting.getId()), secondTestSighting);
  }

  @Test
  public void find_returnsNullWhenNoAnimalFound_null() {
    assertTrue(Animal.find(999) == null);
  }

  @Test
  public void getTimestamp_(){
    Ranger testRanger = new Ranger("Colin");
    testRanger.save();
    String time = "1985-04-12T23:20:50.52";
    Sighting testSighting = new Sighting("Here", testRanger.getId(), time);
    assertTrue(testSighting instanceof Sighting);
  }

  @Test
  public void allAnimals_returnsAnimalsOfSighting_list() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "", "");
    testAnimal.save();
    Sighting testSighting = new Sighting ("there", 1, "Ranger Avery");
    testSighting.save();
    EndangeredAnimal secondTestAnimal = new EndangeredAnimal("Badger", "", "");
    secondTestAnimal.save();
    testAnimal.addSighting(testSighting);
    secondTestAnimal.addSighting(testSighting);
    System.out.println(testSighting.allAnimals());
    assertEquals(Sighting.find(testSighting.getId()).allAnimals().size(), 2);
  }

}
