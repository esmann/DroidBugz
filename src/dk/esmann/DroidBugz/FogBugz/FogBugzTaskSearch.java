package dk.esmann.DroidBugz.FogBugz;

import android.app.Activity;
import android.util.Log;
import dk.esmann.DroidBugz.Constants;
import dk.esmann.DroidBugz.DroidBugz;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FogBugzTaskSearch extends FogBugzTask {

    public FogBugzTaskSearch(Activity parentActivity, String searchParam) {
        super(parentActivity);
        try {
            searchParam = URLEncoder.encode(searchParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(Constants.LOGTAG, "error encoding searchParam", e);
        }
        url = Constants.FOGBUGZ_URL + "token=" + preferences.getString("token", "") + "&cmd=search&cols=ixBug,sTitle&q=" + searchParam;
    }

    protected void handleResponse() {
        ArrayList<Case> cases = new ArrayList<Case>();
        NodeList caseNodes = doc.getDocumentElement().getElementsByTagName("case");
        for (int i = 0; i < caseNodes.getLength(); i++) {
            Node node = caseNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Integer bugNumber = XmlUtils.getIntegerValue("ixBug", element);
                String bugTitle = XmlUtils.getStringValue("sTitle", element);
                cases.add(new Case(bugNumber, bugTitle));
            }
        }
        result = cases;
    }

    protected void onPostExecute(Object result) {
        ArrayList<Case> cases = (ArrayList<Case>) result;
        ((DroidBugz)parentActivity).showCases(cases);
    }


}
