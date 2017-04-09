import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Ranger {
  private int id;
  private int station_id;
  private String name;
  private String phone;
  private String badge;

  public Ranger(String name, String phone, String badge, int station_id) {
    this.name = name;
    this.phone = phone;
    this.badge = badge;
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
      return this.getName().equals(newRanger.getName()) &&
             this.getBadge().equals(newRanger.getBadge());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rangers (name, phone, badge, station_id) VALUES (:name, :phone, :badge, :station_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("phone", phone)
        .addParameter("badge", badge)
        .addParameter("station_id", station_id)
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
      String sql = "SELECT * FROM sightings WHERE ranger_id = :ranger_id ORDER BY timestamp desc;";
      return con.createQuery(sql)
        .addParameter("ranger_id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Animal> allAnimals(){
  try (Connection con = DB.sql2o.open()){
    String sql = "SELECT animals.*  FROM rangers JOIN animals_rangers ON (rangers.id = animals_rangers.ranger_id) JOIN animals ON (animals_rangers.animal_id = animals.id) WHERE rangers.id =:id ORDER BY species asc;";
    return con.createQuery(sql)
      .addParameter("id", this.getId())
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM rangers WHERE id=:id;";
      con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void updateInfo(String column, String value) {
    try(Connection con = DB.sql2o.open()) {
      String sql = String.format("UPDATE rangers SET %s = %s WHERE id=:id;", column, value);
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void changeStation(int stationId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET station_id = :station_id WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("station_id", stationId)
        .executeUpdate();
    }
  }

}
