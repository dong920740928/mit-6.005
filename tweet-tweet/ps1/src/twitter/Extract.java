/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.VariableElement;

import org.w3c.dom.css.ElementCSSInlineStyle;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of every
     *         tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant start;
        Instant end;

        start = end = tweets.get(0).getTimestamp();

        for (int i = 1; i < tweets.size(); ++i) {
            Instant instant = tweets.get(i).getTimestamp();

            if (instant.isBefore(start)) {
                start = instant;
            }

            if (instant.isAfter(end)) {
                end = instant;
            }
        }

        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets. A
     *         username-mention is "@" followed by a Twitter username (as defined by
     *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
     *         preceded or followed by any character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT
     *         contain a mention of the username mit. Twitter usernames are
     *         case-insensitive, and the returned set may include a username at most
     *         once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentioned = new HashSet<>();
        for (int i = 0; i < tweets.size(); ++i) {
            String text = tweets.get(i).getText();

            // get mentioned users in text of ith tweet.
            addMentionedUsersToSet(text, mentioned);
        }

        return mentioned;
    }

    private static void addMentionedUsersToSet(String text, Set<String> mentioned) {
        for (int index = text.indexOf('@'); index != -1; index = text.indexOf('@', index + 1)) {
            int j = index;
            // check whether characters after '@' matches a user name
            while (j < text.length() - 1) {
                char nextChar = text.charAt(j + 1);
                if (isNameCharacter(nextChar)) {
                    // in a string matches rule of user name
                    j++;
                } else if (Character.isWhitespace(nextChar)) {
                    if (j > index) {
                        // find user name
                        mentioned.add(text.substring(index, j + 1));
                    }
                    break;
                } else {
                    break;
                }
            }
        }
    }
    

    private static boolean isNameCharacter(char ch) {
        return ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == '-' || ch == '_';
    }
}
