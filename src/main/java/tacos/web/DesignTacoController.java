package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tacos.Taco;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GBRF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP)
        );

        Type[] types = Ingredient.Type.values();
        for(Type type: types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
