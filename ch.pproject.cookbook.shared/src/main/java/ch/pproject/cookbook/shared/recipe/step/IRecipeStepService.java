package ch.pproject.cookbook.shared.recipe.step;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import ch.pproject.cookbook.shared.recipe.RecipeStepFormData;

@TunnelToServer
public interface IRecipeStepService extends IService {

  RecipeStepFormData prepareCreate(RecipeStepFormData formData);

  RecipeStepFormData create(RecipeStepFormData formData);

  RecipeStepFormData load(RecipeStepFormData formData);

  RecipeStepFormData store(RecipeStepFormData formData);

  List<RecipeStepFormData> getAllRecipeSteps(Long recipeNr);

  void moveStepUp(Long recipeStepNr);

  void moveStepDown(Long recipeStepNr);
}
