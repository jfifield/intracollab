package org.programmerplanet.intracollab.model;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class BaseEntityTest extends TestCase {

	public void testEquals_NoId() {
		TestEntityA a1 = new TestEntityA();
		TestEntityA a2 = new TestEntityA();
		assertFalse("a1 == a2", a1.equals(a2));
		assertFalse("a2 == a1", a2.equals(a1));
		assertTrue("a1 == a1", a1.equals(a1));
		assertTrue("a2 == a2", a2.equals(a2));
	}

	public void testEquals_DifferentId() {
		TestEntityA a1 = new TestEntityA(1);
		TestEntityA a2 = new TestEntityA(2);
		assertFalse("a1 == a2", a1.equals(a2));
		assertFalse("a2 == a1", a2.equals(a1));
	}

	public void testEquals_SameId() {
		TestEntityA a1 = new TestEntityA(1);
		TestEntityA a2 = new TestEntityA(1);
		assertTrue("a1 == a2", a1.equals(a2));
		assertTrue("a2 == a1", a2.equals(a1));
	}

	public void testEquals_SameId_DifferentClasses() {
		TestEntityA a1 = new TestEntityA(1);
		TestEntityB b1 = new TestEntityB(1);
		assertFalse("a1 == b1", a1.equals(b1));
		assertFalse("b1 == a1", b1.equals(a1));
	}

	public void testEquals_SameId_SubClass() {
		TestEntityA a1 = new TestEntityA(1);
		TestEntitySubA a2 = new TestEntitySubA(1);
		assertTrue("a1 == a2", a1.equals(a2));
		assertTrue("a2 == a1", a2.equals(a1));
	}

	public void testHashCode_NoId() {
		TestEntityA a1 = new TestEntityA();
		TestEntityA a2 = new TestEntityA();
		assertFalse("a1 == a2", a1.hashCode() == a2.hashCode());
	}

	public void testHashCode_DifferentId() {
		TestEntityA a1 = new TestEntityA(1);
		TestEntityA a2 = new TestEntityA(2);
		assertFalse("a1 == a2", a1.hashCode() == a2.hashCode());
	}

	public void testHashCode_SameId() {
		TestEntityA a1 = new TestEntityA(1);
		TestEntityA a2 = new TestEntityA(1);
		assertTrue("a1 == a2", a1.hashCode() == a2.hashCode());
	}

	private static class TestEntityA extends BaseEntity {

		public TestEntityA() {
		}

		public TestEntityA(long id) {
			this.setId(Long.valueOf(id));
		}

	}

	private static class TestEntitySubA extends TestEntityA {

		public TestEntitySubA() {
		}

		public TestEntitySubA(long id) {
			super(id);
		}

	}

	private static class TestEntityB extends BaseEntity {

		public TestEntityB() {
		}

		public TestEntityB(long id) {
			this.setId(Long.valueOf(id));
		}

	}

}
