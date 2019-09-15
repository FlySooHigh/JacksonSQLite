package repository.impl;

import model.Colour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import repository.ColourRepo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ColourJdbcRepoImpl implements ColourRepo {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    Colour new RowMapper<Colour>() = (rs, rowNum) -> {
//        Colour colour = new Colour();
//        return colour;
//    };

//    new RowMapper<Colour> rowMapper

//    (ResultSet rs, int rowNum) -> {
//        Colour colour = new Colour();
//        return colour;
//    }

//    new RowMapper<Colour>() {
//        @Override
//        public Colour mapRow(ResultSet rs, int rowNumber) throws SQLException {
//            Colour colour = new Colour();
//            return colour;
//        }
//    }

    @Autowired
    public ColourJdbcRepoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }



    @Override
    public Colour insert(Colour model) {
        String sql = "INSERT INTO colours (name, red, green, blue, alpha) " +
                     "VALUES (:name, :red, :green, :blue, :alpha)";

        int update = namedParameterJdbcTemplate.update(sql, toParams(model));

//        Long id = namedParameterJdbcTemplate.queryForObject(sql, toParams(model), Long.class);
//        model.setId(id);

        return model;

//        List<Colour> query = namedParameterJdbcTemplate.query(sql, new RowMapper<Colour>() {
//            @Override
//            public Colour mapRow(ResultSet rs, int rowNumber) throws SQLException {
//                Colour colour = new Colour();
//                return colour;
//            }
//        });
//        return null;
    }

    public List<Colour> insertBatch(List<Colour> models) {
        String sql = "INSERT INTO colours (name, red, green, blue, alpha) " +
                "VALUES (:name, :red, :green, :blue, :alpha)";

        namedParameterJdbcTemplate.batchUpdate(sql, toBatchParams(models));

        return models;
    }

    private MapSqlParameterSource toParams(Colour model) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", model.getId());
        params.addValue("name", model.getName());
        params.addValue("red", model.getRed());
        params.addValue("green", model.getGreen());
        params.addValue("blue", model.getBlue());
        params.addValue("alpha", model.getAlpha());
        return params;
    }

    private MapSqlParameterSource[] toBatchParams(List<Colour> models) {
        MapSqlParameterSource[] array = new MapSqlParameterSource[models.size()];
        for (int i = 0; i < models.size(); i++) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", models.get(i).getId());
            params.addValue("name", models.get(i).getName());
            params.addValue("red", models.get(i).getRed());
            params.addValue("green", models.get(i).getGreen());
            params.addValue("blue", models.get(i).getBlue());
            params.addValue("alpha", models.get(i).getAlpha());
            array[i] = params;
        }
        return array;
    }
}
