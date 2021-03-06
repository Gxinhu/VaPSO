package jmetal.util.errorchecking;


import java.io.Serializable;

/**
 * jMetal exception class
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class JMetalException extends RuntimeException implements Serializable {
  public JMetalException(String message) {
    super(message);
  }
  }

