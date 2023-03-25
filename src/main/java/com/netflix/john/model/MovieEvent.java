package com.netflix.john.model;

import java.util.Objects;

public class MovieEvent {

    private Long movieID;
    private String eventType;

    public MovieEvent(){};

    public MovieEvent(Long movieID, String eventType) {
        this.movieID = movieID;
        this.eventType = eventType;
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEvent that = (MovieEvent) o;
        return Objects.equals(movieID, that.movieID) && Objects.equals(eventType, that.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID, eventType);
    }

    @Override
    public String toString() {
        return "MovieEvent{" +
                "movieID=" + movieID +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
