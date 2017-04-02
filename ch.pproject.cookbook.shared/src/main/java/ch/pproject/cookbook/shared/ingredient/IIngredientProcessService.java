package ch.pproject.cookbook.shared.ingredient;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IIngredientProcessService extends IService {

  IngredientFormData prepareCreate(IngredientFormData formData);

  IngredientFormData create(IngredientFormData formData);

  IngredientFormData load(IngredientFormData formData);

  IngredientFormData store(IngredientFormData formData);
}
