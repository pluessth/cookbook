package ch.pproject.cookbook.client.ingredient;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.cookbook.client.ingredient.IngredientSearchForm.MainBox.ResetButton;
import ch.pproject.cookbook.client.ingredient.IngredientSearchForm.MainBox.SearchButton;
import ch.pproject.cookbook.client.ingredient.IngredientSearchForm.MainBox.TabBox;
import ch.pproject.cookbook.client.ingredient.IngredientSearchForm.MainBox.TabBox.FieldBox;
import ch.pproject.cookbook.client.ingredient.IngredientSearchForm.MainBox.TabBox.FieldBox.IngredientField;
import ch.pproject.cookbook.shared.ingredient.IngredientSearchFormData;

@FormData(value = IngredientSearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class IngredientSearchForm extends AbstractSearchForm {

  public IngredientSearchForm() {
    super();
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_E;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Ingredient");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) {
    super.execResetSearchFilter(searchFilter);
    IngredientSearchFormData formData = new IngredientSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }
//
//  @Override
//  public void startSearch() {
//  	// FIXME pluessth
//    startInternal(new SearchHandler());
//  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public IngredientField getIngredientField() {
    return getFieldByClass(IngredientField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      public class FieldBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("searchCriteria");
        }

        @Order(20.0)
        public class IngredientField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Ingredient");
          }

          @Override
          protected int getConfiguredMaxLength() {
            return 60;
          }
        }
      }
    }

    @Order(20.0)
    public class ResetButton extends AbstractResetButton {
    }

    @Order(30.0)
    public class SearchButton extends AbstractSearchButton {
    }
  }

  public class SearchHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      //TODO [tpu] Auto-generated method stub.
    }
  }
}
