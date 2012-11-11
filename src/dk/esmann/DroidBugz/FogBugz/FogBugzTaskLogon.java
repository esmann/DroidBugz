package dk.esmann.DroidBugz.FogBugz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import dk.esmann.DroidBugz.Constants;
import dk.esmann.DroidBugz.DroidBugz;
import org.w3c.dom.NodeList;

public class FogBugzTaskLogon extends FogBugzTask {

    public FogBugzTaskLogon(Activity parentActivity, String email, String password) {
        super(parentActivity);
        url = Constants.FOGBUGZ_URL + "cmd=logon&email=" + email + "&password=" + password;

    }

    void handleResponse() {
        NodeList token = doc.getDocumentElement().getElementsByTagName("token");
        String sToken = token.item(0).getTextContent();
        SharedPreferences settings = parentActivity.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", sToken);
        editor.commit();
    }

}
