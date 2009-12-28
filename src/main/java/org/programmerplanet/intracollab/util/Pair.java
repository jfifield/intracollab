package org.programmerplanet.intracollab.util;

/**
 * Utility class to hold two related objects of any type.
 * 
 * Based on the Pair class described here: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6229146
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class Pair<A, B> {

	private A first;
	private B second;

	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	private static boolean equals(Object x, Object y) {
		return (x == null && y == null) || (x != null && x.equals(y));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other instanceof Pair && equals(first, ((Pair)other).first) && equals(second, ((Pair)other).second);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (first == null) {
			return (second == null) ? 0 : second.hashCode() + 1;
		} else if (second == null) {
			return first.hashCode() + 2;
		} else {
			return first.hashCode() * 17 + second.hashCode();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + first + ", " + second + ")";
	}

}
