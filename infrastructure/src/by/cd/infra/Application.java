package by.cd.infra;

import java.util.Map;

public class Application {
  public static ApplicationContext run(String package2scan, Map<Class, Class> ifc2implClass) {
    ConfigJavaImpl config = new ConfigJavaImpl(package2scan, ifc2implClass);
    ApplicationContext context = new ApplicationContext(config);
    ObjectFactory factory = new ObjectFactory(context);
    context.setFactory(factory);

    return context;
  }
}
