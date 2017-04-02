package ch.pproject.cookbook.server.recipe.step;

import java.util.Collections;
import java.util.List;

import org.eclipse.scout.rt.platform.holders.LongArrayHolder;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import ch.pproject.cookbook.server.services.ISequences;
import ch.pproject.cookbook.shared.recipe.RecipeStepFormData;
import ch.pproject.cookbook.shared.recipe.step.IRecipeStepService;

public class RecipeStepService implements IRecipeStepService {

  @Override
  public RecipeStepFormData prepareCreate(RecipeStepFormData formData) {
    return formData;
  }

  @Override
  public RecipeStepFormData create(RecipeStepFormData formData) {
    if (formData.getRecipeStepNr() == null) {
      formData.setRecipeStepNr(SQL.getSequenceNextval(ISequences.CBSEQ));
    }

    SQL.insert("" +
        "INSERT INTO RECIPE_STEP (" +
        "RECIPE_STEP_NR, " +
        "RECIPE_NR, " +
        "STEP_ORDER, " +
        "DESCRIPTION " +
        ")  " +
        "SELECT " +
        ":recipeStepNr, " +
        ":recipeNr, " +
        "COALESCE(MAX(STEP_ORDER), 0) + 1, " +
        ":description " +
        "FROM RECIPE_STEP " +
        "WHERE RECIPE_NR = :recipeNr ", formData);

    return formData;
  }

  @Override
  public RecipeStepFormData load(RecipeStepFormData formData) {
    SQL.selectInto("" +
        "SELECT " +
        "     RECIPE_NR, " +
        "     STEP_ORDER, " +
        "     DESCRIPTION " +
        "FROM RECIPE_STEP " +
        "WHERE RECIPE_STEP_NR = :recipeStepNr " +
        "INTO :recipeNr, " +
        "     :order, " +
        "     :description ", formData);

    return formData;
  }

  @Override
  public RecipeStepFormData store(RecipeStepFormData formData) {
    SQL.update("" +
        "UPDATE RECIPE_STEP " +
        "SET DESCRIPTION = :description " +
        "WHERE RECIPE_STEP_NR = :recipeStepNr ", formData);
    return formData;
  }

  @Override
  public List<RecipeStepFormData> getAllRecipeSteps(Long recipeNr) {
    LongArrayHolder stepNrHolder = new LongArrayHolder();
    SQL.selectInto("" +
        "SELECT " +
        "     RECIPE_STEP_NR " +
        "FROM RECIPE_STEP " +
        "WHERE RECIPE_NR = :recipeNr " +
        "ORDER BY STEP_ORDER ASC " +
        "INTO :{recipeStepNr} ",
        new NVPair("recipeNr", recipeNr),
        new NVPair("recipeStepNr", stepNrHolder));

    List<RecipeStepFormData> result = Collections.emptyList();
    if (CollectionUtility.hasElements(stepNrHolder.getValue())) {
      RecipeStepFormData formData = new RecipeStepFormData();
      SQL.selectInto("" +
          "SELECT " +
          "     RECIPE_NR, " +
          "     STEP_ORDER, " +
          "     DESCRIPTION " +
          "FROM RECIPE_STEP " +
          "WHERE RECIPE_STEP_NR = :recipeStepNr " +
          "INTO :recipeNr, " +
          "     :order " +
          "     :description ",
          formData);
      result.add(formData);
    }

    return result;
  }

  @Override
  public void moveStepUp(Long recipeStepNr) {
    RecipeStepFormData formData = new RecipeStepFormData();
    formData.setRecipeStepNr(recipeStepNr);
    formData = load(formData);

    LongHolder previousStepNrHolder = new LongHolder();
    SQL.selectInto("" +
        "SELECT " +
        "     RECIPE_STEP_NR " +
        "FROM RECIPE_STEP RS " +
        "WHERE RS.RECIPE_NR = :recipeNr " +
        "AND RS.STEP_ORDER = (SELECT MAX(STEP_ORDER) " +
        "                FROM RECIPE_STEP " +
        "                WHERE STEP_ORDER < :order " +
        "                AND RECIPE_NR = :recipeNr) " +
        "INTO :previousStepNr ",
        formData,
        new NVPair("previousStepNr", previousStepNrHolder));

    if (previousStepNrHolder.getValue() != null) {
      SQL.update("" +
          "UPDATE RECIPE_STEP " +
          "SET STEP_ORDER = STEP_ORDER - 1 " +
          "WHERE RECIPE_STEP_NR = :recipeStepNr ",
          new NVPair("recipeStepNr", recipeStepNr));

      SQL.update("" +
          "UPDATE RECIPE_STEP " +
          "SET STEP_ORDER = STEP_ORDER + 1 " +
          "WHERE RECIPE_STEP_NR = :recipeStepNr ",
          new NVPair("recipeStepNr", previousStepNrHolder));
    }
  }

  @Override
  public void moveStepDown(Long recipeStepNr) {
    RecipeStepFormData formData = new RecipeStepFormData();
    formData.setRecipeStepNr(recipeStepNr);
    formData = load(formData);

    LongHolder previousStepNrHolder = new LongHolder();
    SQL.selectInto("" +
        "SELECT " +
        "     RECIPE_STEP_NR " +
        "FROM RECIPE_STEP RS " +
        "WHERE RS.RECIPE_NR = :recipeNr " +
        "AND RS.STEP_ORDER = (SELECT MIN(STEP_ORDER) " +
        "                FROM RECIPE_STEP " +
        "                WHERE STEP_ORDER > :order " +
        "                AND RECIPE_NR = :recipeNr) " +
        "INTO :previousStepNr ",
        formData,
        new NVPair("previousStepNr", previousStepNrHolder));

    if (previousStepNrHolder.getValue() != null) {
      SQL.update("" +
          "UPDATE RECIPE_STEP " +
          "SET STEP_ORDER = STEP_ORDER + 1 " +
          "WHERE RECIPE_STEP_NR = :recipeStepNr ",
          new NVPair("recipeStepNr", recipeStepNr));

      SQL.update("" +
          "UPDATE RECIPE_STEP " +
          "SET STEP_ORDER = STEP_ORDER - 1 " +
          "WHERE RECIPE_STEP_NR = :recipeStepNr ",
          new NVPair("recipeStepNr", previousStepNrHolder));
    }
  }
}
