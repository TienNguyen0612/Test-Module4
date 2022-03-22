package codegym.controller;

import codegym.model.City;
import codegym.model.Country;
import codegym.service.ICityService;
import codegym.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private ICityService cityService;

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public ModelAndView showCities() {
        ModelAndView modelAndView = new ModelAndView("/views/list");
        Iterable<City> cities = cityService.FindAllCities();
        if (!cities.iterator().hasNext()) {
            modelAndView.addObject("message", "We don't have any city");
        }
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/views/create");
        Iterable<Country> countries = countryService.findAllCountries();
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("city", new City());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute City city, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/views/create");
        Iterable<Country> countries = countryService.findAllCountries();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Create failed");
//            modelAndView.addObject("color", "Red");
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", city);
        } else {
            City cityCreate = cityService.save(city);
            if (cityCreate != null) {
                modelAndView.addObject("countries", countries);
                modelAndView.addObject("message", "Create City Successfully !!!");
            }
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        cityService.remove(id);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/views/detail");
        Optional<City> city = cityService.findById(id);
        modelAndView.addObject("city", city.get());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/views/edit");
        Optional<City> city = cityService.findById(id);
        Iterable<Country> countries = countryService.findAllCountries();
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("city", city.get());
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@Valid @ModelAttribute City city, BindingResult bindingResult, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/views/edit");
        Iterable<Country> countries = countryService.findAllCountries();
        city.setId(id);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("message", "Create failed");
//            modelAndView.addObject("color", "Red");
            modelAndView.addObject("countries", countries);
            modelAndView.addObject("city", city);
        } else {
            City cityEdit = cityService.save(city);
            if (cityEdit != null) {
                modelAndView.addObject("countries", countries);
                modelAndView.addObject("message", "Update City Successfully");
            }
        }
        return modelAndView;
    }
}
