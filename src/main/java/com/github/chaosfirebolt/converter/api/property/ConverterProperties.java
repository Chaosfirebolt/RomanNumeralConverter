package com.github.chaosfirebolt.converter.api.property;

/**
 * Configuration properties for roman numeral converter.
 */
public final class ConverterProperties {

  /**
   * Name of the system property, responsible to determine whether certain actions should be synchronized.
   */
  public static final String SYNC_PROPERTY_NAME = "r.n.c.sync";

  private ConverterProperties() {
    throw new RuntimeException("No instances allowed");
  }

  /**
   * Returns the value of a system property, responsible to determine whether certain actions should be synchronized.
   * See {@link ConverterProperties#SYNC_PROPERTY_NAME}.
   *
   * @return the system property value
   */
  public static boolean getSyncProperty() {
    return Boolean.getBoolean(SYNC_PROPERTY_NAME);
  }
}
