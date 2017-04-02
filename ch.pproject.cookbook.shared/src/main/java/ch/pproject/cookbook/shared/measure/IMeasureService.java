package ch.pproject.cookbook.shared.measure;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IMeasureService extends IService {

  MeasureFormData prepareCreate(MeasureFormData formData);

  MeasureFormData create(MeasureFormData formData);

  MeasureFormData load(MeasureFormData formData);

  MeasureFormData store(MeasureFormData formData);
}
