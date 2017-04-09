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
  public void equals_returnsTrueIfNameIsTheSame_false() {
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
  public void allSightings_() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer","Cat","Mammal");
    testAnimal.save();
    Ranger testRanger = new Ranger("Colin");
    testRanger.save();
    Sighting testSighting = new Sighting("Here", testRanger.getId(), "11:11");
    testSighting.save();
    Sighting testSighting2 = new Sighting("Here", testRanger.getId(), "11:11");
    testSighting2.save();
    testAnimal.addSighting(testSighting);
    testAnimal.addSighting(testSighting2);
    EndangeredAnimal savedAnimal = EndangeredAnimal.all().get(0);
    System.out.println(testAnimal.allSightings());
    assertEquals(testAnimal.allSightings().size(), 2);
  }

}
