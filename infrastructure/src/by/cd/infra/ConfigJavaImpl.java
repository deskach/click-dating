package by.cd.infra;

import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class ConfigJavaImpl implements Config {
  private Reflections scanner;
  private Map<Class, Class> ifc2implClass;

  public ConfigJavaImpl(String package2scan, Map<Class, Class> ifc2implClass) {
    this.scanner = new Reflections(package2scan);
    this.ifc2implClass = ifc2implClass;
  }

  @Override
  public Reflections getScanner() {
    return scanner;
  }

  @Override
  public <T> Class<? extends T> getImplClass(Class<T> ifc) {
    return ifc2implClass.computeIfAbsent(ifc, (aClass) -> {
      Set<Class<? extends T>> ifcImpls = scanner.getSubTypesOf(ifc);

      if (ifcImpls.size() != 1) {
        throw new RuntimeException(ifc.getName() + " has 0 or more than 1 impl, please update your config");
      }

      return ifcImpls.iterator().next();
    });
  }
}
