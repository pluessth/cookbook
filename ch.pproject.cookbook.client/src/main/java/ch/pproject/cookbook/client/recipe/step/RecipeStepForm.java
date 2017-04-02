package ch.pproject.cookbook.client.recipe.step;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.cookbook.client.recipe.step.RecipeStepForm.MainBox.CancelButton;
import ch.pproject.cookbook.client.recipe.step.RecipeStepForm.MainBox.FieldBox;
import ch.pproject.cookbook.client.recipe.step.RecipeStepForm.MainBox.FieldBox.DescriptionField;
import ch.pproject.cookbook.client.recipe.step.RecipeStepForm.MainBox.FieldBox.ImageField;
import ch.pproject.cookbook.client.recipe.step.RecipeStepForm.MainBox.OkButton;
import ch.pproject.cookbook.shared.recipe.RecipeStepFormData;
import ch.pproject.cookbook.shared.recipe.step.IRecipeStepService;

@FormData(value = RecipeStepFormData.class, sdkCommand = SdkCommand.CREATE)
public class RecipeStepForm extends AbstractForm {

  private Long m_recipeStepNr;
  private Long m_recipeNr;
  private Long m_order;

  public RecipeStepForm() {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Step");
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

  public DescriptionField getDescriptionField() {
    return getFieldByClass(DescriptionField.class);
  }

  public FieldBox getFieldBox() {
    return getFieldByClass(FieldBox.class);
  }

  public ImageField getImageField() {
    return getFieldByClass(ImageField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class FieldBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(10.0)
      public class DescriptionField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Description");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
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

      @Order(20.0)
      public class ImageField extends AbstractImageField {

        @Override
        protected boolean getConfiguredFocusable() {
          return true;
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
      IRecipeStepService service = BEANS.get(IRecipeStepService.class);
      RecipeStepFormData formData = new RecipeStepFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeStepService service = BEANS.get(IRecipeStepService.class);
      RecipeStepFormData formData = new RecipeStepFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRecipeStepService service = BEANS.get(IRecipeStepService.class);
      RecipeStepFormData formData = new RecipeStepFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeStepService service = BEANS.get(IRecipeStepService.class);
      RecipeStepFormData formData = new RecipeStepFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }

  @FormData
  public Long getRecipeStepNr() {
    return m_recipeStepNr;
  }

  @FormData
  public void setRecipeStepNr(Long recipeStepNr) {
    m_recipeStepNr = recipeStepNr;
  }

  @FormData
  public Long getRecipeNr() {
    return m_recipeNr;
  }

  @FormData
  public void setRecipeNr(Long recipeNr) {
    m_recipeNr = recipeNr;
  }

  /**
   * @return the Order
   */
  @FormData
  public Long getOrder() {
    return m_order;
  }

  /**
   * @param order
   *          the Order to set
   */
  @FormData
  public void setOrder(Long order) {
    m_order = order;
  }
}
