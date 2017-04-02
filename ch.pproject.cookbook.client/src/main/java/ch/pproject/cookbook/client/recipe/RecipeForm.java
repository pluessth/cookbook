package ch.pproject.cookbook.client.recipe;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.CancelButton;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.OkButton;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.RecipeBox;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.RecipeBox.DifficultyField;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.RecipeBox.PortionsField;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.RecipeBox.PreparationTimeField;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.RecipeBox.RecipeNameField;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.TabBox;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.TabBox.IngredientsBox;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.TabBox.IngredientsBox.IngredientsField;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.TabBox.NotesBox;
import ch.pproject.cookbook.client.recipe.RecipeForm.MainBox.TabBox.NotesBox.NotesField;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm;
import ch.pproject.cookbook.shared.ingredient.IngredientLookupCall;
import ch.pproject.cookbook.shared.measure.MeasureLookupCall;
import ch.pproject.cookbook.shared.recipe.DifficultyCodeType;
import ch.pproject.cookbook.shared.recipe.IRecipeService;
import ch.pproject.cookbook.shared.recipe.RecipeFormData;

@FormData(value = RecipeFormData.class, sdkCommand = SdkCommand.CREATE)
public class RecipeForm extends AbstractForm {

  private Long m_recipeNr;

  public RecipeForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Recipe");
  }

  @FormData
  public Long getRecipeNr() {
    return m_recipeNr;
  }

  @FormData
  public void setRecipeNr(Long recipeNr) {
    this.m_recipeNr = recipeNr;
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public DifficultyField getDifficultyField() {
    return getFieldByClass(DifficultyField.class);
  }

  public IngredientsBox getIngredientsBox() {
    return getFieldByClass(IngredientsBox.class);
  }

  public IngredientsField getIngredientsField() {
    return getFieldByClass(IngredientsField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NotesBox getNotesBox() {
    return getFieldByClass(NotesBox.class);
  }

  public NotesField getNotesField() {
    return getFieldByClass(NotesField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return the PortionsField
   */
  public PortionsField getPortionsField() {
    return getFieldByClass(PortionsField.class);
  }

  public PreparationTimeField getPreparationTimeField() {
    return getFieldByClass(PreparationTimeField.class);
  }

  public RecipeBox getRecipeBox() {
    return getFieldByClass(RecipeBox.class);
  }

  public RecipeNameField getRecipeNameField() {
    return getFieldByClass(RecipeNameField.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(40.0)
    public class RecipeBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Recipe");
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(10.0)
      public class RecipeNameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RecipeName");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20.0)
      public class PreparationTimeField extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PreparationTime");
        }
      }

      @Order(30.0)
      public class DifficultyField extends AbstractSmartField<Long> {

        @Override
        protected Class<? extends ICodeType<Long, Long>> getConfiguredCodeType() {
          return DifficultyCodeType.class;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Difficulty");
        }
      }

      @Order(40.0)
      public class PortionsField extends AbstractLongField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Portions");
        }
      }
    }

    @Order(50.0)
    public class TabBox extends AbstractTabBox {

      @Override
      protected int getConfiguredGridH() {
        return 7;
      }

      @Order(10.0)
      public class IngredientsBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridH() {
          return 7;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ingredients");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class IngredientsField extends AbstractTableField<IngredientsField.Table> {

          @Override
          protected int getConfiguredGridH() {
            return 7;
          }

          public class Table extends AbstractTable {

            public IngredientColumn getIngredientColumn() {
              return getColumnSet().getColumnByClass(IngredientColumn.class);
            }

            public RecipeIngredientNrColumn getIngredientRecipeNrColumn() {
              return getColumnSet().getColumnByClass(RecipeIngredientNrColumn.class);
            }

            public MeasureColumn getMeasureColumn() {
              return getColumnSet().getColumnByClass(MeasureColumn.class);
            }

            public QuantityColumn getQuantityColumn() {
              return getColumnSet().getColumnByClass(QuantityColumn.class);
            }

            @Order(5.0)
            public class RecipeIngredientNrColumn extends AbstractLongColumn {

              @Override
              protected boolean getConfiguredDisplayable() {
                return false;
              }
            }

            @Order(10.0)
            public class IngredientColumn extends AbstractSmartColumn<Long> {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Name");
              }

              @Override
              protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                return IngredientLookupCall.class;
              }

              @Override
              protected int getConfiguredWidth() {
                return 250;
              }
            }

            @Order(20.0)
            public class QuantityColumn extends AbstractBigDecimalColumn {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Quantity");
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }
            }

            @Order(30.0)
            public class MeasureColumn extends AbstractSmartColumn<Long> {

              @Override
              protected String getConfiguredHeaderText() {
                return TEXTS.get("Measure");
              }

              @Override
              protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                return MeasureLookupCall.class;
              }

              @Override
              protected int getConfiguredWidth() {
                return 100;
              }

              @Override
              protected void execPrepareLookup(ILookupCall<Long> call, ITableRow row) {
                ((MeasureLookupCall) call).setIsReference(false);
              }
            }

            @Order(10.0)
            public class AddIngredientMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("AddIngredient");
              }

              @Override
              protected void execAction() {
                RecipeIngredientForm form = new RecipeIngredientForm();
                form.setRecipeNr(getRecipeNr());
                form.startNewOnForm();
                form.waitFor();
                if (form.isFormStored()) {
                  ITableRow newIngeredient = getTable().createRow();
                  newIngeredient.setStatus(ITableRow.STATUS_INSERTED);
                  getTable().getIngredientColumn().setValue(newIngeredient, form.getIngredientField().getValue());
                  getTable().getMeasureColumn().setValue(newIngeredient, form.getMeasureField().getValue());
                  getTable().getQuantityColumn().setValue(newIngeredient, form.getQuantityField().getValue());
                  getTable().addRow(newIngeredient);
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
                RecipeIngredientForm form = new RecipeIngredientForm();
                form.setRecipeNr(getRecipeNr());
                form.getIngredientField().setValue(getTable().getIngredientColumn().getSelectedValue());
                form.getMeasureField().setValue(getTable().getMeasureColumn().getSelectedValue());
                form.getQuantityField().setValue(getTable().getQuantityColumn().getSelectedValue());
                form.startModifyOnForm();
                form.waitFor();
                if (form.isFormStored()) {
                  getTable().getSelectedRow().setStatus(ITableRow.STATUS_UPDATED);
                  getTable().getIngredientColumn().setValue(getTable().getSelectedRow(), form.getIngredientField().getValue());
                  getTable().getMeasureColumn().setValue(getTable().getSelectedRow(), form.getMeasureField().getValue());
                  getTable().getQuantityColumn().setValue(getTable().getSelectedRow(), form.getQuantityField().getValue());
                }
              }
            }

            @Order(30.0)
            public class RemoveIngredientsMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TableMenuType.MultiSelection);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("RemoveIngredients");
              }

              @Override
              protected void execAction() {
                for (ITableRow row : getSelectedRows()) {
                  deleteRow(row);
                }
              }
            }
          }
        }
      }

      @Order(20.0)
      public class NotesBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Notes");
        }

        @Order(10.0)
        public class NotesField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 7;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Notes");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }
        }
      }
    }

    @Order(60.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(70.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRecipeService service = BEANS.get(IRecipeService.class);
      RecipeFormData formData = new RecipeFormData();
      formData.setRecipeNr(getRecipeNr());
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeService service = BEANS.get(IRecipeService.class);
      RecipeFormData formData = new RecipeFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRecipeService service = BEANS.get(IRecipeService.class);
      RecipeFormData formData = new RecipeFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeService service = BEANS.get(IRecipeService.class);
      RecipeFormData formData = new RecipeFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
