package tacos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class Design {

  @NotNull
  private Taco taco;

  private Map<String, List<Ingredient>> allIngredients;
}
