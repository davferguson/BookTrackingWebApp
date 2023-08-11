package com.techelevator.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prize {
    private int prize_id;
    private String name;
    private String description;
    private String goal_type;
    private int goal_val;
    private String start_date;
    private String end_date;
    public Prize() {

    }

    public String getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(String goal_type) {
        this.goal_type = goal_type;
    }

    public int getGoal_val() {
        return goal_val;
    }

    public void setGoal_val(int goal_val) {
        this.goal_val = goal_val;
    }

    public int getPrize_id() {
        return prize_id;
    }

    public void setPrize_id(int prize_id) {
        this.prize_id = prize_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
