package net.jpuderer.example.hellonfcdebug;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class NFCDebug extends Activity {
    private static final String TAG = "NFCDebug";

    private static final String URI_HOST = "net.jpuderer.example.hellonfcdebug";
    private static final String PARAM_LAUNCH_DEBUG_MENU = "dbgmenu";
    private static final String PARAM_LAUNCH_DEBUG_TOOL = "dbgtool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getIntent().getDataString();
        if (data == null) {
            Log.w(TAG, "No URI in intent.");
            finish();
            return;
        }
        Log.d(TAG, "Received intent: " + data);
        Uri uri = Uri.parse(data);
        String host = uri.getHost();
        if (!host.equalsIgnoreCase(URI_HOST)) {
            Log.w(TAG, "Invalid host in intent.");
            finish();
            return;
        }

        for (String key : uri.getQueryParameterNames()) {
            if (key.equalsIgnoreCase(PARAM_LAUNCH_DEBUG_TOOL)) {
                Log.d(TAG, "Launching Debug Tool.");
                launchDebugTool();
            } else if (key.equalsIgnoreCase(PARAM_LAUNCH_DEBUG_MENU)) {
                Log.d(TAG, "Launching Debug Menu.");
                launchDebugMenu();
            }
        }
        finish();
    }

    void launchDebugMenu() {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), DebugShortcutActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void launchDebugTool() {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), DebugTool.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
