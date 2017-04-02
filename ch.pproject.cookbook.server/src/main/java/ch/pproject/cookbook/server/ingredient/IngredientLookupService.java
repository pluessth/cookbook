package ch.pproject.cookbook.server.ingredient;

import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

import ch.pproject.cookbook.shared.ingredient.IIngredientLookupService;

public class IngredientLookupService extends AbstractSqlLookupService<Long> implements IIngredientLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "" +
        "SELECT INGREDIENT_NR, " +
        "       INGREDIENT_NAME " +
        "FROM   INGREDIENT " +
        "WHERE  1=1 " +
        "<key>   AND     INGREDIENT_NR = :key </key> " +
        "<text>  AND     UPPER(INGREDIENT_NAME) LIKE UPPER(:text) </text> " +
        "<all> </all>";
  }
}
