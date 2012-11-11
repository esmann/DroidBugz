package dk.esmann.DroidBugz.FogBugz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import dk.esmann.DroidBugz.Constants;
import dk.esmann.DroidBugz.DroidBugz;

public class FogBugzTaskUserInfo extends FogBugzTask {

    public FogBugzTaskUserInfo(Activity parentActivity) {
        super(parentActivity);
        url = Constants.FOGBUGZ_URL + "token=" + preferences.getString("token", "") + "&cmd=viewPerson";
    }

    public void handleResponse() {
        SharedPreferences settings = parentActivity.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        try {
            editor.putString("fullName", XmlUtils.getString(doc, "sFullName"));
            editor.putString("email", XmlUtils.getString(doc, "sEmail"));
            editor.putString("workingOn", XmlUtils.getString(doc, "ixBugWorkingOn"));
            editor.commit();
        } catch (Exception e) {
            Log.i(Constants.LOGTAG, "error getting userinfo", e);
        }
    }

    protected void onPostExecute(Object result) {
        Toast.makeText(parentActivity, "Done", 2000).show();
    }

}
