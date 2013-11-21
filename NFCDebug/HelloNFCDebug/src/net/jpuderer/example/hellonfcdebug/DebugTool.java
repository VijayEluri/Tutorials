package net.jpuderer.example.hellonfcdebug;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DebugTool extends Activity implements Button.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_tool);

        Button crashButton = (Button) this.findViewById(R.id.crash_button);
        crashButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        throw new RuntimeException("Crash!!!");
    }
}
