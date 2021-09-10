package by.resliv.bot.service;

import by.resliv.bot.pojo.City;
import by.resliv.bot.repository.CityRepository;
import by.resliv.bot.repository.impl.CityRepoFindImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CityService {
    private static final Logger logger = LogManager.getLogger(CityService.class);

    private static final String CITY_NOT_FOUND = "City with this id not found!";

    @Autowired
    CityRepository cityRepository;
    @Autowired
    CityRepoFindImpl cityRepoFind;

    public List<City> getAllCities(int maxCount) {
        List<City> cities = new ArrayList<>();
        cityRepository.findAll().forEach(
                city -> {
                    if (cities.size() < maxCount) {
                        cities.add(city);
                    }
                }
        );
        return cities;
    }

    public City getCityById(int cityId) {
        City city = cityRepository.findById((long) cityId).orElseThrow();
        return city;
    }

    public void createNewCity(City newCity) {
        cityRepository.save(newCity);
    }

    public void updateCity(int id,City newCity) {
        if (cityRepository.existsById((long) id)){
            City city = new City();
            city.setCityId((long) id);
            city.setName(newCity.getName());
            city.setText(newCity.getText());
            cityRepository.save(city);
        }else {
            logger.warn(CITY_NOT_FOUND);
            throw new NoSuchElementException ();
        }
    }

    public void deleteCity(int id){
        cityRepository.deleteById((long) id);
    }

    public Optional<City> findByCityName(String name) {
        Optional<City> city = Optional.empty();
        List<City> cities = cityRepoFind.findByCityName(name);
         if (cities.size() == 1) {
            city = Optional.of(cities.get(0));
        }
        return city;
    }
}
