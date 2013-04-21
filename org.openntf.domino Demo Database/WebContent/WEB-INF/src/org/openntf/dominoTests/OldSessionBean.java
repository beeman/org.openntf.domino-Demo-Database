package org.openntf.dominoTests;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import lotus.domino.Database;
import lotus.domino.DateTime;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewNavigator;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class OldSessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OldSessionBean() {

	}

	public String getDateTimeIsBeforeTest() {
		String retVal_ = "";
		View threadsByDate = null;
		Document firstDoc = null;
		Document secondDoc = null;
		try {
			Session s = ExtLibUtil.getCurrentSession();
			Database currDb = s.getCurrentDatabase();
			threadsByDate = currDb.getView("AllThreadsByDate");
			ViewNavigator vNav = threadsByDate.createViewNav();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(30);
			ViewEntry firstEnt = vNav.getNth(randomInt);
			while (!firstEnt.isDocument()) {
				ViewEntry tmpEnt = vNav.getNext();
				firstEnt.recycle();
				firstEnt = tmpEnt;
			}
			randomInt = randomGenerator.nextInt(30);
			ViewEntry secondEnt = vNav.getNth(randomInt);
			while (!secondEnt.isDocument()) {
				ViewEntry tmpEnt = vNav.getNext();
				secondEnt.recycle();
				secondEnt = tmpEnt;
			}
			firstDoc = firstEnt.getDocument();
			secondDoc = secondEnt.getDocument();
			String firstDt = firstDoc.getFirstItem("Date").getText();
			String secondDt = secondDoc.getFirstItem("Date").getText();
			DateTime firstDate = s.createDateTime(firstDt);
			DateTime secondDate = s.createDateTime(secondDt);
			Date firstDateJ = firstDate.toJavaDate();
			Date secondDateJ = secondDate.toJavaDate();
			retVal_ = "Comparing " + firstDt + " (" + firstDoc.getUniversalID() + ") with " + secondDt + " ("
					+ secondDoc.getUniversalID() + ")...";
			if (firstDateJ.before(secondDateJ)) {
				retVal_ += "first before second";
			} else {
				retVal_ += "first NOT before second";
			}
		} catch (NotesException e) {
			// doSomething
		} finally {
			try {
				threadsByDate.recycle();
				firstDoc.recycle();
				secondDoc.recycle();
			} catch (NotesException e) {
				// doSomething
			}
		}
		return retVal_;
	}

	public boolean dateTimeIsAfterTest() {
		boolean retVal_ = false;
		View threadsByDate = null;
		Document firstDoc = null;
		Document secondDoc = null;
		try {
			Session s = ExtLibUtil.getCurrentSession();
			Database currDb = s.getCurrentDatabase();
			threadsByDate = currDb.getView("AllThreadsByDate");
			ViewNavigator vNav = threadsByDate.createViewNav();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
			ViewEntry firstEnt = vNav.getNth(randomInt);
			ViewEntry secondEnt = vNav.getNext(firstEnt);
			firstDoc = firstEnt.getDocument();
			secondDoc = secondEnt.getDocument();
			DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
			DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
			Date firstDateJ = firstDate.toJavaDate();
			Date secondDateJ = secondDate.toJavaDate();
			if (firstDateJ.after(secondDateJ)) {
				retVal_ = true;
			}
		} catch (NotesException e) {
			// doSomething
		} finally {
			try {
				threadsByDate.recycle();
				firstDoc.recycle();
				secondDoc.recycle();
			} catch (NotesException e) {
				// doSomething
			}
		}
		return retVal_;
	}

	public boolean dateTimeEqualsTest() {
		boolean retVal_ = false;
		View threadsByDate = null;
		Document firstDoc = null;
		Document secondDoc = null;
		try {
			Session s = ExtLibUtil.getCurrentSession();
			Database currDb = s.getCurrentDatabase();
			threadsByDate = currDb.getView("AllThreadsByDate");
			ViewNavigator vNav = threadsByDate.createViewNav();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
			ViewEntry firstEnt = vNav.getNth(randomInt);
			ViewEntry secondEnt = vNav.getNext(firstEnt);
			firstDoc = firstEnt.getDocument();
			secondDoc = secondEnt.getDocument();
			DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
			DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
			Date firstDateJ = firstDate.toJavaDate();
			Date secondDateJ = secondDate.toJavaDate();
			if (firstDateJ.equals(secondDateJ)) {
				retVal_ = true;
			}
		} catch (NotesException e) {
			// doSomething
		} finally {
			try {
				threadsByDate.recycle();
				firstDoc.recycle();
				secondDoc.recycle();
			} catch (NotesException e) {
				// doSomething
			}
		}
		return retVal_;
	}

	public boolean dateTimeEqualsIgnoreDateTest() {
		boolean retVal_ = false;
		View threadsByDate = null;
		Document firstDoc = null;
		Document secondDoc = null;
		try {
			Session s = ExtLibUtil.getCurrentSession();
			Database currDb = s.getCurrentDatabase();
			threadsByDate = currDb.getView("AllThreadsByDate");
			ViewNavigator vNav = threadsByDate.createViewNav();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
			ViewEntry firstEnt = vNav.getNth(randomInt);
			ViewEntry secondEnt = vNav.getNext(firstEnt);
			firstDoc = firstEnt.getDocument();
			secondDoc = secondEnt.getDocument();
			DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
			DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
			Calendar c1 = GregorianCalendar.getInstance();
			Calendar c2 = GregorianCalendar.getInstance();
			c1.setTime(firstDate.toJavaDate());
			c1.set(Calendar.DAY_OF_MONTH, 1);
			c1.set(Calendar.MONTH, 0);
			c1.set(Calendar.YEAR, 2000);
			c2.setTime(secondDate.toJavaDate());
			c2.set(Calendar.DAY_OF_MONTH, 1);
			c2.set(Calendar.MONTH, 0);
			c2.set(Calendar.YEAR, 2000);
			if (c1.equals(c2)) {
				retVal_ = true;
			}
		} catch (NotesException e) {
			// doSomething
		} finally {
			try {
				threadsByDate.recycle();
				firstDoc.recycle();
				secondDoc.recycle();
			} catch (NotesException e) {
				// doSomething
			}
		}
		return retVal_;
	}

	public boolean dateTimeEqualsIgnoreTimeTest() {
		boolean retVal_ = false;
		View threadsByDate = null;
		Document firstDoc = null;
		Document secondDoc = null;
		try {
			Session s = ExtLibUtil.getCurrentSession();
			Database currDb = s.getCurrentDatabase();
			threadsByDate = currDb.getView("AllThreadsByDate");
			ViewNavigator vNav = threadsByDate.createViewNav();
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
			ViewEntry firstEnt = vNav.getNth(randomInt);
			ViewEntry secondEnt = vNav.getNext(firstEnt);
			firstDoc = firstEnt.getDocument();
			secondDoc = secondEnt.getDocument();
			DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
			DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
			Calendar c1 = GregorianCalendar.getInstance();
			Calendar c2 = GregorianCalendar.getInstance();
			c1.setTime(firstDate.toJavaDate());
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			c1.set(Calendar.MILLISECOND, 0);
			c2.setTime(secondDate.toJavaDate());
			c2.set(Calendar.HOUR_OF_DAY, 0);
			c2.set(Calendar.MINUTE, 0);
			c2.set(Calendar.SECOND, 0);
			c2.set(Calendar.MILLISECOND, 0);
			if (c1.equals(c2)) {
				retVal_ = true;
			}
		} catch (NotesException e) {
			// doSomething
		} finally {
			try {
				threadsByDate.recycle();
				firstDoc.recycle();
				secondDoc.recycle();
			} catch (NotesException e) {
				// doSomething
			}
		}
		return retVal_;
	}
}
