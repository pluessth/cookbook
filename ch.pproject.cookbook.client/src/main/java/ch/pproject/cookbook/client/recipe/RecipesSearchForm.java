package ch.pproject.cookbook.client.recipe;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.ResetButton;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.SearchButton;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.ExtendedBox;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.ExtendedBox.FilterField;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.ExtendedBox.IngredientsField;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.ExtendedBox.SelectedIngredientsField;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.FieldBox;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.FieldBox.DifficultyField;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.FieldBox.PreparationTimeBox;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.FieldBox.PreparationTimeBox.PreparationTimeFromField;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.FieldBox.PreparationTimeBox.PreparationTimeToField;
import ch.pproject.cookbook.client.recipe.RecipesSearchForm.MainBox.TabBox.FieldBox.RecipeNameField;
import ch.pproject.cookbook.shared.ingredient.IngredientLookupCall;
import ch.pproject.cookbook.shared.recipe.DifficultyCodeType;
import ch.pproject.cookbook.shared.recipe.RecipesSearchFormData;

@FormData(value = RecipesSearchFormData.class, sdkCommand = SdkCommand.CREATE)
public class RecipesSearchForm extends AbstractSearchForm {

  private Set<Long> m_selectedIngredientNrs = CollectionUtility.emptyHashSet();

  public RecipesSearchForm() {
    super();
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_E;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Recipes");
  }

  @Override
  protected void execResetSearchFilter(SearchFilter searchFilter) {
    super.execResetSearchFilter(searchFilter);
    RecipesSearchFormData formData = new RecipesSearchFormData();
    exportFormData(formData);
    searchFilter.setFormData(formData);
  }

  @FormData
  public Set<Long> getSelectedIngredientNrs() {
    return m_selectedIngredientNrs;
  }

  @FormData
  public void setSelectedIngredientNrs(Set<Long> selectedIngredientNrs) {
    m_selectedIngredientNrs = selectedIngredientNrs;
  }

  public DifficultyField getDifficultyField() {
    return getFieldByClass(DifficultyField.class);
  }

  /**
   * @return the ExtendedBox
   */
  public ExtendedBox getExtendedBox() {
    return getFieldByClass(ExtendedBox.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  /**
   * @return the FilterField
   */
  public FilterField getFilterField() {
    return getFieldByClass(FilterField.class);
  }

  /**
   * @return the IngredientsField
   */
  public IngredientsField getIngredientsField() {
    return getFieldByClass(IngredientsField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public PreparationTimeBox getPreparationTimeBox() {
    return getFieldByClass(PreparationTimeBox.class);
  }

  public PreparationTimeFromField getPreparationTimeFromField() {
    return getFieldByClass(PreparationTimeFromField.class);
  }

  public PreparationTimeToField getPreparationTimeToField() {
    return getFieldByClass(PreparationTimeToField.class);
  }

  public RecipeNameField getRecipeNameField() {
    return getFieldByClass(RecipeNameField.class);
  }

  public ResetButton getResetButton() {
    return getFieldByClass(ResetButton.class);
  }

  public SearchButton getSearchButton() {
    return getFieldByClass(SearchButton.class);
  }

  /**
   * @return the SelectedIngredientsField
   */
  public SelectedIngredientsField getSelectedIngredientsField() {
    return getFieldByClass(SelectedIngredientsField.class);
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
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Easy");
        }

        @Order(10.0)
        public class RecipeNameField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("RecipeName");
          }
        }

        @Order(20.0)
        public class PreparationTimeBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("PreparationTime");
          }

          @Order(10.0)
          public class PreparationTimeFromField extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("from");
            }
          }

          @Order(20.0)
          public class PreparationTimeToField extends AbstractLongField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("to");
            }
          }
        }

        @Order(30.0)
        public class DifficultyField extends AbstractListBox<Long> {

          @Override
          protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
            return DifficultyCodeType.class;
          }

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Difficulty");
          }
        }
      }

      @Order(20.0)
      public class ExtendedBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ingredients");
        }

        @Order(10.0)
        public class FilterField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Filter");
          }

          // FIXME pluessth
