package dk.esmann.DroidBugz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import dk.esmann.DroidBugz.FogBugz.FogBugzTaskLogon;

public class LoginActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
    }

    public void doLogin(View view) {
        String email, password;
        EditText et = (EditText) findViewById(R.id.username);
        email = et.getText().toString();
        et = (EditText) findViewById(R.id.password);
        password = et.getText().toString();

        new FogBugzTaskLogon(this, email, password).execute();
        this.finish();
    }

    public void doCancel(View view) {
        this.finish();
    }
}