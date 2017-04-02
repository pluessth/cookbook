package ch.pproject.cookbook.client.recipe.step;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.cookbook.client.recipe.step.RecipeStepWizardForm.MainBox.CancelButton;
import ch.pproject.cookbook.client.recipe.step.RecipeStepWizardForm.MainBox.IngredientsBox;
import ch.pproject.cookbook.client.recipe.step.RecipeStepWizardForm.MainBox.OkButton;
import ch.pproject.cookbook.client.recipe.step.RecipeStepWizardForm.MainBox.StepBox;
import ch.pproject.cookbook.shared.recipe.step.IRecipeStepWizardService;
import ch.pproject.cookbook.shared.recipe.step.RecipeStepWizardFormData;

@FormData(value = RecipeStepWizardFormData.class, sdkCommand = SdkCommand.CREATE)
public class RecipeStepWizardForm extends AbstractForm {

  public RecipeStepWizardForm() {
    super();
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

  public IngredientsBox getIngredientsBox() {
    return getFieldByClass(IngredientsBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public StepBox getStepBox() {
    return getFieldByClass(StepBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridW() {
      return 2;
    }

    @Order(10.0)
    public class StepBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridW() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Step");
      }
    }

    @Order(20.0)
    public class IngredientsBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridW() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Ingredients");
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
      IRecipeStepWizardService service = BEANS.get(IRecipeStepWizardService.class);
      RecipeStepWizardFormData formData = new RecipeStepWizardFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeStepWizardService service = BEANS.get(IRecipeStepWizardService.class);
      RecipeStepWizardFormData formData = new RecipeStepWizardFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() {
      IRecipeStepWizardService service = BEANS.get(IRecipeStepWizardService.class);
      RecipeStepWizardFormData formData = new RecipeStepWizardFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() {
      IRecipeStepWizardService service = BEANS.get(IRecipeStepWizardService.class);
      RecipeStepWizardFormData formData = new RecipeStepWizardFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
