package codegym.service;

import codegym.model.City;

import java.util.Optional;

public interface ICityService {
    Iterable<City> FindAllCities();

    Optional<City> findById(Long id);

    City save(City city);

    void remove(Long id);
}
