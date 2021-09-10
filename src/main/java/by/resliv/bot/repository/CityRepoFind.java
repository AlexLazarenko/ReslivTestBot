package by.resliv.bot.repository;

import by.resliv.bot.pojo.City;

import java.util.List;

public interface CityRepoFind {
    List<City> findByCityName(String cityName);
}
