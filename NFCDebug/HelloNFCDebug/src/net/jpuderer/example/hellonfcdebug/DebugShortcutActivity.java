package net.jpuderer.example.hellonfcdebug;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DebugShortcutActivity extends ListActivity {
    private final static String DEBUG_SHORTCUT_CATEGORY = "net.jpuderer.example.DEBUG";

    private class ActivityAdapter extends ArrayAdapter<ResolveInfo> {
        private Context mContext;
        private int mLayoutResourceId;
        protected List<ResolveInfo> mActivities = null;

        ActivityAdapter(Context context, int layoutResourceId, List<ResolveInfo> activities) {
            super(context, layoutResourceId, activities);

            this.mLayoutResourceId = layoutResourceId;
            this.mContext = context;
            this.mActivities = activities;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PackageManager pm = mContext.getPackageManager();
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(mLayoutResourceId, parent, false);
            }

            ResolveInfo info = mActivities.get(position);
            TextView activityName = (TextView) row.findViewById(android.R.id.text1);
            activityName.setText(info.loadLabel(pm));

            Drawable icon = info.loadIcon(pm);
            icon.setBounds(0, 0, 72, 72);

            activityName.setCompoundDrawables(icon, null, null, null);
            activityName.setCompoundDrawablePadding(16);

            return row;
        }
    }

    private List<ResolveInfo> mActivities;
    private ActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager pm = getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(DEBUG_SHORTCUT_CATEGORY);
        mActivities = pm.queryIntentActivities(intent, 0);

        mAdapter = new ActivityAdapter(this, android.R.layout.simple_list_item_1, mActivities);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ActivityInfo activity = mActivities.get(position).activityInfo;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName componentName = new ComponentName(activity.applicationInfo.packageName,
                activity.name);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setComponent(componentName);
        startActivity(intent);
        finish();
    }
}
