package ch.pproject.cookbook.client.recipe.ingredient;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenuSeparator;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.cookbook.client.ingredient.IngredientForm;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm.MainBox.CancelButton;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm.MainBox.FieldBox;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm.MainBox.FieldBox.IngredientField;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm.MainBox.FieldBox.MeasureField;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm.MainBox.FieldBox.QuantityField;
import ch.pproject.cookbook.client.recipe.ingredient.RecipeIngredientForm.MainBox.OkButton;
import ch.pproject.cookbook.shared.ingredient.IngredientLookupCall;
import ch.pproject.cookbook.shared.measure.MeasureLookupCall;
import ch.pproject.cookbook.shared.recipe.ingredient.IRecipeIngredientService;
import ch.pproject.cookbook.shared.recipe.ingredient.RecipeIngredientFormData;

@FormData(value = RecipeIngredientFormData.class, sdkCommand = SdkCommand.CREATE)
public class RecipeIngredientForm extends AbstractForm {

  private Long m_recipeNr;
  private Long m_recipeIngredientNr;

  public RecipeIngredientForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Ingredient");
  }

  @FormData
  public Long getRecipeIngredientNr() {
    return m_recipeIngredientNr;
  }

  @FormData
  public void setRecipeIngredientNr(Long recipeIngredientNr) {
    m_recipeIngredientNr = recipeIngredientNr;
  }

  @FormData
  public Long getRecipeNr() {
    return m_recipeNr;
  }

  @FormData
  public void setRecipeNr(Long ingredientNr) {
    m_recipeNr = ingredientNr;
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startModifyOnForm() {
    startInternal(new ModifyOnFormHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public void startNewOnForm() {
    startInternal(new NewOnFormHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public IngredientField getIngredientField() {
    return getFieldByClass(IngredientField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MeasureField getMeasureField() {
    return getFieldByClass(MeasureField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public QuantityField getQuantityField() {
    return getFieldByClass(QuantityField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(40.0)
    public class FieldBox extends AbstractGroupBox {

      @Order(10.0)
      public class IngredientField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Ingredient");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return IngredientLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Order(10.0)
        public class EditIngredientMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return TEXTS.get("EditIngredient_");
          }

          @Override
          protected void execAction() {
            IngredientForm form = new IngredientForm();
            form.setIngredientNr(getValue());
            form.startModify();
          }

        }

        @Order(15.0)
        public class SeparatorMenu extends AbstractMenuSeparator {
        }

        @Order(20.0)
        public class NewIngredientMenu extends AbstractMenu {

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType> hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
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
            setValue(form.getIngredientNr());
          }
        }
      }

      @Order(20.0)
      public class MeasureField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Measure");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return MeasureLookupCall.class;
        }

        @Override
        protected Class<? extends IValueField<?>> getConfiguredMasterField() {
          return IngredientField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call) {
          ((MeasureLookupCall) call).setIsReference(false);
          ((MeasureLookupCall) call).setIngredientNr(getIngredientField().getValue());
        }
      }

      @Order(30.0)
      public class QuantityField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Quantity");
        }
      }
    }

    @Order(50.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(60.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRecipeIngredientService service = BEANS.get(IRecipeIngredientService.class);
      RecipeIngredientFormData formData = new RecipeIngredientFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeIngredientService service = BEANS.get(IRecipeIngredientService.class);
      RecipeIngredientFormData formData = new RecipeIngredientFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class ModifyOnFormHandler extends AbstractFormHandler {

  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRecipeIngredientService service = BEANS.get(IRecipeIngredientService.class);
      RecipeIngredientFormData formData = new RecipeIngredientFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeIngredientService service = BEANS.get(IRecipeIngredientService.class);
      RecipeIngredientFormData formData = new RecipeIngredientFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }

  public class NewOnFormHandler extends AbstractFormHandler {

  }
}
