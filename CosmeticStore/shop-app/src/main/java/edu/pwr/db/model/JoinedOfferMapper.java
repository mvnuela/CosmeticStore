package edu.pwr.db.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinedOfferMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        JoinedOfferItem item = new JoinedOfferItem();

        item.setId(resultSet.getInt("offerId"));
        item.setUnitsInStock(resultSet.getInt("unitsInStock"));
        item.setPricePerUnit(resultSet.getDouble("pricePerUnit"));
        item.setProduct(resultSet.getString("product"));
        item.setColor(resultSet.getString("color"));
        item.setBrand(resultSet.getString("brand"));
        item.setCoverageLevelName(resultSet.getString("coverageLevelName"));
        item.setCoverageLevelNumericValue(resultSet.getInt("coverageLevelNumericValue"));
        item.setType(resultSet.getString("type"));
        return item;
    }

}