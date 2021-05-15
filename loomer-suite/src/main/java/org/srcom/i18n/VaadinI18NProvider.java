package org.srcom.i18n;

import com.vaadin.flow.i18n.I18NProvider;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.ResourceBundle.getBundle;

public class VaadinI18NProvider implements I18NProvider {

  public VaadinI18NProvider() {

    System.out.println(VaadinI18NProvider.class.getSimpleName() + " was found..");
  }

  public static final String RESOURCE_BUNDLE_NAME = "vaadinapp";

  private static final ResourceBundle RESOURCE_BUNDLE_EN = getBundle(RESOURCE_BUNDLE_NAME , ENGLISH);
  private static final ResourceBundle RESOURCE_BUNDLE_DE = getBundle(RESOURCE_BUNDLE_NAME , GERMAN);
  private static final List<Locale> providedLocales = unmodifiableList(asList(ENGLISH , GERMAN));

  @Override
  public List<Locale> getProvidedLocales() {
    System.out.println("VaadinI18NProvider getProvidedLocales..");
    return providedLocales;
  }

  @Override
  public String getTranslation(String key , Locale locale , Object... params) {
    ResourceBundle resourceBundle = RESOURCE_BUNDLE_EN;
    if (GERMAN.equals(locale)) {
      resourceBundle = RESOURCE_BUNDLE_DE;
    }

    if (! resourceBundle.containsKey(key)) {
      System.out.println("missing resource key (i18n) " + key);
      return key + " - " + locale;
    } else {
      return (resourceBundle.containsKey(key)) ? resourceBundle.getString(key) : key;
    }
  }

}
