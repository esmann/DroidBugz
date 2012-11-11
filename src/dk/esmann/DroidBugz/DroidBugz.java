package dk.esmann.DroidBugz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import dk.esmann.DroidBugz.FogBugz.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DroidBugz extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ArrayList<Case> cases;
    private Case selectedCase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        String token = settings.getString("token", "");


        if (token.equals("")) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            this.startActivityForResult(loginIntent, RESULT_OK);
        } else {
            new FogBugzTaskUserInfo(this).execute(); // TODO move this it's own activity or somewhere else, so we don't poke the api when changing perspective.
            new FogBugzTaskSearch(this, "\"intern tid\"").execute();
        }

    }

    public void showCases(ArrayList<Case> cases) {
        this.cases = cases;
        ListView caseList = (ListView) findViewById(R.id.caseList);

        caseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              onCaseClick(position);
          }
        });
        ArrayAdapter<Case> adapter = new ArrayAdapter<Case>(this, android.R.layout.simple_list_item_1, android.R.id.text1, cases);
        caseList.setAdapter(adapter);
    }


    public void onCaseClick(int position) {
        selectedCase = cases.get(position);
        Integer workingOn = Integer.parseInt(getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE).getString("workingOn", ""));

        Button button = (Button) findViewById(R.id.startStopButton);
        if (workingOn.equals(selectedCase.getNumber())) {
            button.setText(getText(R.string.stop) + " " + selectedCase.getNumber());
        } else {
            button.setText(getText(R.string.start) + " " + selectedCase.getNumber());
        }
        button.setEnabled(true);

        Log.i(Constants.LOGTAG, "Case clicked " + position + " " + selectedCase.getNumber());
    }

    public void onButtonClick(View view) {
        if (selectedCase != null) {
            Integer workingOn = Integer.parseInt(getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE).getString("workingOn", ""));
            if (selectedCase.getNumber().equals(workingOn)) {
                Log.i(Constants.LOGTAG, "stopping " + selectedCase.getNumber());
                new FogBugzTaskStopWork(this).execute();
            } else {
                Log.i(Constants.LOGTAG, "stopping " + selectedCase.getNumber());
                new FogBugzTaskStartWork(this, selectedCase.getNumber()).execute();
            }
        }


    }
}
