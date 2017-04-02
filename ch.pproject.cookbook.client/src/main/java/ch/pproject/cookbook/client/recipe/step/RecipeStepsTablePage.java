package ch.pproject.cookbook.client.recipe.step;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenuSeparator;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.cookbook.shared.recipe.step.IRecipeStepService;
import ch.pproject.cookbook.shared.recipe.step.RecipeStepsTablePageData;
import ch.pproject.cookbook.shared.services.IStandardOutlineService;

@PageData(RecipeStepsTablePageData.class)
public class RecipeStepsTablePage extends AbstractPageWithTable<RecipeStepsTablePage.Table> {

  private Long m_recipeNr;

  public RecipeStepsTablePage(Long recipeNr) {
    m_recipeNr = recipeNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Steps");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    AbstractTablePageData pageData = BEANS.get(IStandardOutlineService.class).getRecipeStepTableData(getRecipeNr());
    importPageData(pageData);
  }

  @FormData
  public Long getRecipeNr() {
    return m_recipeNr;
  }

  @FormData
  public void setRecipeNr(Long recipeNr) {
    m_recipeNr = recipeNr;
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    @Override
    protected boolean getConfiguredMultilineText() {
      return true;
    }

//    @Override
//    protected int getConfiguredRowHeightHint() {
//    	// FIXME pluessth
//      return 200;
//    }

    public RecipeStepNrColumn getRecipeStepNrColumn() {
      return getColumnSet().getColumnByClass(RecipeStepNrColumn.class);
    }

    public DescriptionColumn getDescriptionColumn() {
      return getColumnSet().getColumnByClass(DescriptionColumn.class);
    }

    public ImageColumn getImageColumn() {
      return getColumnSet().getColumnByClass(ImageColumn.class);
    }

    public StepColumn getStepColumn() {
      return getColumnSet().getColumnByClass(StepColumn.class);
    }

    @Order(10.0)
    public class RecipeStepNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(20.0)
    public class StepColumn extends AbstractIntegerColumn {

      @Override
      protected boolean getConfiguredAlwaysIncludeSortAtBegin() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Step");
      }

      @Override
      protected int getConfiguredSortIndex() {
        return 1;
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(30.0)
    public class ImageColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredFixedWidth() {
        return true;
      }

      @Override
      protected int getConfiguredWidth() {
        return 200;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) {
        String imageId = getValue(row);
        // FIXME pluessth
        //        if (!Activator.getDefault().isImageCached(imageId)) {
//          // load image content by image id
//          byte[] content = BEANS.get(IImageProcessService.class).getImage(imageId);
//          Activator.getDefault().cacheImage(imageId, content);
//        }
        cell.setIconId(imageId);
        cell.setText(null);
      }
    }

    @Order(40.0)
    public class DescriptionColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Description");
      }

      @Override
      protected boolean getConfiguredTextWrap() {
        return true;
      }

      @Override
      protected int getConfiguredWidth() {
        return 800;
      }
    }

    @Order(10.0)
    public class NewStepMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewStep_");
      }

      @Override
      protected void execAction() {
        RecipeStepForm form = new RecipeStepForm();
        form.setRecipeNr(getRecipeNr());
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    public class EditStepMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditStep_");
      }

      @Override
      protected void execAction() {
        RecipeStepForm form = new RecipeStepForm();
        form.setRecipeNr(getRecipeNr());
        form.setRecipeStepNr(getRecipeStepNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(30.0)
    public class SeparatorMenu extends AbstractMenuSeparator {
    }

    @Order(40.0)
    public class ButtonMoveUpMenu extends AbstractMenu {

      @Override
      protected String getConfiguredKeyStroke() {
        return "ALT-UP";
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ButtonMoveUp");
      }

      @Override
      protected void execOwnerValueChanged(Object newOwnerValue) {
        setEnabled(getSelectedRow() != null && getSelectedRow().getRowIndex() != 0);
      }

      @Override
      protected void execAction() {
        BEANS.get(IRecipeStepService.class).moveStepUp(getRecipeStepNrColumn().getSelectedValue());
        reloadPage();
      }
    }

    @Order(50.0)
    public class ButtonMoveDownMenu extends AbstractMenu {
      @Override
      protected String getConfiguredKeyStroke() {
        return "ALT-DOWN";
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ButtonMoveDown");
      }

      @Override
      protected void execOwnerValueChanged(Object newOwnerValue) {
        setEnabled(getSelectedRow() != null && getSelectedRow().getRowIndex() < getRowCount() - 1);
      }

      @Override
      protected void execAction() {
        BEANS.get(IRecipeStepService.class).moveStepDown(getRecipeStepNrColumn().getSelectedValue());
        reloadPage();
      }
    }
  }
}
