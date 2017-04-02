package ch.pproject.cookbook.server.recipe;

import org.eclipse.scout.rt.platform.holders.ITableBeanRowHolder;
import org.eclipse.scout.rt.platform.holders.TableBeanHolderFilter;
import org.eclipse.scout.rt.server.jdbc.SQL;

import ch.pproject.cookbook.server.services.ISequences;
import ch.pproject.cookbook.shared.recipe.IRecipeService;
import ch.pproject.cookbook.shared.recipe.RecipeFormData;

public class RecipeService implements IRecipeService {

  @Override
  public RecipeFormData prepareCreate(RecipeFormData formData) {
    formData.setRecipeNr(SQL.getSequenceNextval(ISequences.CBSEQ));
    return formData;
  }

  @Override
  public RecipeFormData create(RecipeFormData formData) {
    if (formData.getRecipeNr() == null) {
      formData.setRecipeNr(SQL.getSequenceNextval(ISequences.CBSEQ));
    }

    SQL.insert("" +
        "INSERT INTO RECIPE (" +
        "RECIPE_NR, " +
        "RECIPE_NAME, " +
        "PREPARATION_TIME, " +
        "DIFFICULTY_UID, " +
        "PORTIONS, " +
        "NOTES " +
        ")  " +
        "VALUES ( " +
        ":recipeNr, " +
        ":recipeName, " +
        ":preparationTime, " +
        ":difficulty, " +
        ":portions, " +
        ":notes ) ", formData);

    formData = storeRecipeIngredientsTable(formData);

    return formData;
  }

  @Override
  public RecipeFormData load(RecipeFormData formData) {
    SQL.selectInto("" +
        "SELECT " +
        "     RECIPE_NAME, " +
        "     PREPARATION_TIME, " +
        "     DIFFICULTY_UID, " +
        "     PORTIONS, " +
        "     NOTES " +
        "FROM RECIPE " +
        "WHERE RECIPE_NR = :recipeNr " +
        "INTO :recipeName, " +
        "     :preparationTime, " +
        "     :difficulty, " +
        "     :portions, " +
        "     :notes ", formData);

    formData = loadRecipeIngredientsTable(formData);

    return formData;
  }

  @Override
  public RecipeFormData store(RecipeFormData formData) {
    SQL.update("" +
        "UPDATE RECIPE " +
        "SET RECIPE_NAME = :recipeName, " +
        "    DIFFICULTY_UID = :difficulty," +
        "    PREPARATION_TIME = :preparationTime, " +
        "    PORTIONS = :portions, " +
        "    NOTES = :notes " +
        "WHERE  RECIPE_NR = :recipeNr ", formData);

    formData = storeRecipeIngredientsTable(formData);

    return formData;
  }

  private RecipeFormData storeRecipeIngredientsTable(RecipeFormData formData) {
    SQL.insert("" +
        "INSERT INTO RECIPE_INGREDIENT (RECIPE_INGREDIENT_NR, INGREDIENT_NR, MEASURE_NR, QUANTITY, RECIPE_NR) " +
        "SELECT nextval('" + ISequences.CBSEQ + "'), :{ingredient}, :{measure}, :{quantity}, :recipeNr FROM DUAL ",
        new TableBeanHolderFilter(formData.getIngredients(), ITableBeanRowHolder.STATUS_INSERTED), formData.getIngredients(), formData);

    SQL.update("" +
        "UPDATE RECIPE_INGREDIENT " +
        "SET MEASURE_NR = :{measure}, " +
        "    QUANTITY = :{quantity}, " +
        "    INGREDIENT_NR = :{ingredient} " +
        "WHERE RECIPE_INGREDIENT_NR = :{recipeIngredientNr} ",
        new TableBeanHolderFilter(formData.getIngredients(), ITableBeanRowHolder.STATUS_UPDATED),
        formData.getIngredients(), formData);

    SQL.delete("" +
        "DELETE FROM RECIPE_INGREDIENT " +
        "WHERE RECIPE_INGREDIENT_NR = :{recipeIngredientNr} ",
        new TableBeanHolderFilter(formData.getIngredients(), ITableBeanRowHolder.STATUS_DELETED));
    return formData;
  }

  private RecipeFormData loadRecipeIngredientsTable(RecipeFormData formData) {

    SQL.selectInto("" +
        "SELECT RECIPE_INGREDIENT_NR, " +
        "       INGREDIENT_NR, " +
        "       MEASURE_NR, " +
        "       QUANTITY " +
        "FROM   RECIPE_INGREDIENT RI " +
        "WHERE  RECIPE_NR = :recipeNr " +
        "INTO   :recipeIngredientNr, " +
        "       :ingredient, " +
        "       :measure, " +
        "       :quantity ", formData.getIngredients(), formData);

    return formData;
  }
}
