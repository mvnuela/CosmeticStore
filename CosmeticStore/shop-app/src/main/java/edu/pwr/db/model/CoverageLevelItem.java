package edu.pwr.db.model;

public class CoverageLevelItem extends Item {
    private int id;
    private String name;
    private int numericValue;

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumericValue(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getNumericValue() {
        return numericValue;
    }

    @Override
    public String toString() {
        return name + " (" + numericValue + ")";
    }
}
