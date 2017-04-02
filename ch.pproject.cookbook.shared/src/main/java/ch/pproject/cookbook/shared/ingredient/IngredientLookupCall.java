package ch.pproject.cookbook.shared.ingredient;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class IngredientLookupCall extends LookupCall<Long> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<? extends ILookupService<Long>> getConfiguredService() {
    return IIngredientLookupService.class;
  }
}
