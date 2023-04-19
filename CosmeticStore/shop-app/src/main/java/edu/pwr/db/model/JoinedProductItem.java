package edu.pwr.db.model;

public class JoinedProductItem extends Item {
    private int id;
    protected String color, brand, type, coverageLevelName, productName;
    protected int coverageLevelNumericValue;

    @Override
    public String toString() {
        return productName + " | color: " + color + " | brand: " + brand + " | " + type;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getProductName() {
        return productName;
    }
    public void setProductName(String product) {
        this.productName = product;
    }
    public int getCoverageLevelNumericValue() {
        return coverageLevelNumericValue;
    }
    public void setCoverageLevelNumericValue(int coverageLevelNumericValue) {
        this.coverageLevelNumericValue = coverageLevelNumericValue;
    }
}
