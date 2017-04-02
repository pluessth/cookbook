package ch.pproject.cookbook.client;

import java.net.URL;

import org.eclipse.scout.rt.client.services.common.icon.AbstractIconProviderService;

/**
 * <h3>{@link DefaultIconProviderService}</h3>
 *
 * @author pluessth
 */
public class DefaultIconProviderService extends AbstractIconProviderService {
  @Override
  protected URL findResource(String relativePath) {
    return ResourceBase.class.getResource("icons/" + relativePath);
  }
}
