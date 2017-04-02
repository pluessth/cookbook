package ch.pproject.cookbook.client.recipe.ingredient;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.PageData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import ch.pproject.cookbook.shared.recipe.ingredient.IRecipeIngredientService;
import ch.pproject.cookbook.shared.recipe.ingredient.RecipeIngredientsTablePageData;
import ch.pproject.cookbook.shared.services.IStandardOutlineService;

@PageData(RecipeIngredientsTablePageData.class)
public class RecipeIngredientsTablePage extends AbstractPageWithTable<RecipeIngredientsTablePage.Table> {

  private Long m_recipeNr;

  public RecipeIngredientsTablePage(Long recipeNr) {
    m_recipeNr = recipeNr;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Ingredients");
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    AbstractTablePageData pageData = BEANS.get(IStandardOutlineService.class).getRecipeIngredientsTableData(getRecipeNr());
    importPageData(pageData);
  }

  @FormData
  public Long getRecipeNr() {
    return m_recipeNr;
  }

  @FormData
  public void setRecipeNr(Long recipeNr) {
    m_recipeNr = recipeNr;
  }

  @Order(10.0)
  public class Table extends AbstractRecipeIngredientsTable {

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
        RecipeIngredientForm form = new RecipeIngredientForm();
        form.setRecipeIngredientNr(((AbstractRecipeIngredientsTable) getTable()).getIngredientRecipeNrColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
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
        BEANS.get(IRecipeIngredientService.class).remove(getIngredientRecipeNrColumn().getSelectedValues());
        reloadPage();
      }
    }
  }
}
