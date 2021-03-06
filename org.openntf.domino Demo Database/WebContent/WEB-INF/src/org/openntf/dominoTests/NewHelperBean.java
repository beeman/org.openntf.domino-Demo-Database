package org.openntf.dominoTests;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.openntf.domino.Database;
import org.openntf.domino.DocumentCollection;
import org.openntf.domino.Name;
import org.openntf.domino.Session;
import org.openntf.domino.View;
import org.openntf.domino.email.DominoEmail;
import org.openntf.domino.formula.AtFormulaParser;
import org.openntf.domino.formula.FormulaContext;
import org.openntf.domino.formula.ast.SimpleNode;
import org.openntf.domino.helpers.DocumentSorter;
import org.openntf.domino.helpers.DocumentSyncHelper;
import org.openntf.domino.utils.Factory;
import org.openntf.domino.xsp.XspOpenLogUtil;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class NewHelperBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewHelperBean() {

	}

	public void syncDatabases() {
		Session s = Factory.getSession();
		Database currDb = s.getCurrentDatabase();
		Utils.addAllListeners(currDb);
		java.util.Map<Object, String> syncMap = new java.util.HashMap<Object, String>();
		syncMap.put("Key", "State");
		syncMap.put("Name", "StateName");
		syncMap.put("@Now", "LastSync");
		DocumentSyncHelper helper = new DocumentSyncHelper(DocumentSyncHelper.Strategy.CREATE_AND_REPLACE, syncMap,
				currDb.getServer(), currDb.getFilePath(), "AllContactsByState", "Key");
		View states = currDb.getView("AllStates");
		DocumentCollection sourceCollection = states.getAllDocuments();
		helper.process(sourceCollection);
		ExtLibUtil.getViewScope().put("javaTest", "Done");
	}

	public void sendSimpleEmail() {
		DominoEmail myEmail = new DominoEmail();
		myEmail.createSimpleEmail("pwithers@intec.co.uk", "", "", "OpenNTF Domino API Email",
				"this is an email from the OpenNTF Domino API", "");
	}

	public void getGroups() {
		Session currSess = Factory.getSession();
		Name currName = currSess.createName(currSess.getEffectiveUserName());
		ExtLibUtil.getViewScope().put("javaTest", currName.getGroups(currSess.getServerName()));
	}

	public DocumentCollection getSortedCollection() {
		String sSearch = "FIELD Author contains \"Aline Winters\"";
		org.openntf.domino.DocumentCollection dc = Factory.getSession().getCurrentDatabase().FTSearch(sSearch, 500);
		List criteria = new ArrayList();
		criteria.add("Date");
		DocumentSorter sorter = new org.openntf.domino.helpers.DocumentSorter(dc, criteria);
		DocumentCollection results = sorter.sort();
		ExtLibUtil.getViewScope().put("javaTest", results.getCount());
		return results;
	}

	public void processFormula() {
		try {
			SimpleNode n = null;
			List<Object> v = null;

			AtFormulaParser parser = AtFormulaParser.getInstance();
			StringReader sr = new StringReader((String) ExtLibUtil.getViewScope().get("javaFormula"));
			parser.ReInit(sr);
			n = parser.Parse();
			FormulaContext ctx = new FormulaContext(null, parser.getFormatter());
			v = n.evaluate(ctx);
			ExtLibUtil.getViewScope().put("javaTest", v.toArray());
		} catch (Throwable t) {
			XspOpenLogUtil.logError(t);
		}
	}
}
