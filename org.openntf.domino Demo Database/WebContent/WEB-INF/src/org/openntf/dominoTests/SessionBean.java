package org.openntf.dominoTests;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import org.openntf.domino.Database;
import org.openntf.domino.DateTime;
import org.openntf.domino.Document;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.ViewEntry;
import org.openntf.domino.ViewNavigator;
import org.openntf.domino.utils.Factory;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class SessionBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String dateTimeIsBeforeTest;

	public SessionBean() {

	}

	public void runDateTimes() {
		Session s = Factory.fromLotus(ExtLibUtil.getCurrentSession(), org.openntf.domino.Session.class, null);
		Date d = new Date();
		DateTime dt = s.createDateTime(d);
		DateTime dt2 = s.createDateTime(d);
		StringBuilder sb = new StringBuilder();
		sb.append(Utils.doChecks(dt, dt2));
		sb.append("<br/><br/>");
		dt.adjustHour(1);
		sb.append(Utils.doChecks(dt, dt2));
		sb.append("<br/><br/>");
		dt.adjustDay(-1);
		sb.append(Utils.doChecks(dt, dt2));
		sb.append("<br/><br/>");
		dt.adjustHour(-1);
		sb.append(Utils.doChecks(dt, dt2));
		ExtLibUtil.getViewScope().put("datesTestJava", sb.toString());
	}

	public String getDateTimeIsBeforeTest() {
		String retVal_ = "";
		Session s = Factory.fromLotus(ExtLibUtil.getCurrentSession(), org.openntf.domino.Session.class, null);
		Database currDb = s.getCurrentDatabase();
		View threadsByDate = currDb.getView("AllThreadsByDate");
		ViewNavigator vNav = threadsByDate.createViewNav();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(30);
		ViewEntry firstEnt = vNav.getNth(randomInt);
		while (!firstEnt.isDocument()) {
			firstEnt = vNav.getNext();
		}
		randomInt = randomGenerator.nextInt(30);
		ViewEntry secondEnt = vNav.getNth(randomInt);
		while (!secondEnt.isDocument()) {
			secondEnt = vNav.getNext();
		}
		Document firstDoc = firstEnt.getDocument();
		Document secondDoc = secondEnt.getDocument();
		String firstDt = firstDoc.getFirstItem("Date").getText();
		String secondDt = secondDoc.getFirstItem("Date").getText();
		DateTime firstDate = s.createDateTime(firstDt);
		DateTime secondDate = s.createDateTime(secondDt);
		retVal_ = "Comparing " + firstDt + " (" + firstDoc.getUniversalID() + ") with " + secondDt + " ("
				+ secondDoc.getUniversalID() + ")...";
		if (firstDate.isBefore(secondDate)) {
			retVal_ += "first before second";
		} else {
			retVal_ += "first NOT before second";
		}
		return retVal_;
	}

	public boolean dateTimeIsAfterTest() {
		boolean retVal_ = false;
		Session s = Factory.fromLotus(ExtLibUtil.getCurrentSession(), org.openntf.domino.Session.class, null);
		Database currDb = s.getCurrentDatabase();
		View threadsByDate = currDb.getView("AllThreadsByDate");
		ViewNavigator vNav = threadsByDate.createViewNav();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
		ViewEntry firstEnt = vNav.getNth(randomInt);
		ViewEntry secondEnt = vNav.getNext(firstEnt);
		Document firstDoc = firstEnt.getDocument();
		Document secondDoc = secondEnt.getDocument();
		DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
		DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
		if (firstDate.isAfter(secondDate)) {
			retVal_ = true;
		}
		return retVal_;
	}

	public boolean dateTimeEqualsTest() {
		boolean retVal_ = false;
		Session s = Factory.fromLotus(ExtLibUtil.getCurrentSession(), org.openntf.domino.Session.class, null);
		Database currDb = s.getCurrentDatabase();
		View threadsByDate = currDb.getView("AllThreadsByDate");
		ViewNavigator vNav = threadsByDate.createViewNav();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
		ViewEntry firstEnt = vNav.getNth(randomInt);
		ViewEntry secondEnt = vNav.getNext(firstEnt);
		Document firstDoc = firstEnt.getDocument();
		Document secondDoc = secondEnt.getDocument();
		DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
		DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
		if (firstDate.equals(secondDate)) {
			retVal_ = true;
		}
		return retVal_;
	}

	public boolean dateTimeEqualsIgnoreDateTest() {
		boolean retVal_ = false;
		Session s = Factory.fromLotus(ExtLibUtil.getCurrentSession(), org.openntf.domino.Session.class, null);
		Database currDb = s.getCurrentDatabase();
		View threadsByDate = currDb.getView("AllThreadsByDate");
		ViewNavigator vNav = threadsByDate.createViewNav();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
		ViewEntry firstEnt = vNav.getNth(randomInt);
		ViewEntry secondEnt = vNav.getNext(firstEnt);
		Document firstDoc = firstEnt.getDocument();
		Document secondDoc = secondEnt.getDocument();
		DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
		DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
		if (firstDate.equalsIgnoreDate(secondDate)) {
			retVal_ = true;
		}
		return retVal_;
	}

	public boolean dateTimeEqualsIgnoreTimeTest() {
		boolean retVal_ = false;
		Session s = Factory.fromLotus(ExtLibUtil.getCurrentSession(), org.openntf.domino.Session.class, null);
		Database currDb = s.getCurrentDatabase();
		View threadsByDate = currDb.getView("AllThreadsByDate");
		ViewNavigator vNav = threadsByDate.createViewNav();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(threadsByDate.getEntryCount());
		ViewEntry firstEnt = vNav.getNth(randomInt);
		ViewEntry secondEnt = vNav.getNext(firstEnt);
		Document firstDoc = firstEnt.getDocument();
		Document secondDoc = secondEnt.getDocument();
		DateTime firstDate = s.createDateTime(firstDoc.getFirstItem("Date").getText());
		DateTime secondDate = s.createDateTime(secondDoc.getFirstItem("Date").getText());
		if (firstDate.equalsIgnoreTime(secondDate)) {
			retVal_ = true;
		}
		return retVal_;
	}
}
