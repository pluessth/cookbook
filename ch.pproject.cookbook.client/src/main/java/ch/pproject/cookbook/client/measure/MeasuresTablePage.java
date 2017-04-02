package ch.pproject.cookbook.client.measure;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.cookbook.shared.measure.MeasureTablePageData;
import ch.pproject.cookbook.shared.services.IStandardOutlineService;

@PageData(MeasureTablePageData.class)
public class MeasuresTablePage extends AbstractPageWithTable<MeasuresTablePage.Table> {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Measures");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    AbstractTablePageData pageData = BEANS.get(IStandardOutlineService.class).getMeasureTableData();
    importPageData(pageData);
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    public AcronymColumn getAcronymColumn() {
      return getColumnSet().getColumnByClass(AcronymColumn.class);
    }

    public FactorColumn getFactorColumn() {
      return getColumnSet().getColumnByClass(FactorColumn.class);
    }

    public MeasureNrColumn getMeasureNrColumn() {
      return getColumnSet().getColumnByClass(MeasureNrColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public ReferenceUnitColumn getReferenceUnitColumn() {
      return getColumnSet().getColumnByClass(ReferenceUnitColumn.class);
    }

    @Order(10.0)
    public class MeasureNrColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }
    }

    @Order(20.0)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(30.0)
    public class AcronymColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Acronym");
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(40.0)
    public class ReferenceUnitColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("ReferenceUnit");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }

    @Order(50.0)
    public class FactorColumn extends AbstractBigDecimalColumn {

      @Override
      protected int getConfiguredFractionDigits() {
        return 5;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Factor");
      }

      @Override
      protected int getConfiguredMaxFractionDigits() {
        return 5;
      }

      @Override
      protected int getConfiguredMinFractionDigits() {
        return 0;
      }

      @Override
      protected int getConfiguredWidth() {
        return 100;
      }
    }

    @Order(10.0)
    public class NewMeasureMenu extends AbstractMenu {

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewMeasure_");
      }

      @Override
      protected void execAction() {
        MeasureForm form = new MeasureForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    public class EditMeasureMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("EditMeasure_");
      }

      @Override
      protected void execAction() {
        MeasureForm form = new MeasureForm();
        form.setMeasureNr(getMeasureNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }
}
