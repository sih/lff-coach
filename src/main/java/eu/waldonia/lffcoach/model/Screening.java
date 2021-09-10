package eu.waldonia.lffcoach.model;

import lombok.Data;

/**
 * A screening in the film festival
 *
 * @author sih
 */
@Data
public class Screening {
  private String film;
  private String venueDate;
  private String venueTime;
  private String cinema;

  public String toJson() {
    return '{'
        + "\"film\":\""
        + film
        + "\""
        + ", \"venueDate\":\""
        + venueDate
        + "\""
        + ", \"venueTime\":\""
        + venueTime
        + "\""
        + ", \"cinema\":\""
        + cinema
        + "\""
        + '}';
  }
}
