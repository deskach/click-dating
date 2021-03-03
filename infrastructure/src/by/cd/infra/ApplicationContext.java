package by.cd.infra;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
  @Setter
  private ObjectFactory factory;
  private Map<Class, Object> cache = new ConcurrentHashMap<>();

  @Getter
  private Config config;

  public ApplicationContext(Config config) {
    this.config = config;
  }

  public  <T> T getObject(Class<T> tClass) {
    if (cache.containsKey(tClass)) {
      return (T) cache.get(tClass);
    }

    Class<? extends T> implClass = tClass;

    if (implClass.isInterface()) {
      implClass = config.getImplClass(tClass);
    }

    T t = factory.createObject(implClass);

    if (implClass.isAnnotationPresent(Singleton.class)) {
      cache.put(tClass, t);
    }

    return t;
  }
}
