package ch.pproject.cookbook.shared.services;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import ch.pproject.cookbook.shared.ingredient.IngredientSearchFormData;
import ch.pproject.cookbook.shared.recipe.RecipesSearchFormData;

@TunnelToServer
public interface IStandardOutlineService extends IService {

  AbstractTablePageData getRecipesTableData(RecipesSearchFormData searchFromData);

  AbstractTablePageData getIngredientTableData(IngredientSearchFormData searchFromData);

  AbstractTablePageData getRecipeIngredientsTableData(Long recipeNr);

  AbstractTablePageData getRecipeStepTableData(Long recipeNr);

  AbstractTablePageData getMeasureTableData();

}
