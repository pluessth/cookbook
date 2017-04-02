package ch.pproject.cookbook.client.outlines;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.cookbook.client.ingredient.IngredientsTablePage;
import ch.pproject.cookbook.client.measure.MeasuresTablePage;
import ch.pproject.cookbook.client.recipe.RecipesTablePage;

public class StandardOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("StandardOutline");
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(new RecipesTablePage());
    pageList.add(new IngredientsTablePage());
    pageList.add(new MeasuresTablePage());
  }
}
