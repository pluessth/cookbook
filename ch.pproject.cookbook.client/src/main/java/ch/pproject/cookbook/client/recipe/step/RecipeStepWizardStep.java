package ch.pproject.cookbook.client.recipe.step;

import org.eclipse.scout.rt.client.ui.wizard.AbstractWizardStep;
import org.eclipse.scout.rt.shared.TEXTS;

public class RecipeStepWizardStep extends AbstractWizardStep<RecipeStepWizardForm> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Step");
  }
}
