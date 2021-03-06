import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class NonEndangered extends Animal {
  public static final boolean ENDANGERED_STATUS = false;

  public NonEndangered(String tag, String species, String type) {
    this.tag = tag;
    this.id = id;
    this.type = type;
    this.species = species;
    endangered = ENDANGERED_STATUS;
  }

  @Override
  public boolean equals(Object otherNonEndangered) {
    if(!(otherNonEndangered instanceof NonEndangered)) {
      return false;
    } else {
      NonEndangered newNonEndangered = (NonEndangered) otherNonEndangered;
      return this.getTag().equals(newNonEndangered.getTag()) && this.getSpecies().equals(newNonEndangered.getSpecies());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (tag, type, species, endangered) VALUES (:tag, :type, :species, false);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("tag", this.tag)
        .addParameter("type", this.type)
        .addParameter("species", this.species)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<NonEndangered> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE endangered = false;";
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .executeAndFetch(NonEndangered.class);
    }
  }

  // public static NonEndangered find(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM animals WHERE id=:id;";
  //     NonEndangered nonEndangered = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(NonEndangered.class);
  //     return nonEndangered;
  //   }
  // }


}
