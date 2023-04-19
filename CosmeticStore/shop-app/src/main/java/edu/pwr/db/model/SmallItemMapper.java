package edu.pwr.db.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SmallItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        SmallItem item = new SmallItem();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        return item;
    }
}
