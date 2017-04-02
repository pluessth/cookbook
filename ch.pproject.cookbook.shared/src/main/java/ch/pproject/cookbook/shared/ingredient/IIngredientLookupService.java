package ch.pproject.cookbook.shared.ingredient;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

@TunnelToServer
public interface IIngredientLookupService extends ILookupService<Long> {
}
