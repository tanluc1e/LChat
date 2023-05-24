package me.nitkanikita.extendedchat.Utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {
   public static Iterable<MatchResult> allMatches(final Pattern p, final CharSequence input) {
      return new Iterable<MatchResult>() {
         public Iterator<MatchResult> iterator() {
            return new Iterator<MatchResult>() {
               final Matcher matcher = p.matcher(input);
               MatchResult pending;

               public boolean hasNext() {
                  if (this.pending == null && this.matcher.find()) {
                     this.pending = this.matcher.toMatchResult();
                  }

                  return this.pending != null;
               }

               public MatchResult next() {
                  if (!this.hasNext()) {
                     throw new NoSuchElementException();
                  } else {
                     MatchResult next = this.pending;
                     this.pending = null;
                     return next;
                  }
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }
      };
   }
}
