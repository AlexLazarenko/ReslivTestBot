package by.resliv.bot.service;

import by.resliv.bot.controller.CityController;
import by.resliv.bot.pojo.City;
import by.resliv.bot.repository.CityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private static final Logger logger = LogManager.getLogger(CityService.class);

        @Autowired
        CityRepository cityRepository;

        public List<City> getAllCities(int maxCount) {
            List<City> cities = new ArrayList<>();

            cityRepository.findAll().forEach(
                    city -> {
                        City city1 = new City(city.getCityId(),
                                city.getName(),
                                city.getText());
                        if (cities.size() < maxCount) {
                            cities.add(city1);
                        }
                    }
            );
            return cities;
        }

        public City getCityById(int cityId) {
            City city = cityRepository.findById((long) cityId).orElseThrow();

            return new City(city.getCityId(),
                    city.getName(),
                    city.getText());
        }

        public void createNewCity(City newCity) {
            City city = new City();
            city.setCityId(newCity.getCityId());
            city.setName(newCity.getName());
            city.setText(newCity.getText());
            cityRepository.save(city);
        }
}
