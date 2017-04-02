package ch.pproject.cookbook.shared.recipe.step;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 */
@Generated(value = "ch.pproject.cookbook.client.recipe.step.RecipeStepsTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class RecipeStepsTablePageData extends AbstractTablePageData {

  private static final long serialVersionUID = 1L;

  @Override
  public RecipeStepsTableRowData addRow() {
    return (RecipeStepsTableRowData) super.addRow();
  }

  @Override
  public RecipeStepsTableRowData addRow(int rowState) {
    return (RecipeStepsTableRowData) super.addRow(rowState);
  }

  @Override
  public RecipeStepsTableRowData createRow() {
    return new RecipeStepsTableRowData();
  }

  @Override
  public Class<? extends AbstractTableRowData> getRowType() {
    return RecipeStepsTableRowData.class;
  }

  @Override
  public RecipeStepsTableRowData[] getRows() {
    return (RecipeStepsTableRowData[]) super.getRows();
  }

  @Override
  public RecipeStepsTableRowData rowAt(int index) {
    return (RecipeStepsTableRowData) super.rowAt(index);
  }

  public void setRows(RecipeStepsTableRowData[] rows) {
    super.setRows(rows);
  }

  public static class RecipeStepsTableRowData extends AbstractTableRowData {

    private static final long serialVersionUID = 1L;
    public static final String recipeStepNr = "recipeStepNr";
    public static final String step = "step";
    public static final String image = "image";
    public static final String description = "description";
    private Long m_recipeStepNr;
    private Integer m_step;
    private String m_image;
    private String m_description;

    public Long getRecipeStepNr() {
      return m_recipeStepNr;
    }

    public void setRecipeStepNr(Long newRecipeStepNr) {
      m_recipeStepNr = newRecipeStepNr;
    }

    public Integer getStep() {
      return m_step;
    }

    public void setStep(Integer newStep) {
      m_step = newStep;
    }

    public String getImage() {
      return m_image;
    }

    public void setImage(String newImage) {
      m_image = newImage;
    }

    public String getDescription() {
      return m_description;
    }

    public void setDescription(String newDescription) {
      m_description = newDescription;
    }
  }
}