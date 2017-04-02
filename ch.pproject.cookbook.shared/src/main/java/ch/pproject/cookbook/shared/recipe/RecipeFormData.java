package ch.pproject.cookbook.shared.recipe;

import java.math.BigDecimal;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.pproject.cookbook.client.recipe.RecipeForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class RecipeFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  public Difficulty getDifficulty() {
    return getFieldByClass(Difficulty.class);
  }

  public Ingredients getIngredients() {
    return getFieldByClass(Ingredients.class);
  }

  public Notes getNotes() {
    return getFieldByClass(Notes.class);
  }

  public Portions getPortions() {
    return getFieldByClass(Portions.class);
  }

  public PreparationTime getPreparationTime() {
    return getFieldByClass(PreparationTime.class);
  }

  public RecipeName getRecipeName() {
    return getFieldByClass(RecipeName.class);
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

  public static class Difficulty extends AbstractValueFieldData<Long> {

    private static final long serialVersionUID = 1L;
  }

  public static class Ingredients extends AbstractTableFieldBeanData {

    private static final long serialVersionUID = 1L;

    @Override
    public IngredientsRowData addRow() {
      return (IngredientsRowData) super.addRow();
    }

    @Override
    public IngredientsRowData addRow(int rowState) {
      return (IngredientsRowData) super.addRow(rowState);
    }

    @Override
    public IngredientsRowData createRow() {
      return new IngredientsRowData();
    }

    @Override
    public Class<? extends AbstractTableRowData> getRowType() {
      return IngredientsRowData.class;
    }

    @Override
    public IngredientsRowData[] getRows() {
      return (IngredientsRowData[]) super.getRows();
    }

    @Override
    public IngredientsRowData rowAt(int index) {
      return (IngredientsRowData) super.rowAt(index);
    }

    public void setRows(IngredientsRowData[] rows) {
      super.setRows(rows);
    }

    public static class IngredientsRowData extends AbstractTableRowData {

      private static final long serialVersionUID = 1L;
      public static final String recipeIngredientNr = "recipeIngredientNr";
      public static final String ingredient = "ingredient";
      public static final String quantity = "quantity";
      public static final String measure = "measure";
      private Long m_recipeIngredientNr;
      private Long m_ingredient;
      private BigDecimal m_quantity;
      private Long m_measure;

      public Long getRecipeIngredientNr() {
        return m_recipeIngredientNr;
      }

      public void setRecipeIngredientNr(Long newRecipeIngredientNr) {
        m_recipeIngredientNr = newRecipeIngredientNr;
      }

      public Long getIngredient() {
        return m_ingredient;
      }

      public void setIngredient(Long newIngredient) {
        m_ingredient = newIngredient;
      }

      public BigDecimal getQuantity() {
        return m_quantity;
      }

      public void setQuantity(BigDecimal newQuantity) {
        m_quantity = newQuantity;
      }

      public Long getMeasure() {
        return m_measure;
      }

      public void setMeasure(Long newMeasure) {
        m_measure = newMeasure;
      }
    }
  }

  public static class Notes extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class Portions extends AbstractValueFieldData<Long> {

    private static final long serialVersionUID = 1L;
  }

  public static class PreparationTime extends AbstractValueFieldData<Long> {

    private static final long serialVersionUID = 1L;
  }

  public static class RecipeName extends AbstractValueFieldData<String> {

    private static final long serialVersionUID = 1L;
  }

  public static class RecipeNrProperty extends AbstractPropertyData<Long> {

    private static final long serialVersionUID = 1L;
  }
}
