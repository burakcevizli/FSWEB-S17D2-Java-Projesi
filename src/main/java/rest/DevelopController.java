package rest;

import jakarta.annotation.PostConstruct;
import model.Developer;
import model.Experience;
import org.springframework.web.bind.annotation.*;
import tax.DeveloperTax;
import tax.Taxable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech")
public class DevelopController {
    private Map<Integer, Developer> developers;
    private Taxable developerTax;

    public DevelopController(Taxable developerTax) {
        this.developerTax = developerTax;
    }

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
        developers.put(1, new Developer(1, "Burak",30000, Experience.JUNIOR ));
        developers.put(2, new Developer(2, "Mehmet",25555,Experience.MID));
    }

    @GetMapping("/developers")
    public List<Developer> developerList() {
        return new ArrayList<>(developers.values());
    }
    @GetMapping("/{id}")
    public Developer idDeveloper(@PathVariable Integer id){
        return developers.get(id);
    }
    @PostMapping
    public void addDeveloper(@RequestBody Developer developer){

        if(developer.getExperience().equals(Experience.JUNIOR)){
            developer.setSalary((int) (developer.getSalary()-developerTax.getSimpleTaxRate()));
        }else if(developer.getExperience().equals(Experience.MID)){
            developer.setSalary((int) (developer.getSalary()-developerTax.getMiddleTaxRate()));
        }else{
            developer.setSalary((int) (developer.getSalary()-developerTax.getUpperTaxRate()));
        }
        developers.put(developer.getId(),developer);
    }

}
