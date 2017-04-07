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
  public void updateName_updatesAnimalNameInDatabase_String() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Deer","","");
    testAnimal.save();
    testAnimal.updateName("Buck");
    assertEquals("Buck", Animal.allAnimals().get(0).getName());
  }

  @Test
  public void find_returnsNullWhenNoAnimalFound_null() {
    assertTrue(Animal.find(999) == null);
  }

}
