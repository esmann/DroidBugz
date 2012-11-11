package dk.esmann.DroidBugz.FogBugz;

import android.app.Activity;
import dk.esmann.DroidBugz.Constants;

public class FogBugzTaskStopWork extends FogBugzTask {

    public FogBugzTaskStopWork(Activity parentActivity) {
        super(parentActivity);
        url = Constants.FOGBUGZ_URL + "token=" + preferences.getString("token", "") + "&cmd=stopWork";
    }

    protected void handleResponse() {

    }

    protected void onPostExecute(Object result) {
        new FogBugzTaskUserInfo(parentActivity).execute();
    }

}
