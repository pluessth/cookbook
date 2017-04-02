package ch.pproject.cookbook.server.ingredient;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.LongArrayHolder;
import org.eclipse.scout.rt.platform.holders.LongHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import ch.pproject.cookbook.server.services.ISequences;
import ch.pproject.cookbook.shared.ingredient.IIngredientProcessService;
import ch.pproject.cookbook.shared.ingredient.IngredientFormData;

public class IngredientService implements IIngredientProcessService {

  @Override
  public IngredientFormData prepareCreate(IngredientFormData formData) {
    return formData;
  }

  @Override
  public IngredientFormData create(IngredientFormData formData) {
    LongHolder exists = new LongHolder();
    SQL.selectInto(
        "SELECT COUNT(1) " +
            "FROM INGREDIENT " +
            "WHERE INGREDIENT_NAME = :name " +
            "INTO :exists ",
        formData, new NVPair("exists", exists));

    if (exists.getValue() > 0) {
      throw new VetoException("Es existiert bereits eine Zutat mit dem Namen \"" + formData.getName().getValue() + "\"");
    }

    formData.setIngredientNr(SQL.getSequenceNextval(ISequences.CBSEQ));
    SQL.insert(
        "INSERT INTO INGREDIENT (" +
            "INGREDIENT_NR, " +
            "INGREDIENT_NAME " +
            ")  " +
            "VALUES (" +
            ":ingredientNr, " +
            ":name) ",
        formData);

    if (formData.getMeasure().getValue() != null) {
      SQL.insert("" +
          "INSERT  INTO INGREDIENT_MEASURE(INGREDIENT_NR, MEASURE_NR) " +
          "SELECT  :ingredientNr, MEASURE_NR " +
          "FROM MEASURE " +
          "WHERE REFERENCE_MEASURE_NR IN (:{measure}) ", formData);
    }

    return formData;
  }

  @Override
  public IngredientFormData load(IngredientFormData formData) {
    SQL.selectInto(
        "SELECT INGREDIENT_NAME " +
            "FROM INGREDIENT " +
            "WHERE INGREDIENT_NR = :ingredientNr " +
            "INTO :name ",
        formData);

    SQL.selectInto("" +
        "SELECT  MEASURE_NR " +
        "FROM    INGREDIENT_MEASURE " +
        "WHERE   INGREDIENT_NR = :ingredientNr " +
        "INTO    :measure ", formData);

    return formData;
  }

  @Override
  public IngredientFormData store(IngredientFormData formData) {
    SQL.update(
        "UPDATE INGREDIENT " +
            "SET INGREDIENT_NAME = :name " +
            "WHERE INGREDIENT_NR = :ingredientNr ",
        formData);

    StringArrayHolder referencedMeasures = new StringArrayHolder();
    LongArrayHolder measures = new LongArrayHolder(formData.getMeasure().getValue().toArray(new Long[]{}));
    SQL.selectInto(
        "SELECT MRII.DISPLAY_NAME " +
            "FROM RECIPE_INGREDIENT RI, " +
            "     MEASURE MRI, " +
            "     MEASURE MRII " +
            "WHERE RI.INGREDIENT_NR = :ingredientNr " +
            "AND   RI.MEASURE_NR = MRI.MEASURE_NR " +
            "AND   MRII.MEASURE_NR = MRI.REFERENCE_MEASURE_NR " +
            "AND   MRI.REFERENCE_MEASURE_NR NOT IN :measures " +
            "INTO :referencedMeasures ",
        formData, new NVPair("measures", measures), new NVPair("referencedMeasures", referencedMeasures));

    if (CollectionUtility.hasElements(referencedMeasures.getValue())) {
      StringBuilder message = new StringBuilder("Folgende Masseinheiten k�nnen nicht entfernt werden, da sie noch verwendet werden:");
      for (String referenceMeasureName : referencedMeasures.getValue()) {
        message.append("\n\t - " + referenceMeasureName);
      }
      throw new VetoException(message.toString());
    }

    SQL.delete("" +
        "DELETE FROM INGREDIENT_MEASURE WHERE INGREDIENT_NR = :ingredientNr", formData);

    SQL.insert("" +
        "INSERT  INTO INGREDIENT_MEASURE(INGREDIENT_NR, MEASURE_NR) " +
        "SELECT  :ingredientNr, MEASURE_NR " +
        "FROM MEASURE " +
        "WHERE REFERENCE_MEASURE_NR IN (:{measure}) ", formData);

    return formData;
  }
}
