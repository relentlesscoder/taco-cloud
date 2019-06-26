package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,
                                TacoRepository tacoRepository){
      this.ingredientRepository = ingredientRepository;
      this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order(){
      return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco(){
      return new Taco();
    }

    @ModelAttribute
    public void addIngredientToModel(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        for(String type: Ingredient.Type) {
            model.addAttribute(type.toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model){

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@ModelAttribute("design") @Valid Taco design, Errors errors,
                                @ModelAttribute("order") Order order){
      if(errors.hasErrors()){
          System.out.println(errors);
          return "design";
      }

      log.info("Processing design: " + design);
      Taco saved = tacoRepository.save(design);
      order.addDesign(saved);

      return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, String type){
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
