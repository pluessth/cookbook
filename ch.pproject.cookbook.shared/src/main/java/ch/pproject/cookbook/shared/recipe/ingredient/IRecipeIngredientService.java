package ch.pproject.cookbook.shared.recipe.ingredient;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IRecipeIngredientService extends IService {

  RecipeIngredientFormData prepareCreate(RecipeIngredientFormData formData);

  RecipeIngredientFormData create(RecipeIngredientFormData formData);

  RecipeIngredientFormData load(RecipeIngredientFormData formData);

  RecipeIngredientFormData store(RecipeIngredientFormData formData);

  void remove(List<Long> recipeIngredientNrs);
}
