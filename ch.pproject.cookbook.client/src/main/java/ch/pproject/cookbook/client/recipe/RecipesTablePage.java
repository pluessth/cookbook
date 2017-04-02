package ch.pproject.cookbook.client.recipe;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.cookbook.client.wizard.CookWizard;
import ch.pproject.cookbook.shared.recipe.DifficultyCodeType;
import ch.pproject.cookbook.shared.recipe.RecipesSearchFormData;
import ch.pproject.cookbook.shared.recipe.RecipesTablePageData;
import ch.pproject.cookbook.shared.services.IStandardOutlineService;

@PageData(RecipesTablePageData.class)
public class RecipesTablePage extends AbstractPageWithTable<RecipesTablePage.Table> {

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return RecipesSearchForm.class;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Recipes");
  }

  @Override
  protected IPage<?> execCreateChildPage(ITableRow row) {
    RecipeNodePage childPage = new RecipeNodePage(getTable().getRecipeNrColumn().getValue(row));
    return childPage;
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    AbstractTablePageData pageData = BEANS.get(IStandardOutlineService.class).getRecipesTableData((RecipesSearchFormData) filter.getFormData());
    importPageData(pageData);
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    /**
     * @return the PortionsColumn
     */
    public PortionsColumn getPortionsColumn() {
      return getColumnSet().getColumnByClass(PortionsColumn.class);
    }

    public DifficultyColumn getDifficultyColumn() {
      return getColumnSet().getColumnByClass(DifficultyColumn.class);
    }

    public PreparationTimeColumn getPreparationTimeColumn() {
      return getColumnSet().getColumnByClass(PreparationTimeColumn.class);
    }

    public RecipeNameColumn getRecipeNameColumn() {
      return getColumnSet().getColumnByClass(RecipeNameColumn.class);
    }

    public RecipeNrColumn getRecipeNrColumn() {
      return getColumnSet().getColumnByClass(RecipeNrColumn.class);
    }

    @Order(10.0)
    public class RecipeNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("RecipeNr");
      }
    }

    @Order(20.0)
    public class RecipeNameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("RecipeName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 300;
      }
    }

    @Order(30.0)
    public class PreparationTimeColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("PreparationTime");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(40.0)
    public class DifficultyColumn extends AbstractSmartColumn<Long> {

      @Override
      protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
        return DifficultyCodeType.class;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Difficulty");
      }

      @Override
      protected int getConfiguredWidth() {
        return 120;
      }
    }

    @Order(50.0)
    public class PortionsColumn extends AbstractLongColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Portions");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(10.0)
    public class NewRecipeMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewRecipe_");
      }

      @Override
      protected void execAction() {
        RecipeForm form = new RecipeForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    public class EditRecipeMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditRecipe_");
      }

      @Override
      protected void execAction() {
        RecipeForm form = new RecipeForm();
        form.setRecipeNr(getRecipeNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(30.0)
    public class SeparatorMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredSeparator() {
        return true;
      }
    }

    @Order(40.0)
    public class CookMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Cook");
      }

      @Override
      protected void execAction() {
        new CookWizard(getRecipeNrColumn().getSelectedValue());
      }
    }
  }
}
