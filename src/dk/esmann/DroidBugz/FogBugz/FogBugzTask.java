package dk.esmann.DroidBugz.FogBugz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import dk.esmann.DroidBugz.Constants;
import dk.esmann.DroidBugz.DroidBugz;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public abstract class FogBugzTask extends AsyncTask<Void, Void, Object> {
    protected Activity parentActivity;
    protected Document doc;
    protected String url;
    protected SharedPreferences preferences;
    protected Object result;
    abstract void handleResponse();

    public FogBugzTask(Activity parentActivity) {
        this.parentActivity = parentActivity;
        preferences = parentActivity.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);

    }

    protected Object doInBackground(Void... notUsed) {
        Log.d(Constants.LOGTAG, "using url: " + url);

        HttpGet uri = new HttpGet(url);

        HttpResponse response = null;
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            response = client.execute(uri);
        } catch (Exception e) {
            // TODO better exception handling
            Log.d(Constants.LOGTAG, "http error", e);
        }

        StatusLine status = response != null ? response.getStatusLine() : null;
        if (status != null && status.getStatusCode() != 200) {
            Log.d(Constants.LOGTAG, "HTTP error, invalid server status code: " + response.getStatusLine());
            return null;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(response.getEntity().getContent());
            NodeList errors = doc.getDocumentElement().getElementsByTagName("error");
            if (errors.getLength() > 0) {
                // handle errors
            } else {
                handleResponse();
            }
        } catch (Exception e) {
            // TODO better exception handling
            Log.d(Constants.LOGTAG, "xml error", e);
        }

        return result;
    }

}