//           @Override
//          protected boolean getConfiguredValidateOnAnyKey() {
//            return true;
//          }
        }

        @Order(20.0)
        public class IngredientsField extends AbstractListBox<Long> {

          @Override
          protected int getConfiguredGridH() {
            return 4;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Ingredients");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return IngredientLookupCall.class;
          }

          @Override
          protected Class<? extends IValueField<?>> getConfiguredMasterField() {
            return RecipesSearchForm.MainBox.TabBox.ExtendedBox.FilterField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            execPopulateTable();
          }

          @Override
          protected void execChangedValue() {
            getSelectedIngredientNrs().addAll(getValue());
            execPopulateTable();
          }

          @Override
          protected void execFilterLookupResult(ILookupCall<Long> call, List<ILookupRow<Long>> result) {
            List<ILookupRow<Long>> markedRows = CollectionUtility.emptyArrayList();
            Iterator<ILookupRow<Long>> lookupRowIterator = result.iterator();
            while (lookupRowIterator.hasNext()) {
              ILookupRow<Long> lookupRow = lookupRowIterator.next();
              // remove if already selected
              if (CollectionUtility.containsAny(getSelectedIngredientNrs(), lookupRow.getKey())) {
                markedRows.add(lookupRow);
                continue;
              }
              // remove if not contained in filter
              if (StringUtility.hasText(getFilterField().getValue())) {
                if (!StringUtility.contains(lookupRow.getText(), getFilterField().getValue())) {
                  markedRows.add(lookupRow);
                  continue;
                }
              }
            }
            result.removeAll(markedRows);
          }
        }

        @Order(30.0)
        public class SelectedIngredientsField extends AbstractListBox<Long> {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SelectedIngredients");
          }

          @Override
          protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
            return IngredientLookupCall.class;
          }

          @Override
          protected Class<? extends IValueField<?>> getConfiguredMasterField() {
            return RecipesSearchForm.MainBox.TabBox.ExtendedBox.IngredientsField.class;
          }

          @Override
          protected void execChangedMasterValue(Object newMasterValue) {
            execPopulateTable();
            setValueChangeTriggerEnabled(false);
            setValue(getSelectedIngredientNrs());
            setValueChangeTriggerEnabled(true);
          }

          @Override
          protected void execChangedValue() {
            getSelectedIngredientNrs().clear();
            getSelectedIngredientNrs().addAll(getValue());
            execPopulateTable();
            getIngredientsField().execChangedValue();
          }

          @Override
          protected void execFilterLookupResult(ILookupCall<Long> call, List<ILookupRow<Long>> result) {
            List<ILookupRow<Long>> markedRows = CollectionUtility.emptyArrayList();
            Iterator<ILookupRow<Long>> lookupRowIterator = result.iterator();
            while (lookupRowIterator.hasNext()) {
              ILookupRow<Long> lookupRow = lookupRowIterator.next();
              // remove all non-checked
              if (!CollectionUtility.containsAny(getSelectedIngredientNrs(), lookupRow.getKey())) {
                markedRows.add(lookupRow);
              }
            }
            result.removeAll(markedRows);
          }

          @Override
          public void resetValue() {
            getSelectedIngredientNrs().clear();
            super.resetValue();
          }
        }
      }
    }

    @Order(20.0)
    public class ResetButton extends AbstractResetButton {

      @Override
      protected int getConfiguredVerticalAlignment() {
        return 1;
      }
    }

    @Order(30.0)
    public class SearchButton extends AbstractSearchButton {

      @Override
      protected int getConfiguredVerticalAlignment() {
        return 1;
      }
    }
  }

  public class SearchHandler extends AbstractFormHandler {
  }
}
