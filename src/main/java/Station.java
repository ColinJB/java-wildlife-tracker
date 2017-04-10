import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Station {
  private int id;
  private String name;

  public Station(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherStation) {
    if(!(otherStation instanceof Station)) {
      return false;
    } else {
      Station newStation = (Station) otherStation;
      return this.getName().equals(newStation.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stations (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Station> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stations ORDER BY name asc;";
      return con.createQuery(sql)
        .executeAndFetch(Station.class);
    }
  }

  public static Station find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stations WHERE id=:id;";
      Station station = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Station.class);
      return station;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  public List<Sighting> allSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE station_id = :station_id ORDER BY timestamp desc;";
      return con.createQuery(sql)
        .addParameter("station_id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Sighting> allRangers() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE station_id = :id ORDER BY name asc;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stations WHERE id=:id;";
      con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stations SET name = :name WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("name", name)
        .executeUpdate();
    }
  }

}
