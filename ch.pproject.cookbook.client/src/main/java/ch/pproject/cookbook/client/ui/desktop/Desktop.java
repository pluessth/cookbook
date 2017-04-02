package ch.pproject.cookbook.client.ui.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;

import ch.pproject.cookbook.client.outlines.StandardOutline;
import ch.pproject.cookbook.shared.Icons;

public class Desktop extends AbstractDesktop {

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    ArrayList<Class<? extends IOutline>> outlines = new ArrayList<Class<? extends IOutline>>();
    outlines.add(StandardOutline.class);
    return outlines;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected void execGuiAttached() {
    super.execGuiAttached();
    selectFirstVisibleOutline();
  }

  protected void selectFirstVisibleOutline() {
    for (IOutline outline : getAvailableOutlines()) {
      if (outline.isEnabled() && outline.isVisible()) {
        setOutline(outline);
        break;
      }
    }
  }

  @Order(1000)
  public class UserMenu extends AbstractMenu {

    @Override
    protected String getConfiguredIconId() {
      return Icons.Person;
    }

    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
      return CollectionUtility.hashSet();
    }

    @Order(100)
    public class AboutMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AboutMenu");
      }

      @Override
      public void execAction() {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }

    @Order(1000)
    public class LogoffMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Logoff");
      }

      @Override
      protected void execAction() {
        ClientSessionProvider.currentSession().stop();
      }
    }
  }

  @Order(10.0)
  @ClassId("92f7e95e-218c-46e5-ac80-d939f03be83a")
  public class RefreshOutlineKeyStroke extends AbstractKeyStroke {

    @Override
    protected String getConfiguredKeyStroke() {
      return "f5";
    }

    @Override
    protected void execAction() {
      if (getOutline() != null) {
        IPage page = getOutline().getActivePage();
        if (page != null) {
          page.reloadPage();
        }
      }
    }
  }
}
