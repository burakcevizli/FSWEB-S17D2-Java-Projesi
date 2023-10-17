package com.example.workintech.Sprint17D2.rest;

import jakarta.annotation.PostConstruct;
import com.example.workintech.Sprint17D2.model.Developer;
import com.example.workintech.Sprint17D2.model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.workintech.Sprint17D2.tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DevelopController {
    private Map<Integer, Developer> developers;
    private Taxable developerTax;


    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    @Autowired
    public DevelopController(Taxable developerTax) {
        this.developerTax = developerTax;
    }

    @GetMapping("/")
    public List<Developer> getAllDevelopers() {
        System.out.println("Bütün developerlar aşağıdadır :");
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Developer getDeveloper(@PathVariable Integer id) {
        return developers.get(id);
    }

    @PostMapping
    public Developer addDeveloper(@RequestBody Developer developer) {
        if (developer.getExperience().equals(Experience.JUNIOR)) {
            developer.setSalary((int) (developer.getSalary() - developerTax.getSimpleTaxRate()));
        } else if (developer.getExperience().equals(Experience.MID)) {
            developer.setSalary((int) (developer.getSalary() - developerTax.getMiddleTaxRate()));
        } else {
            developer.setSalary((int) (developer.getSalary() - developerTax.getUpperTaxRate()));
        }
        developers.put(developer.getId(), developer);
        return developer;
    }

    @PutMapping("/{id}")
    public void updateDeveloper(@PathVariable Integer id, @RequestBody Developer developer) {
        if (developers.containsKey(id)) {
            developers.put(id, developer);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDeveloper(@PathVariable Integer id) {
        developers.remove(id);
    }
}
