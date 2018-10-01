package seedu.address.model.carpark;

public class CarparkJson {
  private final String short_term_parking;
  private final String y_coord;
  private final String car_park_type;
  private final String x_coord;
  private final String free_parking;
  private final String night_parking;
  private final String address;
  private final String car_park_no;
  private final String type_of_parking_system;
  private final String id;
  private String total_lots;
  private String lots_available;

  public CarparkJson(String... data) {
    this.short_term_parking = data[0];
    this.car_park_type = data[1];
    this.y_coord = data[2];
    this.x_coord = data[3];
    this.free_parking = data[4];
    this.night_parking = data[5];
    this.address = data[6];
    this.id = data[7];
    this.car_park_no = data[8];
    this.type_of_parking_system = data[9];
  }

  public void setData() {
    this.total_lots = "Not available";
    this.lots_available = "Not available";
  }

  public void AddOn(String... data) {
    this.total_lots = data[0];
    this.lots_available = data[1];
  }

  public String getNumber() {
    return this.car_park_no;
  }

  @Override
  public String toString() {
    return "Short Term Parking: " + this.short_term_parking + "\n"
            + "Carpark Type: " + this.car_park_type + "\n"
            + "Y-Coordinates: " + this.y_coord + "\n"
            + "X-Coordinates: " + this.x_coord + "\n"
            + "Free Parking: " + this.free_parking + "\n"
            + "Night Parking: " + this.night_parking + "\n"
            + "Address: " + this.address + "\n"
            + "Carpark Number: " + this.car_park_no + "\n"
            + "Type Of Parking System: " + this.type_of_parking_system + "\n"
            + "Total Lots: " + this.total_lots + "\n"
            + "Lots Available: " + this.lots_available;
  }

  public Carpark toCarpark () {
    return new Carpark(this.short_term_parking,
            this.car_park_type,
            this.y_coord,
            this.x_coord,
            this.free_parking,
            this.night_parking,
            this.address,
            this.id,
            this.car_park_no,
            this.type_of_parking_system,
            this.total_lots,
            this.lots_available);
  }
}
