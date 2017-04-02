package ch.pproject.cookbook.client.measure;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.CancelButton;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.ConversionBox;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.ConversionBox.FactorField;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.ConversionBox.RefernceMeasureField;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.FieldBox;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.FieldBox.AcronymField;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.FieldBox.NameField;
import ch.pproject.cookbook.client.measure.MeasureForm.MainBox.OkButton;
import ch.pproject.cookbook.shared.measure.IMeasureService;
import ch.pproject.cookbook.shared.measure.MeasureFormData;
import ch.pproject.cookbook.shared.measure.MeasureLookupCall;

@FormData(value = MeasureFormData.class, sdkCommand = SdkCommand.CREATE)
public class MeasureForm extends AbstractForm {

  private Long m_measureNr;

  public MeasureForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Measure");
  }

  @FormData
  public Long getMeasureNr() {
    return m_measureNr;
  }

  @FormData
  public void setMeasureNr(Long measureNr) {
    m_measureNr = measureNr;
  }

  public void startModify() {
    startInternal(new ModifyHandler());
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public AcronymField getAcronymField() {
    return getFieldByClass(AcronymField.class);
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ConversionBox getConversionBox() {
    return getFieldByClass(ConversionBox.class);
  }

  public FactorField getFactorField() {
    return getFieldByClass(FactorField.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public RefernceMeasureField getRefernceMeasureField() {
    return getFieldByClass(RefernceMeasureField.class);
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
      public class AcronymField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Acronym");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }
    }

    @Order(20.0)
    public class ConversionBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Conversion");
      }

      @Order(10.0)
      public class RefernceMeasureField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ReferenceUnit");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return MeasureLookupCall.class;
        }

        @Override
        protected void execPrepareLookup(ILookupCall<Long> call) {
          ((MeasureLookupCall) call).setIsReference(true);
        }
      }

      @Order(20.0)
      public class FactorField extends AbstractBigDecimalField {

        @Override
        protected int getConfiguredFractionDigits() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
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
      }
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IMeasureService service = BEANS.get(IMeasureService.class);
      MeasureFormData formData = new MeasureFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IMeasureService service = BEANS.get(IMeasureService.class);
      MeasureFormData formData = new MeasureFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IMeasureService service = BEANS.get(IMeasureService.class);
      MeasureFormData formData = new MeasureFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IMeasureService service = BEANS.get(IMeasureService.class);
      MeasureFormData formData = new MeasureFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
