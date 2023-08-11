package com.techelevator.dao;

import com.techelevator.model.Prize;

import java.util.List;

public interface PrizeDao {

    List<Prize> findAll();
    List<Prize> listAvailablePrizes(int familyId);
    int createPrize(Prize prize);
}
