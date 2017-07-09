package com.renotekno.zcabez.networking_2;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    public static boolean IS_PREFERENCE_CHANGED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d("AsyncTask", "On Create SettingsActivity...");
        SettingsActivity.IS_PREFERENCE_CHANGED = false;
    }

    @Override
    protected void onStart() {
        Log.d("AsyncTask", "On Start SettingsActivity...");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("AsyncTask", "On Resume SettingsActivity...");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("AsyncTask", "On Pause SettingsActivity...");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("AsyncTask", "On Stop SettingsActivity...");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("AsyncTask", "On Destroy SettingsActivity...");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("AsyncTask", "On Restart SettingsActivity...");

        super.onRestart();
    }

    public static class EarthQuakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMag = findPreference(getString(R.string.settings_min_magnitude_key));
            initiatePreferenceState(minMag);
        }

        private void initiatePreferenceState(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String prefVal = sharedPref.getString(getStrignFromId(preference, R.string.settings_min_magnitude_key),
                    getStrignFromId(preference, R.string.settings_min_magnitude_default));
            onPreferenceChange(preference, prefVal);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            Log.d("PREFERENCE_CHANGE", stringValue);
            preference.setSummary(stringValue);

            SettingsActivity.IS_PREFERENCE_CHANGED = true;
            return true;
        }

        public String getStrignFromId(Preference preference, int id){
            return preference.getContext().getString(id);
        }
    }
}
