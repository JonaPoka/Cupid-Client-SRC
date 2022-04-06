package org.apache.logging.log4j.core.pattern;

import java.util.List;
import java.util.regex.Pattern;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "replace", category = "Converter")
@ConverterKeys({"replace"})
public final class RegexReplacementConverter extends LogEventPatternConverter {
  private final Pattern pattern;
  
  private final String substitution;
  
  private final List<PatternFormatter> formatters;
  
  private RegexReplacementConverter(List<PatternFormatter> formatters, Pattern pattern, String substitution) {
    super("replace", "replace");
    this.pattern = pattern;
    this.substitution = substitution;
    this.formatters = formatters;
  }
  
  public static RegexReplacementConverter newInstance(Configuration config, String[] options) {
    if (options.length != 3) {
      LOGGER.error("Incorrect number of options on replace. Expected 3 received " + options.length);
      return null;
    } 
    if (options[0] == null) {
      LOGGER.error("No pattern supplied on replace");
      return null;
    } 
    if (options[1] == null) {
      LOGGER.error("No regular expression supplied on replace");
      return null;
    } 
    if (options[2] == null) {
      LOGGER.error("No substitution supplied on replace");
      return null;
    } 
    Pattern p = Pattern.compile(options[1]);
    PatternParser parser = PatternLayout.createPatternParser(config);
    List<PatternFormatter> formatters = parser.parse(options[0]);
    return new RegexReplacementConverter(formatters, p, options[2]);
  }
  
  public void format(LogEvent event, StringBuilder toAppendTo) {
    StringBuilder buf = new StringBuilder();
    for (PatternFormatter formatter : this.formatters)
      formatter.format(event, buf); 
    toAppendTo.append(this.pattern.matcher(buf.toString()).replaceAll(this.substitution));
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\logging\log4j\core\pattern\RegexReplacementConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */