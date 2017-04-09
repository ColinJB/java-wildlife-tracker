import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class Sighting {
  private int id;
  private int ranger_id;
  private String location;
  private Timestamp timestamp;
  private String date;
  private Animal[] animals;

  public Sighting(String location, String date, int ranger_id, Animal... animals) {
    this.location = location;
    this.ranger_id = ranger_id;
    this.date = date;
    this.animals = animals;
  }

  public Timestamp createTimestamp() {
    Timestamp timestamp = Timestamp.valueOf(this.date.replace("T"," "));
    return timestamp;
  }

  public String stampToString() {
    Date date = new Date(this.timestamp.getTime());
    DateFormat df = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
    String stringDate = df.format(date);
    return stringDate;
  }

  public Animal[] getAnimals() {
    return animals;
  }

  public int getId() {
    return id;
  }

  public String getLocation() {
    return location;
  }

  public int getRangerId() {
    return ranger_id;
  }

  public String getDate() {
    return date;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  @Override
  public boolean equals(Object otherSighting) {
    if(!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getRangerId() == (newSighting.getRangerId()) && this.getLocation().equals(newSighting.getLocation()) && this.getDate().equals(newSighting.getDate());
    }
  }

  public void save() {
    Timestamp timestamp = this.createTimestamp();
    this.timestamp = timestamp;
    String date = this.stampToString();
    this.date = date;
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (timestamp, date, location, ranger_id) VALUES (:timestamp, :date, :location, :ranger_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("timestamp", timestamp)
        .addParameter("date", date)
        .addParameter("location", this.location)
        .addParameter("ranger_id", this.ranger_id)
        .executeUpdate()
        .getKey();
      for (Animal eachAnimal : this.animals) {
        String sql2 = "INSERT INTO animals_sightings (animal_id, sighting_id) VALUES (:animal_id, :sighting_id);";
        con.createQuery(sql2)
          .addParameter("animal_id", eachAnimal.getId())
          .addParameter("sighting_id", this.id)
          .executeUpdate();
        String sql3 = "INSERT INTO animals_rangers (animal_id, ranger_id) VALUES (:animal_id, :ranger_id);";
        con.createQuery(sql3)
          .addParameter("animal_id", eachAnimal.getId())
          .addParameter("ranger_id", this.ranger_id)
          .executeUpdate();
      }
    }
  }

  public static List<Sighting> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings;";
      return con.createQuery(sql)
        .executeAndFetch(Sighting.class);
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id=:id;";
      Sighting sighting = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
      return sighting;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM sightings WHERE id=:id;";
      con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void updateLocation(String location) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE sightings SET location=:location WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("location", location)
        .executeUpdate();
    }
  }

  public List<Animal> allAnimals(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT animals.*  FROM sightings JOIN animals_sightings ON (sightings.id = animals_sightings.sighting_id) JOIN animals ON (animals_sightings.animal_id = animals.id) WHERE sightings.id =:id;";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
    }
  }

}
