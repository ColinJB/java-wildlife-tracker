import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Location {
  private int id;
  private String name;
  private int station_id;

  public Location(String name, int station_id) {
    this.name = name;
    this.station_id = station_id;
  }

  public int getId() {
    return id;
  }

  public int getStationId() {
    return station_id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherLocation) {
    if(!(otherLocation instanceof Location)) {
      return false;
    } else {
      Location newLocation = (Location) otherLocation;
      return this.getName().equals(newLocation.getName()) &&
             this.getStationId() == newLocation.getStationId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO locations (name, station_id) VALUES (:name, :station_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("station_id", station_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Location> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM locations ORDER BY name asc;";
      return con.createQuery(sql)
        .executeAndFetch(Location.class);
    }
  }

  public static Location find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM locations WHERE id=:id;";
      Location location = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Location.class);
      return location;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  public List<Sighting> allSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE location_id = :location_id ORDER BY timestamp desc;";
      return con.createQuery(sql)
        .addParameter("location_id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Animal> allAnimals(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT animals.*  FROM locations JOIN animals_locations ON (locations.id = animals_locations.location_id) JOIN animals ON (animals_locations.animal_id = animals.id) WHERE locations.id =:id ORDER BY species asc;";
    return con.createQuery(sql)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM locations WHERE id=:id;";
      con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE locations SET name = :name WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("name", name)
        .executeUpdate();
    }
  }

}
