package org.apache.http.impl.cookie;

import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.cookie.SetCookie2;

@Immutable
public class RFC2965CommentUrlAttributeHandler implements CookieAttributeHandler {
  public void parse(SetCookie cookie, String commenturl) throws MalformedCookieException {
    if (cookie instanceof SetCookie2) {
      SetCookie2 cookie2 = (SetCookie2)cookie;
      cookie2.setCommentURL(commenturl);
    } 
  }
  
  public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {}
  
  public boolean match(Cookie cookie, CookieOrigin origin) {
    return true;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\http\impl\cookie\RFC2965CommentUrlAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */