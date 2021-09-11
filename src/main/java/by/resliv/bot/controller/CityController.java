package by.resliv.bot.controller;

import by.resliv.bot.pojo.City;
import by.resliv.bot.service.CityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CityController {

    private static final Logger logger = LogManager.getLogger(CityController.class);

    @Autowired
    CityService service;

    @GetMapping("/city")
    public ResponseEntity<?> getCities(@RequestParam int maxCount) {
        if (maxCount < 1) throw new IllegalArgumentException();
        List<City> cities = service.getAllCities(maxCount);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> getCity(@PathVariable("cityId") int cityId) {
        ResponseEntity<?> responseEntity;
        Optional<City> city = Optional.of(service.getCityById(cityId));
        if (city.isPresent()) {
            responseEntity = new ResponseEntity<>(city, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>("City with id=" + cityId + " not exist", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PostMapping("/city")
    public void createNewCity(@RequestBody City newCity) {
        service.createNewCity(newCity);
        logger.info("New city: " + newCity);
    }

    @PutMapping("/city/{id}")
    public ResponseEntity<?> updateCity(@PathVariable int id,
                                        @RequestBody City city) {
        ResponseEntity<?> responseEntity;
        if (service.isExists(id)) {
            service.updateCity(id, city);
            responseEntity = new ResponseEntity<>(city, HttpStatus.OK);
            logger.info("Update city ID=" + id + " " + city);
        } else responseEntity = new ResponseEntity<>("City with id=" + id + " not exist", HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @DeleteMapping("/city/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable int id) {
        ResponseEntity<?> responseEntity;
        if (service.isExists(id)) {
            service.deleteCity(id);
            responseEntity = new ResponseEntity<>("City deleted!", HttpStatus.OK);
            logger.info("Delete city ID=" + id);
        } else responseEntity = new ResponseEntity<>("City with id=" + id + " not exists!", HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}
