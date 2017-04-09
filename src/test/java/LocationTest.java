import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class LocationTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getStationId_() {
    Station testStation1 = new Station("Station1");
    testStation1.save();
    Location testLocation = new Location("Here", testStation1.getId());
    testLocation.save();
    assertEquals(testLocation.getStationId(), testStation1.getId());
  }
}
