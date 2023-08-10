package com.techelevator.dao;

import com.techelevator.model.Prize;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPrizeDao implements PrizeDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcPrizeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Prize> findAll() {
        List<Prize> prizes = new ArrayList<>();
        String sql = "select * from prize";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Prize prize= mapRowToPrize(results);
            prizes.add(prize);
        }

        return prizes;
    }

    @Override
    public void createPrize(Prize prize) {
        prize.setStart_date(prize.getStart_date().replace('T', ' '));
        prize.setEnd_date(prize.getEnd_date().replace('T', ' '));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(prize.getStart_date(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(prize.getEnd_date(), formatter);
        String sql = "INSERT INTO prize (name, description, start_date, end_date) VALUES (?, ?, ?, ?) RETURNING prize_id";
        Integer prize_id;
        prize_id = jdbcTemplate.queryForObject(sql, Integer.class, prize.getName(), prize.getDescription(), startDate, endDate);
        System.out.println(prize_id);
    }


    private Prize mapRowToPrize(SqlRowSet row){
        Prize prize = new Prize();
        prize.setPrize_id(row.getInt("prize_id"));
        prize.setName(row.getString("name"));
        prize.setDescription(row.getString("description"));
        prize.setStart_date(row.getString("start_date"));
        prize.setEnd_date(row.getString("end_date"));
        return prize;
    }

}
