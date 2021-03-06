import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class EndangeredAnimal extends Animal{
  private String health;
  private String age;
  public static final boolean ENDANGERED_STATUS = true;

  public EndangeredAnimal(String tag, String species, String type) {
    this.tag = tag;
    this.id = id;
    this.type = type;
    this.health = health;
    this.age = age;
    this.species = species;
    endangered = ENDANGERED_STATUS;
  }

  public String getHealth() {
    return health;
  }

  public String getAge() {
    return age;
  }

  @Override
  public boolean equals(Object otherEndangeredAnimal) {
    if(!(otherEndangeredAnimal instanceof EndangeredAnimal)) {
      return false;
    } else {
      EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) otherEndangeredAnimal;
      return this.getTag().equals(newEndangeredAnimal.getTag()) && this.getSpecies().equals(newEndangeredAnimal.getSpecies());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (tag, type, species, health, age, endangered) VALUES (:tag, :type, :species, :health, :age, true);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("tag", this.tag)
        .addParameter("health", this.health)
        .addParameter("age", this.age)
        .addParameter("type", this.type)
        .addParameter("species", this.species)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<EndangeredAnimal> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE endangered = true;";
      return con.createQuery(sql)
        .executeAndFetch(EndangeredAnimal.class);
    }
  }

  public void updateHealth(String health) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET health=:health WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("health", health)
        .executeUpdate();
    }
  }

  public void updateAge(String age) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET age=:age WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("age", age)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

}
