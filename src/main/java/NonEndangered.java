import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class NonEndangered extends Animal {

  public NonEndangered(String name, String species, String type) {
    this.name = name;
    this.id = id;
    this.type = type;
    this.endangered = false;
    this.health = health;
    this.age = age;
    this.species = species;
  }
}
