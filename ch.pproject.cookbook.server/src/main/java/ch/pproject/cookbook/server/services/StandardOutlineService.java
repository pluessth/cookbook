package ch.pproject.cookbook.server.services;

import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

import ch.pproject.cookbook.shared.ingredient.IngredientSearchFormData;
import ch.pproject.cookbook.shared.ingredient.IngredientsTablePageData;
import ch.pproject.cookbook.shared.measure.MeasureTablePageData;
import ch.pproject.cookbook.shared.recipe.RecipesSearchFormData;
import ch.pproject.cookbook.shared.recipe.RecipesTablePageData;
import ch.pproject.cookbook.shared.recipe.ingredient.RecipeIngredientsTablePageData;
import ch.pproject.cookbook.shared.recipe.step.RecipeStepsTablePageData;
import ch.pproject.cookbook.shared.services.IStandardOutlineService;

public class StandardOutlineService implements IStandardOutlineService {

  @Override
  public AbstractTablePageData getRecipesTableData(RecipesSearchFormData searchFormData) {
    RecipesTablePageData page = new RecipesTablePageData();
    StringBuilder statement = new StringBuilder();
    statement.append("" +
        "SELECT R.RECIPE_NR, " +
        "       R.RECIPE_NAME, " +
        "       R.PREPARATION_TIME, " +
        "       R.DIFFICULTY_UID, " +
        "       R.PORTIONS " +
        "FROM   RECIPE R " +
        "WHERE  1=1 ");
    if (!StringUtility.isNullOrEmpty(searchFormData.getRecipeName().getValue())) {
      statement.append("AND UPPER(R.RECIPE_NAME) LIKE UPPER(CONCAT('%',:recipeName,'%')) ");
    }
    if (searchFormData.getPreparationTimeFrom().getValue() != null) {
      statement.append("AND R.PREPARATION_TIME >= :preparationTimeFrom ");
    }
    if (searchFormData.getPreparationTimeTo().getValue() != null) {
      statement.append("AND R.PREPARATION_TIME <= :preparationTimeTo ");
    }
    if (searchFormData.getDifficulty().getValue() != null) {
      statement.append("AND R.DIFFICULTY_UID IN (:{difficulty}) ");
    }
    if (searchFormData.getSelectedIngredientNrs() != null && !CollectionUtility.isEmpty(searchFormData.getSelectedIngredientNrs())) {
      statement.append("AND R.RECIPE_NR IN (SELECT RI.RECIPE_NR FROM (");
      statement.append("SELECT RII.RECIPE_NR, COUNT(1) FROM RECIPE_INGREDIENT RII WHERE RII.INGREDIENT_NR IN (" + StringUtility.join(",", searchFormData.getSelectedIngredientNrs()) + ") GROUP BY RII.RECIPE_NR HAVING COUNT(1) >= "
          + (searchFormData.getSelectedIngredientNrs().size()));
      statement.append(") RI)");
    }
    statement.append(" INTO " +
        " :{page.recipeNr}, " +
        " :{page.recipeName}, " +
        " :{page.preparationTime}, " +
        " :{page.difficulty}, " +
        " :{page.portions} ");
    SQL.selectInto(statement.toString(), searchFormData, new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getIngredientTableData(IngredientSearchFormData searchFormData) {
    IngredientsTablePageData page = new IngredientsTablePageData();
    StringBuilder statement = new StringBuilder();
    statement.append("" +
        "SELECT I.INGREDIENT_NR, " +
        "       I.INGREDIENT_NAME, " +
        "       GROUP_CONCAT(M.DISPLAY_NAME ORDER BY M.FACTOR ASC SEPARATOR ', ')" +
        "FROM   INGREDIENT I, INGREDIENT_MEASURE IM, MEASURE M " +
        "WHERE  IM.MEASURE_NR = M.MEASURE_NR " +
        "AND    IM.INGREDIENT_NR = I.INGREDIENT_NR ");
    if (searchFormData.getIngredient().getValue() != null) {
      statement.append("AND UPPER(I.INGREDIENT_NAME) LIKE UPPER(CONCAT('%',:ingredient,'%')) ");
    }
    statement.append("" +
        "GROUP BY " +
        "       I.INGREDIENT_NR, " +
        "       I.INGREDIENT_NAME" +
        "INTO " +
        " :{page.ingredientNr}, " +
        " :{page.ingredient}, " +
        " :{page.measure} ");
    SQL.selectInto(statement.toString(), searchFormData, new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getRecipeIngredientsTableData(Long recipeNr) {
    RecipeIngredientsTablePageData page = new RecipeIngredientsTablePageData();
    SQL.selectInto("" +
        "SELECT RI.RECIPE_INGREDIENT_NR, " +
        "       RI.INGREDIENT_NR, " +
        "       RI.QUANTITY, " +
        "       RI.MEASURE_NR, " +
        "       I.INGREDIENT_NAME " +
        "FROM   RECIPE_INGREDIENT RI, " +
        "       INGREDIENT I " +
        "WHERE  RECIPE_NR = :recipeNr " +
        "AND    I.INGREDIENT_NR = RI.INGREDIENT_NR" +
        "INTO " +
        " :{page.recipeIngredientNr}, " +
        " :{page.ingredientNr}, " +
        " :{page.quantity}, " +
        " :{page.measure}, " +
        " :{page.name} ",
        new NVPair("recipeNr", recipeNr),
        new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getRecipeStepTableData(Long recipeNr) {
    RecipeStepsTablePageData page = new RecipeStepsTablePageData();
    SQL.selectInto("" +
        "SELECT S.RECIPE_STEP_NR, " +
        "       S.STEP_ORDER, " +
        "       NULL, " +
        "       S.DESCRIPTION " +
        "FROM   RECIPE_STEP S " +
        "WHERE  RECIPE_NR = :recipeNr " +
        "INTO " +
        " :{page.recipeStepNr}, " +
        " :{page.step}, " +
        " :{page.image}, " +
        " :{page.description} ",
        new NVPair("recipeNr", recipeNr),
        new NVPair("page", page));
    return page;
  }

  @Override
  public AbstractTablePageData getMeasureTableData() {
    MeasureTablePageData page = new MeasureTablePageData();
    SQL.selectInto("" +
        "SELECT M.MEASURE_NR, " +
        "       M.MEASURE_NAME, " +
        "       M.ACRONYM, " +
        "       RM.DISPLAY_NAME, " +
        "       M.FACTOR " +
        "FROM   MEASURE M LEFT OUTER JOIN MEASURE RM ON M.REFERENCE_MEASURE_NR = RM.MEASURE_NR " +
        "INTO " +
        " :{page.measureNr}, " +
        " :{page.name}, " +
        " :{page.acronym}, " +
        " :{page.referenceUnit}, " +
        " :{page.factor} ",
        new NVPair("page", page));
    return page;
  }

}
