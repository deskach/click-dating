package by.cd.infra;

public interface ProxyConfigurator {
  Object replaceWithProxyIfNeeded(Object t, Class implClass);
}
