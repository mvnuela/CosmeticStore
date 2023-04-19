package edu.pwr.db.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoverageLevelItemMapper implements RowMapper<Item> {
    @Override
    public CoverageLevelItem mapRow(ResultSet resultSet, int i) throws SQLException {
        CoverageLevelItem item = new CoverageLevelItem();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setNumericValue(resultSet.getInt("numericValue"));
        return item;
    }
}
