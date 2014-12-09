
package com.android.settings.rastapop;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SystemSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // advanced reboot
    private static final String ADVANCED_REBOOT = "advanced_reboot";
    // kill-app long press back
    private static final String KILL_APP_LONGPRESS_BACK = "kill_app_longpress_back";

    // advanced reboot
    private SwitchPreference mAdvancedReboot;
    // kill-app long press back
    private SwitchPreference mKillAppLongPressBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ras_system_settings);

        // advanced reboot
        mAdvancedReboot = (SwitchPreference) findPreference(ADVANCED_REBOOT);
        mAdvancedReboot.setOnPreferenceChangeListener(this);
        int advancedReboot = Settings.Secure.getInt(getContentResolver(),
                ADVANCED_REBOOT, 0);
        mAdvancedReboot.setChecked(advancedReboot != 0);

        // kill-app long press back
        mKillAppLongPressBack = (SwitchPreference) findPreference(KILL_APP_LONGPRESS_BACK);
        mKillAppLongPressBack.setOnPreferenceChangeListener(this);
        int killAppLongPressBack = Settings.Secure.getInt(getContentResolver(),
                KILL_APP_LONGPRESS_BACK, 0);
        mKillAppLongPressBack.setChecked(killAppLongPressBack != 0);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

        // advanced reboot
        if (preference == mAdvancedReboot) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putInt(getContentResolver(), ADVANCED_REBOOT,
                    value ? 1 : 0);
            return true;
        }

        // kill-app long press back
        else if (preference == mKillAppLongPressBack) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putInt(getContentResolver(), KILL_APP_LONGPRESS_BACK,
                    value ? 1 : 0);
            return true;
        }
        return false;
    }
}
