package ch.pproject.cookbook.server.measure;

import org.eclipse.scout.rt.server.jdbc.SQL;

import ch.pproject.cookbook.server.services.ISequences;
import ch.pproject.cookbook.shared.measure.IMeasureService;
import ch.pproject.cookbook.shared.measure.MeasureFormData;

public class MeasureService implements IMeasureService {

  @Override
  public MeasureFormData prepareCreate(MeasureFormData formData) {
    return formData;
  }

  @Override
  public MeasureFormData create(MeasureFormData formData) {
    formData.setMeasureNr(SQL.getSequenceNextval(ISequences.CBSEQ));

    SQL.insert("" +
        "INSERT INTO MEASURE (" +
        " MEASURE_NR, " +
        " MEASURE_NAME, " +
        " DISPLAY_NAME, " +
        " ACRONYM, " +
        " REFERENCE_MEASURE_NR, " +
        " FACTOR) " +
        "SELECT " +
        " :measureNr, " +
        " :name, " +
        " CONCAT('', :name, CONCAT(' (', :acronym, ')')), " +
        " :acronym, " +
        " :refernceMeasure, " +
        " :factor " +
        "FROM DUAL ",
        formData);

    return formData;
  }

  @Override
  public MeasureFormData load(MeasureFormData formData) {
    SQL.selectInto("" +
        "SELECT " +
        " MEASURE_NAME, " +
        " ACRONYM, " +
        " REFERENCE_MEASURE_NR, " +
        " FACTOR " +
        "FROM MEASURE " +
        "WHERE " +
        " MEASURE_NR = :measureNr " +
        "INTO " +
        " :name, " +
        " :acronym, " +
        " :refernceMeasure, " +
        " :factor ",
        formData);

    return formData;
  }

  @Override
  public MeasureFormData store(MeasureFormData formData) {
    SQL.update("" +
        "UPDATE MEASURE SET " +
        " MEASURE_NAME = :name, " +
        " ACRONYM = :acronym, " +
        " DISPLAY_NAME = CONCAT('', :name, CONCAT(' (', :acronym, ')')), " +
        " REFERENCE_MEASURE_NR = :refernceMeasure, " +
        " FACTOR = :factor " +
        "WHERE " +
        " MEASURE_NR = :measureNr ",
        formData);

    return formData;
  }
}
