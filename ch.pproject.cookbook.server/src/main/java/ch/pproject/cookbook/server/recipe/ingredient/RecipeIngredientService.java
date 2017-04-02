package ch.pproject.cookbook.server.recipe.ingredient;

import java.util.List;

import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import ch.pproject.cookbook.server.services.ISequences;
import ch.pproject.cookbook.shared.recipe.ingredient.IRecipeIngredientService;
import ch.pproject.cookbook.shared.recipe.ingredient.RecipeIngredientFormData;

public class RecipeIngredientService implements IRecipeIngredientService {

  @Override
  public RecipeIngredientFormData prepareCreate(RecipeIngredientFormData formData) {
    return formData;
  }

  @Override
  public RecipeIngredientFormData create(RecipeIngredientFormData formData) {
    formData.setRecipeIngredientNr(SQL.getSequenceNextval(ISequences.CBSEQ));

    SQL.insert("" +
        "INSERT INTO RECIPE_INGREDIENT (RECIPE_INGREDIENT_NR, INGREDIENT_NR, MEASURE_NR, RECIPE_NR, QUANTITY) " +
        "VALUES (:recipeIngredientNr, :ingredient, :measure, :recipeNr, :quantity) ", formData);

    return formData;
  }

  @Override
  public RecipeIngredientFormData load(RecipeIngredientFormData formData) {
    SQL.selectInto("" +
        "SELECT INGREDIENT_NR, " +
        "       MEASURE_NR, " +
        "       RECIPE_NR, " +
        "       QUANTITY " +
        "FROM   RECIPE_INGREDIENT " +
        "WHERE  RECIPE_INGREDIENT_NR = :recipeIngredientNr " +
        "INTO   :ingredient, " +
        "       :measure, " +
        "       :recipeNr, " +
        "       :quantity ", formData);

    return formData;
  }

  @Override
  public RecipeIngredientFormData store(RecipeIngredientFormData formData) {
    SQL.update("" +
        "UPDATE RECIPE_INGREDIENT " +
        "SET    INGREDIENT_NR = :ingredient, " +
        "       MEASURE_NR = :measure, " +
        "       RECIPE_NR = :recipeNr, " +
        "       QUANTITY = :quantity " +
        "WHERE  RECIPE_INGREDIENT_NR = :recipeIngredientNr ", formData);

    return formData;
  }

  @Override
  public void remove(List<Long> recipeIngredientNrs) {
    SQL.delete("" +
        "DELETE FROM RECIPE_INGREDIENT " +
        "WHERE  RECIPE_INGREDIENT_NR = :recipeIngredientNrs ", new NVPair("recipeIngredientNrs", recipeIngredientNrs));
  }
}
