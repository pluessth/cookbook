package ch.pproject.cookbook.server.services;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.util.IOUtility;

import ch.pproject.cookbook.shared.services.IImageService;

public class ImageService implements IImageService {

  @Override
  public byte[] getImage(String imageId) {
    try {
      // TODO [pluessth] get content of image
      return IOUtility.getContent("C:/Temp/images/" + imageId + ".jpg");
    }
    catch (ProcessingException e) {
      // nop
    }
    return null;
  }
}
