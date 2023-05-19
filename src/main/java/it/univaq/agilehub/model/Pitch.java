package it.univaq.agilehub.model;

public class Pitch {

    private int id;

    private String name;

    private Sport sport;

    public Pitch() {}
    public Pitch(int id, String name, Sport sport) {
        this.id = id;
        this.name = name;
        this.sport = sport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return name;
    }
}
