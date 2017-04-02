/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package ch.pproject.cookbook.client.recipe.ingredient;

import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import ch.pproject.cookbook.shared.measure.MeasureLookupCall;

public class AbstractRecipeIngredientsTable extends AbstractTable {

  public QuantityColumn getQuantityColumn() {
    return getColumnSet().getColumnByClass(QuantityColumn.class);
  }

  public MeasureColumn getMeasureColumn() {
    return getColumnSet().getColumnByClass(MeasureColumn.class);
  }

  public NameColumn getNameColumn() {
    return getColumnSet().getColumnByClass(NameColumn.class);
  }

  public IngredientNrColumn getIngredientNrColumn() {
    return getColumnSet().getColumnByClass(IngredientNrColumn.class);
  }

  public RecipeIngredientNrColumn getIngredientRecipeNrColumn() {
    return getColumnSet().getColumnByClass(RecipeIngredientNrColumn.class);
  }

  @Order(5.0)
  public class RecipeIngredientNrColumn extends AbstractLongColumn {

    @Override
    protected boolean getConfiguredDisplayable() {
      return false;
    }
  }

  @Order(10.0)
  public class IngredientNrColumn extends AbstractLongColumn {

    @Override
    protected boolean getConfiguredDisplayable() {
      return false;
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
    protected void execPrepareLookup(ILookupCall<Long> call, ITableRow row) {
      ((MeasureLookupCall) call).setIsReference(false);
    }

    @Override
    protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
      return MeasureLookupCall.class;
    }

    @Override
    protected int getConfiguredWidth() {
      return 100;
    }
  }

  @Order(40.0)
  public class NameColumn extends AbstractStringColumn {

    @Override
    protected String getConfiguredHeaderText() {
      return TEXTS.get("Name");
    }

    @Override
    protected int getConfiguredWidth() {
      return 250;
    }
  }
}
