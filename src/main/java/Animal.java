import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Animal {
  public int id;
  public String name;
  public String type;
  public String species;
  public boolean endangered;

  public String getName() {
    return name;
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

  public static List<Animal> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE endangered = true;";
      List<EndangeredAnimal> endangered = con.createQuery(sql)
        .executeAndFetch(EndangeredAnimal.class);

      String sql2 = "SELECT * FROM animals WHERE endangered = false;";
      List<NonEndangered> nonEndangered = con.createQuery(sql2)
        .executeAndFetch(NonEndangered.class);
    }
    List<Animal> animals = new ArrayList<Animal>();
    animals.addAll(endangered);
    animals.addAll(nonEndangered);
    return animals;
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
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET name=:name WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("name", name)
        .executeUpdate();
    }
  }


  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE animal_id=:id;";
        return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Sighting.class);
    }
  }

}
