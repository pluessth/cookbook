package ch.pproject.cookbook.shared.services;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IImageService extends IService {

  byte[] getImage(String imageId);
}
