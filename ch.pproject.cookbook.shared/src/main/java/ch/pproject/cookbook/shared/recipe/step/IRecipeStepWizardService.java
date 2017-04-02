package ch.pproject.cookbook.shared.recipe.step;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IRecipeStepWizardService extends IService {

  RecipeStepWizardFormData prepareCreate(RecipeStepWizardFormData formData);

  RecipeStepWizardFormData create(RecipeStepWizardFormData formData);

  RecipeStepWizardFormData load(RecipeStepWizardFormData formData);

  RecipeStepWizardFormData store(RecipeStepWizardFormData formData);
}
