package by.cd.infra;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Inject Property Annotation (IPA) Object Configurator Implementation */
public class ObjectConfiguratorIPAImpl implements ObjectConfigurator {
  Map<String, String> propertyMap;

  @SneakyThrows
  public ObjectConfiguratorIPAImpl() {
    // in IntelliJ resources folder should be configured under Project Settings->Modules->Sources
    String path = ClassLoader
        .getSystemClassLoader()
        .getResource("application.properties")
        .getPath();
    Stream<String> lines = new BufferedReader(new FileReader(path)).lines();

    propertyMap = lines
        .map(line -> line.split("="))
        .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
  }

  @Override
  @SneakyThrows
  public void configure(Object t, ApplicationContext context) {
    Class<?> implClass = t.getClass();

    for (Field field : implClass.getDeclaredFields()) {
      InjectProperty annotation = field.getAnnotation(InjectProperty.class);

      if (annotation != null) {
        String key = annotation.value().isEmpty() ? field.getName() : annotation.value();
        String value = propertyMap.get(key);
        field.setAccessible(true);
        field.set(t, value);
      }
    }
  }
}
