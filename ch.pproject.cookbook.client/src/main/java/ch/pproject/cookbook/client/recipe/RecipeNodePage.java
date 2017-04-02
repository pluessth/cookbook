package ch.pproject.cookbook.client.recipe;

import java.util.List;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientsTablePage;
import ch.pproject.cookbook.client.recipe.step.RecipeStepsTablePage;

public class RecipeNodePage extends AbstractPageWithNodes {

  private Long m_recipeNr;

  public RecipeNodePage(Long recipeNr) {
    m_recipeNr = recipeNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Recipe");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    RecipeIngredientsTablePage recipeIngredientsTablePage = new RecipeIngredientsTablePage(getRecipeNr());
    pageList.add(recipeIngredientsTablePage);

    RecipeStepsTablePage recipeStepsTablePage = new RecipeStepsTablePage(getRecipeNr());
    pageList.add(recipeStepsTablePage);
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
