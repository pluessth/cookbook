package ch.pproject.cookbook.server.measure;

import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

import ch.pproject.cookbook.shared.measure.IMeasureLookupService;

public class MeasureLookupService extends AbstractSqlLookupService<Long> implements IMeasureLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return "" +
        "SELECT M.MEASURE_NR, " +
        "       M.DISPLAY_NAME " +
        "FROM   MEASURE M " +
        "WHERE  (NOT :isReference OR FACTOR = 1) " +
        "AND    (:ingredientNr IS NULL OR EXISTS (SELECT 1 FROM INGREDIENT_MEASURE IM WHERE M.MEASURE_NR = IM.MEASURE_NR AND :ingredientNr = IM.INGREDIENT_NR)) " +
        "<key>   AND     MEASURE_NR = :key </key> " +
        "<text>  AND     UPPER(DISPLAY_NAME) LIKE UPPER(:text) </text> " +
        "<all> </all>";
  }
}
