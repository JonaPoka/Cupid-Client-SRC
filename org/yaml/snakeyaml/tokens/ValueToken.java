package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class ValueToken extends Token {
  public ValueToken(Mark startMark, Mark endMark) {
    super(startMark, endMark);
  }
  
  public Token.ID getTokenId() {
    return Token.ID.Value;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\yaml\snakeyaml\tokens\ValueToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */