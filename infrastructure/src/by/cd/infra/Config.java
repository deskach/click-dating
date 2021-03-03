package by.cd.infra;

import org.reflections.Reflections;

public interface Config {
  Reflections getScanner();

  <T> Class<? extends T> getImplClass(Class<T> ifc);
}
