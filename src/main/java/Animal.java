import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Animal {
  public int id;
  public String name;
  public String type;
  public String species;
  public boolean endangered;
  public int count;

  public String getName() {
    return name;
  }

  public int getCount() {
    return count;
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

  public boolean endangeredStatus() {
    return endangered;
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if(!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName()) &&
             this.getSpecies().equals(newAnimal.getSpecies()) &&
             this.endangeredStatus() == newAnimal.endangeredStatus();
    }
  }

  public static List<Animal> allAnimals() {
    List<Animal> allAnimals = new ArrayList<Animal>();
    allAnimals.addAll(EndangeredAnimal.all());
    allAnimals.addAll(NonEndangered.all());
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
    String sql = "SELECT sightings.*  FROM animals JOIN animals_sightings ON (animals.id = animals_sightings.animal_id) JOIN sightings ON (animals_sightings.sighting_id = sightings.id) WHERE animals.id =:id;";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Sighting.class);
    }
  }

  public void addSighting(Sighting sighting){
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO animals_sightings (animal_id, sighting_id) VALUES (:animal_id, :sighting_id);";
      con.createQuery(sql)
        .addParameter("animal_id", this.id)
        .addParameter("sighting_id", sighting.getId())
        .executeUpdate();
      }
    count++;
  }

}
