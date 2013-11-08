package com.android.settings.chroma;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static String STATUS_BAR_GENERAL_CATEGORY = "category_battery_options";
    private static final String STATUS_BAR_NATIVE_BATTERY_PERCENTAGE = "status_bar_native_battery_percentage";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.chroma_settings_navigation);
        // General category
        mStatusBarGeneralCategory = (PreferenceCategory) findPreference(STATUS_BAR_GENERAL_CATEGORY);

        // Native battery percentage
        mStatusBarNativeBatteryPercentage = (CheckBoxPreference) getPreferenceScreen()
                .findPreference(STATUS_BAR_NATIVE_BATTERY_PERCENTAGE);
        mStatusBarNativeBatteryPercentage.setChecked((Settings.System.getInt(getActivity()
                .getApplicationContext().getContentResolver(),
                Settings.System.STATUS_BAR_NATIVE_BATTERY_PERCENTAGE, 0) == 1));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
		// preference changes here
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
       if (preference == mStatusBarNativeBatteryPercentage) {
            value = mStatusBarNativeBatteryPercentage.isChecked();
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_NATIVE_BATTERY_PERCENTAGE, value ? 1 : 0);
            return true;
        }
 		return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}