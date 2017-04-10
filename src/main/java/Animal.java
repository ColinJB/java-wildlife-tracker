import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;

public class Animal {
  public int id;
  public String tag;
  public String type;
  public String species;
  public boolean endangered;

  public static Comparator<Animal> AnimalSpeciesComparator = new Comparator<Animal>() {
    public int compare(Animal a1, Animal a2) {
      String animalSpecies1 = a1.getSpecies().toUpperCase();
      String animalSpecies2 = a2.getSpecies().toUpperCase();
      return animalSpecies1.compareTo(animalSpecies2);
    }
  };

  public String getTag() {
    return tag;
  }

  public int getCount() {
    return this.allSightings().size();
  }

  public String getSpecies() {
    return species;
  }

  public int getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String endangeredStatus() {
    return String.valueOf(endangered).toUpperCase();
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if(!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getTag().equals(newAnimal.getTag()) &&
             this.getSpecies().equals(newAnimal.getSpecies()) &&
             this.endangeredStatus() == newAnimal.endangeredStatus();
    }
  }

  public static List<Animal> allAnimals() {
    List<Animal> allAnimals = new ArrayList<Animal>();
    allAnimals.addAll(EndangeredAnimal.all());
    allAnimals.addAll(NonEndangered.all());
    Collections.sort(allAnimals, Animal.AnimalSpeciesComparator);
    return allAnimals;
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id=:id;";
      Animal animal = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(EndangeredAnimal.class);
      return animal;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id=:id;";
      con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateInfo(String column, String value) {
    try(Connection con = DB.sql2o.open()) {
      String sql = String.format("UPDATE animals SET %s = '%s' WHERE id=:id;", column, value);
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Sighting> allSightings(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT sightings.*  FROM animals JOIN animals_sightings ON (animals.id = animals_sightings.animal_id) JOIN sightings ON (animals_sightings.sighting_id = sightings.id) WHERE animals.id =:id ORDER BY timestamp desc;";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Sighting.class);
    }
  }

  public List<Ranger> allRangers(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT rangers.*  FROM animals JOIN animals_rangers ON (animals.id = animals_rangers.animal_id) JOIN rangers ON (animals_rangers.ranger_id = rangers.id) WHERE animals.id =:id ORDER BY name asc;";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Ranger.class);
    }
  }

}
