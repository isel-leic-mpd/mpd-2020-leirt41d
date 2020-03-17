public class Location {
    // indexes corresponding to fields to use
    private static final int NAME       =   0;
    private static final int COUNTRY    =   1;
    private static final int DISTRICT   =   2;
    private static final int LATITUDE   =   3;
    private static final int LONGITUDE  =   4;

    private String name;
    private String country;
    private String district;
    private double latitude;
    private double longitude;

    public Location(String name,
                    String country,
                    String district,
                    double latitude,
                    double longitude) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /* accessors */
    public String getName() {return name; }
    public String getCountry() { return country; }
    public String getDistrict() { return district; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    /**
     * Create a WeatherInfo from a line with csv parts
     * @param line
     * @return A WeatherInfo instance
     */
    public static Location valueOf(String line) {
        String[] parts = line.split("\t");

        return new Location(
                parts[NAME],
                parts[COUNTRY],
                parts[DISTRICT],
                Double.valueOf(parts[LATITUDE]),
                Double.valueOf(parts[LONGITUDE]));

    }

    @Override
    public String toString() {
        return "Location { " +
                name + ',' +
                country + ',' +
                district + ',' +
                latitude + ',' +
                latitude +
                '}';
    }

}
