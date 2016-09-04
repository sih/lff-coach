package eu.waldonia.lffcoach.model;

/**
 * A screening in the film festival
 * @author sih
 */
public class Screening  {

    private String film;
    private String venueDate;
    private String venueTime;
    private String isoDate;
    private String synopsis;
    private String cinema;
    private double latitude;
    private double longitude;
    private String festivalLink;


    /**
     * This object is simple enough to be represented as a JSON string
     * @return A string representation of this screening.
     */
    @Override
    public String toString() {
        return '{' +
                "\"film\":\"" + film + "\"" +
                ", \"venueDate\":\"" + venueDate + "\""+
                ", \"venueTime\":\"" + venueTime + "\"" +
                ", \"isoDate\":\"" + isoDate + "\"" +
                ", \"synopsis\":\"" + synopsis + "\"" +
                ", \"cinema\":\"" + cinema + "\"" +
                ", \"latitude\":" + latitude +
                ", \"longitude\":" + longitude +
                ", \"festivalLink\":\"" + festivalLink + "\"" +
                '}';
    }

    /**
     * @return A JSON string representing this screenings
     */
    public String toJson() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening screening = (Screening) o;

        if (film != null ? !film.equals(screening.film) : screening.film != null) return false;
        if (venueDate != null ? !venueDate.equals(screening.venueDate) : screening.venueDate != null) return false;
        return venueTime != null ? venueTime.equals(screening.venueTime) : screening.venueTime == null;

    }

    @Override
    public int hashCode() {
        int result = film != null ? film.hashCode() : 0;
        result = 31 * result + (venueDate != null ? venueDate.hashCode() : 0);
        result = 31 * result + (venueTime != null ? venueTime.hashCode() : 0);
        return result;
    }

    public String getVenueDate() {
        return venueDate;
    }

    public void setVenueDate(String venueDate) {
        this.venueDate = venueDate;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFestivalLink() {
        return festivalLink;
    }

    public void setFestivalLink(String festivalLink) {
        this.festivalLink = festivalLink;
    }

    public String getVenueTime() {
        return venueTime;
    }

    public void setVenueTime(String venueTime) {
        this.venueTime = venueTime;
    }

    public String getIsoDate() {
        return isoDate;
    }

    public void setIsoDate(String isoDate) {
        this.isoDate = isoDate;
    }
}
