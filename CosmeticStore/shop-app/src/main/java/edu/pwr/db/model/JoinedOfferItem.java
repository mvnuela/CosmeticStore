package edu.pwr.db.model;

public class JoinedOfferItem extends Item {
    private int id;
    protected double pricePerUnit; // TODO: check if it is ok to use double when mapping from decimal
    protected int unitsInStock;
    protected String color, brand, type, coverageLevelName, product;
    protected int coverageLevelNumericValue;

    @Override
    public String toString() {
        return product + " | color: " + color + " | brand: " + brand + " | " + type + " || " ;//+ String.format("%2lf", pricePerUnit);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getPricePerUnit() {
        return pricePerUnit;
    }
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
    public int getUnitsInStock() {
        return unitsInStock;
    }
    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCoverageLevelName() {
        return coverageLevelName;
    }
    public void setCoverageLevelName(String coverageLevelName) {
        this.coverageLevelName = coverageLevelName;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public int getCoverageLevelNumericValue() {
        return coverageLevelNumericValue;
    }
    public void setCoverageLevelNumericValue(int coverageLevelNumericValue) {
        this.coverageLevelNumericValue = coverageLevelNumericValue;
    }
}
