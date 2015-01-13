
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
    private SwitchPreference mAdvancedReboot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ras_system_settings);

        // advanced reboot
        mAdvancedReboot = (SwitchPreference) findPreference(ADVANCED_REBOOT);
        mAdvancedReboot.setOnPreferenceChangeListener(this);
        int advancedReboot = Settings.Secure.getInt(getContentResolver(),
                ADVANCED_REBOOT, 1);
        mAdvancedReboot.setChecked(advancedReboot != 0);
    }

    //@Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

        // advanced reboot
        if (preference == mAdvancedReboot) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putInt(getContentResolver(), ADVANCED_REBOOT,
                    value ? 1 : 0);
            return true;
        }
        return false;
    }
}
