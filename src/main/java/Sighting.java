import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class Sighting {
  private int id;
  private int ranger_id;
  private int location_id;
  private int station_id;
  private Timestamp timestamp;
  private String date;
  private Animal[] animals;

  public Sighting(int location_id, Timestamp timestamp, String date, int ranger_id, Animal... animals) {
    this.location_id = location_id;
    this.ranger_id = ranger_id;
    this.date = date;
    this.animals = animals;
    this.timestamp = timestamp;
  }

  // public Timestamp createTimestamp() {
  //   Timestamp timestamp = Timestamp.valueOf(this.date.replace("T"," ") + ":00");
  //   return timestamp;
  // }
  //
  // public String stampToString() {
  //   Date date = new Date(this.timestamp.getTime());
  //   DateFormat df = new SimpleDateFormat("E, MMM dd yyyy H:mm a");
  //   String stringDate = df.format(date);
  //   return stringDate;
  // }

  public Animal[] getAnimals() {
    return animals;
  }

  public int getId() {
    return id;
  }

  public int getLocationId() {
    return location_id;
  }

  public Location getLocation() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM locations WHERE id=:location_id;";
      return con.createQuery(sql)
        .addParameter("location_id", this.location_id)
        .executeAndFetchFirst(Location.class);
    }
  }

  public int getStationId() {
    return this.station_id;
  }

  public Station getStation() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stations WHERE id=:station_id;";
      return con.createQuery(sql)
        .addParameter("station_id", this.station_id)
        .executeAndFetchFirst(Station.class);
    }
  }

  public int getRangerId() {
    return ranger_id;
  }

  public Ranger getRanger() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE id=:ranger_id;";
      return con.createQuery(sql)
        .addParameter("ranger_id", this.ranger_id)
        .executeAndFetchFirst(Ranger.class);
    }
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
      return this.getRangerId() == (newSighting.getRangerId()) && this.getLocationId() == newSighting.getLocationId() && this.getDate().equals(newSighting.getDate());
    }
  }

  public void save() {
    // Timestamp timestamp = this.createTimestamp();
    // this.timestamp = timestamp;
    // String date = this.stampToString();
    // this.date = date;
    Location location = Location.find(this.location_id);
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (timestamp, date, location_id, station_id, ranger_id) VALUES (:timestamp, :date, :location_id, :station_id, :ranger_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("timestamp", this.timestamp)
        .addParameter("date", this.date)
        .addParameter("location_id", this.location_id)
        .addParameter("station_id", location.getStationId())
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
        String sql4 = "INSERT INTO animals_locations (animal_id, location_id) VALUES (:animal_id, :location_id);";
        con.createQuery(sql4)
          .addParameter("animal_id", eachAnimal.getId())
          .addParameter("location_id", this.location_id)
          .executeUpdate();
      }
    }
  }

  public static List<Sighting> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings ORDER BY timestamp desc;";
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

  public void updateLocationIdOrRangerId(String column, int valueId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = String.format("UPDATE sightings SET %s = '%s' WHERE id=:id;", column, valueId);
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public List<Animal> allAnimals(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT animals.*  FROM sightings JOIN animals_sightings ON (sightings.id = animals_sightings.sighting_id) JOIN animals ON (animals_sightings.animal_id = animals.id) WHERE sightings.id =:id ORDER BY species asc;";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
    }
  }


}
