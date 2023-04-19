package edu.pwr.db.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmallItemJdbcTemplate {
    protected String tableName;
    protected RowMapper<Item> mapper;
    private JdbcTemplate jdbcTemplate;
    protected static Set<String> tableNames;
    static {
        tableNames = new HashSet<>();
        tableNames.add("colors");
        tableNames.add("brands");
        tableNames.add("types");
    }

    public SmallItemJdbcTemplate(String tableName) throws SQLException {
        if (!tableNames.contains(tableName)) {
            throw new SQLException("table name " + tableName + " does not match small item");
        }
        this.tableName = tableName;
        this.mapper = new SmallItemMapper();
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Item> list() {
        String SQL = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(SQL, mapper);
    }
}
