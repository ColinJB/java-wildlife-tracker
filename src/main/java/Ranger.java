import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Ranger {
  private int id;
  private String name;
  private String phone;
  private String badge;

  public Ranger(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public String getBadge() {
    return badge;
  }

  @Override
  public boolean equals(Object otherRanger) {
    if(!(otherRanger instanceof Ranger)) {
      return false;
    } else {
      Ranger newRanger = (Ranger) otherRanger;
      return this.getName().equals(newRanger.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rangers (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Ranger> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers;";
      return con.createQuery(sql)
        .executeAndFetch(Ranger.class);
    }
  }

  public static Ranger find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE id=:id;";
      Ranger ranger = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ranger.class);
      return ranger;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  public List<Sighting> allSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE ranger_id = :ranger_id;";
      return con.createQuery(sql)
        .addParameter("ranger_id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public void addAnimal(Animal animal){
    try (Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO animals_rangers (animal_id, ranger_id) VALUES (:animal_id, :ranger_id);";
      con.createQuery(sql)
        .addParameter("animal_id", animal.getId())
        .addParameter("ranger_id", this.id)
        .executeUpdate();
    }
  }

  public List<Animal> allAnimals(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT animals.*  FROM rangers JOIN animals_rangers ON (rangers.id = animals_rangers.ranger_id) JOIN animals ON (animals_rangers.animal_id = animals.id) WHERE rangers.id =:id;";
    return con.createQuery(sql)
      .addParameter("id", this.getId())
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
    }
  }



}
