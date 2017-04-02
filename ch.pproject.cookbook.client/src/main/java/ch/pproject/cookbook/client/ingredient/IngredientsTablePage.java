package ch.pproject.cookbook.client.ingredient;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.ISearchForm;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.cookbook.shared.ingredient.IngredientSearchFormData;
import ch.pproject.cookbook.shared.ingredient.IngredientsTablePageData;
import ch.pproject.cookbook.shared.services.IStandardOutlineService;

@PageData(IngredientsTablePageData.class)
public class IngredientsTablePage extends AbstractPageWithTable<IngredientsTablePage.Table> {

  @Override
  protected Class<? extends ISearchForm> getConfiguredSearchForm() {
    return IngredientSearchForm.class;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Ingredients");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    AbstractTablePageData pageData = BEANS.get(IStandardOutlineService.class).getIngredientTableData((IngredientSearchFormData) filter.getFormData());
    importPageData(pageData);
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    public IngredientColumn getIngredientColumn() {
      return getColumnSet().getColumnByClass(IngredientColumn.class);
    }

    public IngredientNrColumn getIngredientNrColumn() {
      return getColumnSet().getColumnByClass(IngredientNrColumn.class);
    }

    public MeasureColumn getMeasureColumn() {
      return getColumnSet().getColumnByClass(MeasureColumn.class);
    }

    @Order(10.0)
    public class IngredientNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }
    }

    @Order(20.0)
    public class IngredientColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Ingredient");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 60;
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(30.0)
    public class MeasureColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Measure");
      }

      @Override
      protected int getConfiguredWidth() {
        return 400;
      }
    }

    @Order(10.0)
    public class NewIngredientMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewIngredient_");
      }

      @Override
      protected void execAction() {
        IngredientForm form = new IngredientForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    public class EditIngredientMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditIngredient_");
      }

      @Override
      protected void execAction() {
        IngredientForm form = new IngredientForm();
        form.setIngredientNr(getIngredientNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
