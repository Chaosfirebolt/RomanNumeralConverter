package com.github.chaosfirebolt.converter.api.initialization.source;

import java.io.InputStream;

/**
 * Internal!
 */
interface ResourceFactory {

  /**
   * Internal.
   *
   * @param path path to resource
   * @return input stream from the resource
   */
  InputStream create(String path);
}
