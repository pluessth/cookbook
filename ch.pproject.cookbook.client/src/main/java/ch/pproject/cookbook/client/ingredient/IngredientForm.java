package ch.pproject.cookbook.client.ingredient;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.cookbook.client.ingredient.IngredientForm.MainBox.CancelButton;
import ch.pproject.cookbook.client.ingredient.IngredientForm.MainBox.FieldBox;
import ch.pproject.cookbook.client.ingredient.IngredientForm.MainBox.FieldBox.MeasureField;
import ch.pproject.cookbook.client.ingredient.IngredientForm.MainBox.FieldBox.NameField;
import ch.pproject.cookbook.client.ingredient.IngredientForm.MainBox.OkButton;
import ch.pproject.cookbook.shared.ingredient.IIngredientProcessService;
import ch.pproject.cookbook.shared.ingredient.IngredientFormData;
import ch.pproject.cookbook.shared.measure.MeasureLookupCall;

@FormData(value = IngredientFormData.class, sdkCommand = SdkCommand.CREATE)
public class IngredientForm extends AbstractForm {

  private Long ingredientNr;

  public IngredientForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Ingredient");
  }

  @FormData
  public Long getIngredientNr() {
    return ingredientNr;
  }

  @FormData
  public void setIngredientNr(Long ingredientNr) {
    this.ingredientNr = ingredientNr;
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

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MeasureField getMeasureField() {
    return getFieldByClass(MeasureField.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class FieldBox extends AbstractGroupBox {

      @Order(10.0)
      public class NameField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }

      @Order(20.0)
      public class MeasureField extends AbstractListBox<Long> {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Measure");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return MeasureLookupCall.class;
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call) {
          ((MeasureLookupCall) call).setIsReference(true);
        }
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IIngredientProcessService service = BEANS.get(IIngredientProcessService.class);
      IngredientFormData formData = new IngredientFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IIngredientProcessService service = BEANS.get(IIngredientProcessService.class);
      IngredientFormData formData = new IngredientFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IIngredientProcessService service = BEANS.get(IIngredientProcessService.class);
      IngredientFormData formData = new IngredientFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IIngredientProcessService service = BEANS.get(IIngredientProcessService.class);
      IngredientFormData formData = new IngredientFormData();
      exportFormData(formData);
      formData = service.create(formData);
      setIngredientNr(formData.getIngredientNr());
    }
  }
}
