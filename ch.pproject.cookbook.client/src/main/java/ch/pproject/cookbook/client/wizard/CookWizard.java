package ch.pproject.cookbook.client.wizard;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.wizard.AbstractWizard;
import org.eclipse.scout.rt.shared.TEXTS;

public class CookWizard extends AbstractWizard {

  private Long m_recipeNr;

  public CookWizard(Long recipeNr) {
    super();
    setRecipeNr(recipeNr);
    // TODO [pluessth] Collect all steps of recipe
    //    setSteps(null);
  }

  // FIXME pluessth
//  @Override
//  protected int getConfiguredDisplayHint() {
//    return DISPLAY_HINT_VIEW;
//  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Cook");
  }

  @Override
  protected void execNextStep() {
    //TODO [pluessth] Auto-generated method stub.
  }

  @FormData
  public Long getRecipeNr() {
    return m_recipeNr;
  }

  @FormData
  public void setRecipeNr(Long recipeNr) {
    m_recipeNr = recipeNr;
  }
}
