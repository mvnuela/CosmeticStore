package edu.pwr.db.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        ClientItem item = new ClientItem();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setSurname(resultSet.getString("surname"));
        item.setAddress(resultSet.getString("address"));
        return item;
    }
}
