/*
 * 文件名称: StringWriter.java Copyright 2011-2013 Nali All right reserved.
 */
package com.wikimore.mlog;

import java.io.IOException;
import java.io.Writer;

/**
 * A character stream like java.io.StringWriter that collects its output in a StringBuilder, which
 * can then be used to construct a string.
 * <p>
 * Closing a <tt>StringWriter</tt> has no effect. The methods in this class can be called after the
 * stream has been closed without generating an <tt>IOException</tt>.
 * 
 * <p>
 * <strong>Note:</strong> this class is not thread-safe.
 * 
 * @author ted created on 2013-5-18
 * @since 1.0
 */

public class StringWriter extends Writer {

    private StringBuilder stringBuilder;

    /**
     * Create a new string writer using the default initial string-builder
     * size.
     */
    public StringWriter() {
        stringBuilder = new StringBuilder();
        lock = stringBuilder;
    }

    /**
     * Create a new string writer using the specified initial string-builder
     * size.
     * 
     * @param initialSize
     *            The number of <tt>char</tt> values that will fit into this builder
     *            before it is automatically expanded
     * 
     * @throws IllegalArgumentException
     *             If <tt>initialSize</tt> is negative
     */
    public StringWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        stringBuilder = new StringBuilder(initialSize);
        lock = stringBuilder;
    }

    /**
     * Write a single character.
     */
    public void write(int c) {
        stringBuilder.append((char) c);
    }

    /**
     * Write a portion of an array of characters.
     * 
     * @param cbuf Array of characters
     * @param off Offset from which to start writing characters
     * @param len Number of characters to write
     */
    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length)
                || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        stringBuilder.append(cbuf, off, len);
    }

    /**
     * Write a string.
     */
    public void write(String str) {
        stringBuilder.append(str);
    }

    /**
     * Write a portion of a string.
     * 
     * @param str String to be written
     * @param off Offset from which to start writing characters
     * @param len Number of characters to write
     */
    public void write(String str, int off, int len) {
        stringBuilder.append(str.substring(off, off + len));
    }

    /**
     * Appends the specified character sequence to this writer.
     * 
     * <p>
     * An invocation of this method of the form <tt>out.append(csq)</tt> behaves in exactly the same
     * way as the invocation
     * 
     * <pre>
     * out.write(csq.toString())
     * </pre>
     * 
     * <p>
     * Depending on the specification of <tt>toString</tt> for the character sequence <tt>csq</tt>,
     * the entire sequence may not be appended. For instance, invoking the <tt>toString</tt> method
     * of a character builder will return a subsequence whose content depends upon the builder's
     * position and limit.
     * 
     * @param csq
     *            The character sequence to append. If <tt>csq</tt> is <tt>null</tt>, then the four
     *            characters <tt>"null"</tt> are
     *            appended to this writer.
     * 
     * @return This writer
     * 
     * @since 1.5
     */
    public StringWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
        return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this writer.
     * 
     * <p>
     * An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in exactly the same way as the
     * invocation
     * 
     * <pre>
     * out.write(csq.subSequence(start, end).toString())
     * </pre>
     * 
     * @param csq
     *            The character sequence from which a subsequence will be
     *            appended. If <tt>csq</tt> is <tt>null</tt>, then characters
     *            will be appended as if <tt>csq</tt> contained the four
     *            characters <tt>"null"</tt>.
     * 
     * @param start
     *            The index of the first character in the subsequence
     * 
     * @param end
     *            The index of the character following the last character in the
     *            subsequence
     * 
     * @return This writer
     * 
     * @throws IndexOutOfBoundsException
     *             If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt> is greater than
     *             <tt>end</tt>, or <tt>end</tt> is greater than <tt>csq.length()</tt>
     * 
     * @since 1.5
     */
    public StringWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    /**
     * Appends the specified character to this writer.
     * 
     * <p>
     * An invocation of this method of the form <tt>out.append(c)</tt> behaves in exactly the same
     * way as the invocation
     * 
     * <pre>
     * out.write(c)
     * </pre>
     * 
     * @param c
     *            The 16-bit character to append
     * 
     * @return This writer
     * 
     * @since 1.5
     */
    public StringWriter append(char c) {
        write(c);
        return this;
    }

    /**
     * Return the builder's current value as a string.
     */
    public String toString() {
        return stringBuilder.toString();
    }

    /**
     * Return the string builder itself.
     * 
     * @return Stringbuilder holding the current builder value.
     */
    public StringBuilder getBuilder() {
        return stringBuilder;
    }

    /**
     * Flush the stream.
     */
    public void flush() {
    }

    /**
     * Closing a <tt>StringWriter</tt> has no effect. The methods in this
     * class can be called after the stream has been closed without generating
     * an <tt>IOException</tt>.
     */
    public void close() throws IOException {
    }

}
