package ch.pproject.cookbook.shared.recipe;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IRecipeService extends IService {

  RecipeFormData prepareCreate(RecipeFormData formData);

  RecipeFormData create(RecipeFormData formData);

  RecipeFormData load(RecipeFormData formData);

  RecipeFormData store(RecipeFormData formData);
}
