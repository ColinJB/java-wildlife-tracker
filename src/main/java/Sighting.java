import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;


public class Sighting {
  private int id;
  private int ranger_id;
  private String location;
  private Timestamp timestamp;
  private String date;

  public Sighting(String location, int ranger_id, String dateTime) {
    this.location = location;
    this.ranger_id = ranger_id;
    this.date = dateTime;
  }

  public Timestamp createTimestamp() {
    Timestamp timestamp = Timestamp.valueOf(this.date.replace("T"," "));
    return timestamp;
  }

  public String stampToString() {
    String stringTime = DateFormat.getDateTimeInstance().format(timestamp);
    return stringTime;
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
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (date, location, ranger_id) VALUES (:date, :location, :ranger_id);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("date", this.date)
        .addParameter("location", this.location)
        .addParameter("ranger_id", this.ranger_id)
        .executeUpdate()
        .getKey();
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



}
