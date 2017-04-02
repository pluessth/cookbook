package ch.pproject.cookbook.shared.recipe;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.pproject.cookbook.client.recipe.step.RecipeStepForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class RecipeStepFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  public Description getDescription() {
    return getFieldByClass(Description.class);
  }

  /**
   * access method for property Order.
   */
  public Long getOrder() {
    return getOrderProperty().getValue();
  }

  /**
   * access method for property Order.
   */
  public void setOrder(Long order) {
    getOrderProperty().setValue(order);
  }

  public OrderProperty getOrderProperty() {
    return getPropertyByClass(OrderProperty.class);
  }

  /**
   * access method for property RecipeNr.
   */
  public Long getRecipeNr() {
    return getRecipeNrProperty().getValue();
  }

  /**
   * access method for property RecipeNr.
   */
  public void setRecipeNr(Long recipeNr) {
    getRecipeNrProperty().setValue(recipeNr);
  }

  public RecipeNrProperty getRecipeNrProperty() {
    return getPropertyByClass(RecipeNrProperty.class);
  }

  /**
   * access method for property RecipeStepNr.
   */
  public Long getRecipeStepNr() {
    return getRecipeStepNrProperty().getValue();
  }

  /**
   * access method for property RecipeStepNr.
   */
  public void setRecipeStepNr(Long recipeStepNr) {
    getRecipeStepNrProperty().setValue(recipeStepNr);
  }

  public RecipeStepNrProperty getRecipeStepNrProperty() {
    return getPropertyByClass(RecipeStepNrProperty.class);
  }

  public static class Description extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class OrderProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }

  public static class RecipeNrProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }

  public static class RecipeStepNrProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }
}
