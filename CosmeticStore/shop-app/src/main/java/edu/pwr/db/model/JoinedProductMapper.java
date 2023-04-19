package edu.pwr.db.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinedProductMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        JoinedProductItem item = new JoinedProductItem();

        item.setId(resultSet.getInt("productId"));
        item.setProductName(resultSet.getString("product"));
        item.setBrand(resultSet.getString("brand"));
        item.setCoverageLevelName(resultSet.getString("coverageLevelName"));
        item.setColor(resultSet.getString("color"));
        item.setCoverageLevelNumericValue(resultSet.getInt("coverageLevelNumericValue"));
        item.setType(resultSet.getString("type"));
        return item;
    }
}
