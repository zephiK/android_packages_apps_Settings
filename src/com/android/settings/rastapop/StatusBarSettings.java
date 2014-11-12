
package com.android.settings.rastapop;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    // General
    private static String STATUS_BAR_GENERAL_CATEGORY = "status_bar_general_category";
    // Native battery percentage
    private static final String STATUS_BAR_NATIVE_BATTERY_PERCENTAGE = "status_bar_native_battery_percentage";

    // General
    private PreferenceCategory mStatusBarGeneralCategory;
    // Native battery percentage
    private SwitchPreference mStatusBarNativeBatteryPercentage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.status_bar_settings);

        // General category
        mStatusBarGeneralCategory = (PreferenceCategory) findPreference(STATUS_BAR_GENERAL_CATEGORY);

        // Native battery percentage
        mStatusBarNativeBatteryPercentage = (SwitchPreference) findPreference(STATUS_BAR_NATIVE_BATTERY_PERCENTAGE);
        mStatusBarNativeBatteryPercentage.setOnPreferenceChangeListener(this);
        int statusBarNativeBatteryPercentage = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_NATIVE_BATTERY_PERCENTAGE, 0);
        mStatusBarNativeBatteryPercentage.setChecked(statusBarNativeBatteryPercentage != 0);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mStatusBarNativeBatteryPercentage) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_NATIVE_BATTERY_PERCENTAGE,
                    value ? 1 : 0);
            return true;
        }
        return false;
    }

}
