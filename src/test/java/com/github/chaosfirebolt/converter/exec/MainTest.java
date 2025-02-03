package com.github.chaosfirebolt.converter.exec;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

  private InputStream defaultIn;
  private PrintStream defaultOut;

  @BeforeEach
  public void setUp() {
    defaultIn = System.in;
    defaultOut = System.out;
  }

  @AfterEach
  public void tearDown() {
    System.setIn(defaultIn);
    System.setOut(defaultOut);
  }

  @Test
  public void test() {
    System.setIn(in());

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    String[] args = {
            "-i", "c",
            "-id", ";",
            "-b", "4096",
            "-o", "c",
            "-od", ";",
            "-e", "1000"
    };
    Main.main(args);

    String actual = out.toString(StandardCharsets.UTF_8);
    String expected = expected();
    assertEquals(expected, actual, "Incorrect result in command line execution");
  }

  private InputStream in() {
    return getClass().getResourceAsStream("input.txt");
  }

  private String expected() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in()))) {
      StringBuilder sb = new StringBuilder();
      int count = 0;
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(";");
        for (String part : parts) {
          RomanInteger romanInteger = RomanInteger.parse(part);
          sb.append(romanInteger);
          count++;
          if (count >= 1000) {
            sb.append(System.lineSeparator());
            count = 0;
          } else {
            sb.append(';');
          }
        }
      }
      char lastChar = sb.charAt(sb.length() - 1);
      if (lastChar == ';') {
        sb.deleteCharAt(sb.length() - 1);
      }
      return sb.toString();
    } catch (IOException exd) {
      throw new RuntimeException(exd);
    }
  }
}
