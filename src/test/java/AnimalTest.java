import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void equals_returnsTrueIfTagIsTheSame_false() {
    EndangeredAnimal firstAnimal = new EndangeredAnimal("Deer","","");
    EndangeredAnimal anotherAnimal = new EndangeredAnimal("Deer","","");
    assertTrue(firstAnimal.equals(anotherAnimal));
  }

  @Test
  public void save_assignsIdToObjectAndSavesObjectToDatabase() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer", "Healthy", "Young");
    testAnimal.save();
    EndangeredAnimal savedAnimal = EndangeredAnimal.all().get(0);
    assertEquals(testAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_false() {
    EndangeredAnimal firstAnimal = new EndangeredAnimal("Deer","","");
    firstAnimal.save();
    EndangeredAnimal secondAnimal = new EndangeredAnimal("Black Bear","","");
    secondAnimal.save();
    assertEquals(true, Animal.allAnimals().get(0).equals(firstAnimal));
    assertEquals(true, Animal.allAnimals().get(1).equals(secondAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    EndangeredAnimal firstAnimal = new EndangeredAnimal("Deer", "","");
    firstAnimal.save();
    EndangeredAnimal secondAnimal = new EndangeredAnimal("Black Bear","","");
    secondAnimal.save();
    assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  }

  @Test
  public void delete_deletesAnimalFromDatabase_0() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer","","");
    testAnimal.save();
    testAnimal.delete();
    assertEquals(0, Animal.allAnimals().size());
  }

  @Test
  public void find_returnsNullWhenNoAnimalFound_null() {
    assertTrue(Animal.find(999) == null);
  }

  @Test
  public void updateInfo() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Marty","Cat","Mammal");
    testAnimal.save();
    testAnimal.updateInfo("species", "Maned Wolf");
    testAnimal.updateInfo("type", "Canid");
    EndangeredAnimal savedAnimal = EndangeredAnimal.all().get(0);
    assertEquals(savedAnimal.getType(), "Canid");
    assertEquals(savedAnimal.getSpecies(), "Maned Wolf");
  }

  @Test
  public void allRangers_() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer","Cat","Mammal");
    testAnimal.save();
    Ranger testRanger = new Ranger("Colin", "", "",1);
    testRanger.save();
    Ranger testRanger2 = new Ranger("Grace", "", "",1);
    testRanger2.save();
    Sighting testSighting = new Sighting(1,"1985-04-12T23:20:50.52", testRanger.getId(), testAnimal);
    Sighting testSighting2 = new Sighting(1,"1985-04-12T23:20:50.52", testRanger2.getId(), testAnimal);
    testSighting.save();
    testSighting2.save();
    EndangeredAnimal savedAnimal = EndangeredAnimal.all().get(0);
    System.out.println(testAnimal.allRangers());
    assertEquals(testAnimal.allRangers().size(), 2);
  }

  @Test
  public void allSightings_() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer","Cat","Mammal");
    testAnimal.save();
    Ranger testRanger = new Ranger("Colin", "", "",1);
    testRanger.save();
    Sighting testSighting = new Sighting(1,"1987-04-12T23:20:50.52", testRanger.getId(), testAnimal);
    Sighting testSighting2 = new Sighting(1,"1985-04-12T23:20:52.52", testRanger.getId(), testAnimal);
    Sighting testSighting3 = new Sighting(1,"1986-04-12T23:20:52.52", testRanger.getId(), testAnimal);
    testSighting.save();
    testSighting2.save();
    testSighting3.save();
    EndangeredAnimal savedAnimal = EndangeredAnimal.all().get(0);
    List<Integer> ids = new ArrayList<Integer>();
    for (Sighting each : savedAnimal.allSightings()) {
      int id = each.getId();
      ids.add(id);
    }
    System.out.println(ids);
    assertEquals(savedAnimal.allSightings().size(), 3);
  }
}
