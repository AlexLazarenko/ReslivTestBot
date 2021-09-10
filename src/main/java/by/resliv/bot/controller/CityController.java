package by.resliv.bot.controller;

import by.resliv.bot.pojo.City;
import by.resliv.bot.service.CityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {

    private static final Logger logger = LogManager.getLogger(CityController.class);

    @Autowired
    CityService service;

    @GetMapping("/city")
    public List<City> getCities(@RequestParam int maxCount) {
        if (maxCount < 1) throw new IllegalArgumentException();
        return service.getAllCities(maxCount);
    }

    @GetMapping("/city/{cityId}")
    public City getCity(@PathVariable("cityId") int cityId) {
        return service.getCityById(cityId);
    }

    @PostMapping("/city")
    public void createNewCity(@RequestBody City newCity) {
        service.createNewCity(newCity);
        logger.info("New city: " + newCity);
    }

    @PutMapping("/city/{id}")
    public void updateCity(@PathVariable int id,
                           @RequestBody City city) {
        service.updateCity(id, city);
        logger.info("Update city ID=" + id + " " + city);
    }

    @DeleteMapping("/city/{id}")
    public void deleteCity(@PathVariable int id) {
        service.deleteCity(id);
        logger.info("Delete city ID=" + id);
    }
}
