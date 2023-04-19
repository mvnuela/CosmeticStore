package edu.pwr.db.model;

import edu.pwr.db.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JoinedProductJdbcTemplate {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Item> mapper;

    public JoinedProductJdbcTemplate() {
        this.mapper = new JoinedProductMapper();
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Item> list(Item brand, Item color, Item coverageLevel, Item type) {
        String SQL =
                "select " +
                        "products.id as productId, " +
                        "products.name as product, " +
                        "c2.name as color, " +
                        "b2.name as brand, " +
                        "c3.name as coverageLevelName, " +
                        "c3.numericValue as coverageLevelNumericValue, " +
                        "t2.name as type " +
                        "from products " +
                        "inner join brands b2 on products.brand = b2.id " +
                        "inner join colors c2 on products.color = c2.id " +
                        "inner join coverageLevels c3 on products.coverageLevel = c3.id " +
                        "inner join types t2 on products.type = t2.id ";

        List<Object> args = new ArrayList<>();
        boolean argumentsBefore = false;
        if (brand instanceof SmallItem) {
            argumentsBefore = true;
            SQL += "WHERE ";
            SQL += "b2.id = ? ";
            args.add(((SmallItem) brand).getId());
        }
        if (coverageLevel instanceof CoverageLevelItem) {
            if (!argumentsBefore) {
                argumentsBefore = true;
                SQL += "WHERE ";
            } else {
                SQL += "AND ";
            }
            SQL += "c3.id = ? ";
            args.add(((CoverageLevelItem) coverageLevel).getId());
        }
        if (type instanceof SmallItem) {
            if (!argumentsBefore) {
                argumentsBefore = true;
                SQL += "WHERE ";
            } else {
                SQL += "AND ";
            }
            SQL += "t2.id = ? ";
            args.add(((SmallItem) type).getId());
        }
        if (color instanceof SmallItem) {
            if (!argumentsBefore) {
                //argumentsBefore = true;
                SQL += "WHERE ";
            } else {
                SQL += "AND ";
            }
            SQL += "c2.id = ? ";
            args.add(((SmallItem) color).id);
        }
        SQL += ";"; // nwm XD

        Logger.debug(SQL);

        return jdbcTemplate.query(SQL, mapper, args.toArray());
    }
}
