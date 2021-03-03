package by.cd.infra;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
  private final ApplicationContext context;
  private final ArrayList<ProxyConfigurator> proxyConfigurators;
  private List<ObjectConfigurator> configurators;

  @SneakyThrows
  public ObjectFactory(ApplicationContext context) {
    this.context = context;

    configurators = new ArrayList<>();
    for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
      configurators.add(aClass.getDeclaredConstructor().newInstance());
    }

    proxyConfigurators = new ArrayList<>();
    for (Class<? extends ProxyConfigurator> pClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
      proxyConfigurators.add(pClass.getDeclaredConstructor().newInstance());
    }
  }

  @SneakyThrows
  public <T> T createObject(Class<T> implClass) {
    T t = implClass.getDeclaredConstructor().newInstance();

    configure(t);
    runInitMethod(implClass, t);

    t = wrapWithProxyIfNeeded(implClass, t);

    return t;
  }

  private <T> void configure(T t) {
    T finalT = t;
    configurators.forEach(c -> c.configure(finalT, context));
  }

  @SneakyThrows
  private <T> void runInitMethod(Class<T> implClass, T t) {
    for (Method method : implClass.getMethods()) {
      if (method.isAnnotationPresent(PostConstruct.class)) {
        method.invoke(t);
      }
    }
  }

  private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t) {
    for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
      t = (T) proxyConfigurator.replaceWithProxyIfNeeded(t, implClass);
    }
    return t;
  }
}
