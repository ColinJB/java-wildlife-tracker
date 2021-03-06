import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.allAnimals());
      model.put("rangers", Ranger.all());
      model.put("locations", Location.all());
      model.put("stations", Station.all());
      model.put("sightings", Sighting.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.allAnimals());
      model.put("template", "templates/animal-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/new", (request, response) -> {
      String endangered = request.queryParams("endangered");
      String species = request.queryParams("species");
      String type = request.queryParams("type");
      String tag = request.queryParams("tag");
      if (endangered.equals("yes")){
        EndangeredAnimal endangeredAnimal = new EndangeredAnimal(tag, species, type);
        endangeredAnimal.save();
      } else {
        NonEndangered nonEndangered = new NonEndangered(tag, species, type);
        nonEndangered.save();
      }
      response.redirect("/");
      return null;
    });

    get("/ranger/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stations", Station.all());
      model.put("template", "templates/ranger-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/ranger/new", (request, response) -> {
      String name = request.queryParams("name");
      String phone = request.queryParams("phone");
      String badge = request.queryParams("badge");
      int stationId = Integer.parseInt(request.queryParams("station"));
      Ranger newRanger = new Ranger(name, phone, badge, stationId);
      newRanger.save();
      response.redirect("/");
      return null;
    });

    get("/station/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/station-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/station/new", (request, response) -> {
      String name = request.queryParams("name");
      Station newStation = new Station(name);
      newStation.save();
      response.redirect("/");
      return null;
    });

    get("/location/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stations", Station.all());
      model.put("template", "templates/location-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/location/new", (request, response) -> {
      String name = request.queryParams("name");
      int station_id = Integer.parseInt(request.queryParams("station"));
      Location newLocation = new Location(name, station_id);
      newLocation.save();
      response.redirect("/");
      return null;
    });

    post("/animal/sighting/new", (request, response) -> {
      int rangerId = Integer.parseInt(request.queryParams("ranger"));
      int locationId = Integer.parseInt(request.queryParams("location"));
      int stationId = Integer.parseInt(request.queryParams("station"));
      int animalId = Integer.parseInt(request.queryParams("animal"));
      String dateInput = request.queryParams("date");
      Timestamp timestamp = Timestamp.valueOf(dateInput.replace("T"," ") + ":00");
      Date date = new Date(timestamp.getTime());
      DateFormat df = new SimpleDateFormat("E, MMM dd yyyy H:mm a");
      String stringDate = df.format(date);
      Animal newAnimal = Animal.find(animalId);
      Sighting newSighting = new Sighting(locationId, stationId, timestamp, stringDate, rangerId, newAnimal);
      newSighting.save();
      System.out.println(newAnimal.getId());
      response.redirect("/");
      return null;
    });

    get("/animal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params("id")));
      model.put("animal", animal);
      model.put("sightings", animal.allSightings());
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post("/endangered_sighting", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   String rangerName = request.queryParams("rangerName");
    //   int animalIdSelected = Integer.parseInt(request.queryParams("endangeredAnimalSelected"));
    //   String latLong = request.queryParams("latLong");
    //   // Sighting sighting = new Sighting(animalIdSelected, latLong, rangerName);
    //   // sighting.save();
    //   // model.put("sighting", sighting);
    //   model.put("animals", EndangeredAnimal.all());
    //   String animal = EndangeredAnimal.find(animalIdSelected).getSpecies();
    //   model.put("animal", animal);
    //   model.put("template", "templates/success.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    // post("/sighting", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   String rangerName = request.queryParams("rangerName");
    //   int animalIdSelected = Integer.parseInt(request.queryParams("animalSelected"));
    //   String latLong = request.queryParams("latLong");
    //   // Sighting sighting = new Sighting(animalIdSelected, latLong, rangerName);
    //   // sighting.save();
    //   // model.put("sighting", sighting);
    //   model.put("animals", Animal.allAnimals());
    //   String animal = Animal.find(animalIdSelected).getSpecies();
    //   model.put("animal", animal);
    //   model.put("template", "templates/success.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());



    // get("/endangered_animal/:id", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   EndangeredAnimal endangeredAnimal = EndangeredAnimal.find(Integer.parseInt(request.params("id")));
    //   model.put("endangeredAnimal", endangeredAnimal);
    //   model.put("template", "templates/endangered_animal.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/error", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/error.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
