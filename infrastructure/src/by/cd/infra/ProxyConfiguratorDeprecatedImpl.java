package by.cd.infra;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Proxy;

public class ProxyConfiguratorDeprecatedImpl implements ProxyConfigurator {
  @Override
  public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
    // TODO: improve this to allow @Deprecated be applicable to methods too (not just classes)
    if (implClass.isAnnotationPresent(java.lang.Deprecated.class)) {
      if (implClass.getInterfaces().length == 0) {
        logMessage(implClass.getCanonicalName());

        return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) -> method.invoke(t, args));
      }

      return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> {
        logMessage(implClass.getCanonicalName());

        return method.invoke(t, args);
      });
    }

    return t;
  }

  private void logMessage(String className) {
    System.out.println("*** Class " + className + " is deprecated ***");
  }
}
