package ch.pproject.cookbook.shared.recipe;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class DifficultyCodeType extends AbstractCodeType<Long, Long> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10000L;

  public DifficultyCodeType() {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Difficulty");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10.0)
  public static class EasyCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10001L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Easy");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20.0)
  public static class MediumCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10002L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Medium");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(30.0)
  public static class HardCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10003L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Hard");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }
}
