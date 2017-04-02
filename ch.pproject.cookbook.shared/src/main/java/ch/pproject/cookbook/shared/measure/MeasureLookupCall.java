package ch.pproject.cookbook.shared.measure;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

public class MeasureLookupCall extends LookupCall<Long> {

  private static final long serialVersionUID = 1L;
  private boolean m_isReference;
  private Long m_ingredientNr;

  @Override
  protected Class<? extends ILookupService<Long>> getConfiguredService() {
    return IMeasureLookupService.class;
  }

  public boolean isIsReference() {
    return m_isReference;
  }

  public void setIsReference(boolean isReference) {
    m_isReference = isReference;
  }

  public Long getIngredientNr() {
    return m_ingredientNr;
  }

  public void setIngredientNr(Long ingredientNr) {
    m_ingredientNr = ingredientNr;
  }
}
