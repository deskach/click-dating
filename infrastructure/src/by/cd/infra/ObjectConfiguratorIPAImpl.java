package by.cd.infra;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Inject Property Annotation (IPA) Object Configurator Implementation */
public class ObjectConfiguratorIPAImpl implements ObjectConfigurator {
  Map<String, String> props;
  Map<Class, Function<String, Object>> strParsers;

  public ObjectConfiguratorIPAImpl() {
    this.props = createProps();
    this.strParsers = createType2Value();
  }

  @SneakyThrows
  protected Map<String, String> createProps() {
    // in IntelliJ resources folder should be configured under Project Settings->Modules->Sources
    String path = ClassLoader
        .getSystemClassLoader()
        .getResource("application.properties")
        .getPath();
    Stream<String> lines = new BufferedReader(new FileReader(path)).lines();

    return lines
        .map(line -> line.split("="))
        .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
  }

  protected HashMap<Class, Function<String, Object>> createType2Value() {
    HashMap<Class, Function<String, Object>> results = new HashMap<>();
    results.put(Integer.class, Integer::parseInt);
    results.put(String.class, s -> s);

    return results;
  }

  @Override
  @SneakyThrows
  public void configure(Object t, ApplicationContext context) {
    Class<?> implClass = t.getClass();

    for (Field field : implClass.getDeclaredFields()) {
      InjectProperty annotation = field.getAnnotation(InjectProperty.class);

      if (annotation != null) {
        String key = annotation.value().isEmpty() ? field.getName() : annotation.value();
        String value = props.get(key);
        Function<String, Object> parser = strParsers.getOrDefault(field.getType(), v -> v);

        field.setAccessible(true);
        field.set(t, parser.apply(value));
      }
    }
  }
}
