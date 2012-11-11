package dk.esmann.DroidBugz.FogBugz;

import android.app.Activity;
import dk.esmann.DroidBugz.Constants;

public class FogBugzTaskStartWork extends FogBugzTask {

    public FogBugzTaskStartWork(Activity parentActivity, Integer caseNumber) {
        super(parentActivity);
        url = Constants.FOGBUGZ_URL + "token=" + preferences.getString("token", "") + "&cmd=startWork&ixBug=" + caseNumber;
    }

    protected void handleResponse() {

    }

    protected void onPostExecute(Object result) {
        new FogBugzTaskUserInfo(parentActivity).execute();
    }
}
