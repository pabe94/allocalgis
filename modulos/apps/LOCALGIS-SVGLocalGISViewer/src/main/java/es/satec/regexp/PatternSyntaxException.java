/*
 * @(#)PatternSyntaxException.java	1.12 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package es.satec.regexp;


/**
 * Unchecked exception thrown to indicate a syntax error in a
 * regular-expression pattern.
 *
 * @author  unascribed
 * @version 1.12, 03/01/23
 * @since 1.4
 * @spec JSR-51
 */

public class PatternSyntaxException
    extends IllegalArgumentException
{

    private final String desc;
    private final String pattern;
    private final int index;

    /**
     * Constructs a new instance of this class.
     *
     * @param  desc
     *         A description of the error
     *
     * @param  regex
     *         The erroneous pattern
     *
     * @param  index
     *         The approximate index in the pattern of the error,
     *         or <tt>-1</tt> if the index is not known
     */
    public PatternSyntaxException(String desc, String regex, int index) {
	this.desc = desc;
	this.pattern = regex;
	this.index = index;
    }

    /**
     * Retrieves the error index.
     *
     * @return  The approximate index in the pattern of the error,
     *         or <tt>-1</tt> if the index is not known
     */
    public int getIndex() {
	return index;
    }

    /**
     * Retrieves the description of the error.
     *
     * @return  The description of the error
     */
    public String getDescription() {
	return desc;
    }

    /**
     * Retrieves the erroneous regular-expression pattern.
     *
     * @return  The erroneous pattern
     */
    public String getPattern() {
	return pattern;
    }

    /**
     * Returns a multi-line string containing the description of the syntax
     * error and its index, the erroneous regular-expression pattern, and a
     * visual indication of the error index within the pattern.
     *
     * @return  The full detail message
     */
    public String getMessage() {
	String nl = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer();
        sb.append(desc);
	if (index >= 0) {
	    sb.append(" near index ");
	    sb.append(index);
	}
        sb.append(nl);
        sb.append(pattern);
	if (index >= 0) {
	    sb.append(nl);
	    for (int i = 0; i < index; i++) sb.append(' ');
	    sb.append('^');
	}
        return sb.toString();
    }

}
