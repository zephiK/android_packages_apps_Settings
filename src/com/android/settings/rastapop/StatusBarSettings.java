
package com.android.settings.rastapop;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // Quick Pulldown
    public static final String STATUS_BAR_QUICK_QS_PULLDOWN = "status_bar_quick_qs_pulldown";
    // status bar brightness control
    private static final String STATUS_BAR_BRIGHTNESS_CONTROL = "status_bar_brightness_control";
    // Statusbar battery percent
    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";

    private static final String TAG = "StatusBar";
    private static final String STATUS_BAR_BATTERY_STYLE = "status_bar_battery_style";
    private static final String STATUS_BAR_BATTERY_STYLE_HIDDEN = "4";
    private static final String STATUS_BAR_BATTERY_STYLE_TEXT = "6";
	
    // Quick Pulldown
    private SwitchPreference mStatusBarQuickQsPulldown;
    // status bar brightness control
    private SwitchPreference mStatusBarBrightnessControl;
    // status bar battery percentage style
    private ListPreference mStatusBarBattery;
    private ListPreference mStatusBarBatteryShowPercent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ras_status_bar_settings);

        // status bar brightness control
        mStatusBarBrightnessControl = (SwitchPreference) findPreference(STATUS_BAR_BRIGHTNESS_CONTROL);
        mStatusBarBrightnessControl.setOnPreferenceChangeListener(this);
        int statusBarBrightnessControl = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_BRIGHTNESS_CONTROL, 0);
        mStatusBarBrightnessControl.setChecked(statusBarBrightnessControl != 0);

        //Statusbar battery percent
        mStatusBarBattery = (ListPreference) findPreference(STATUS_BAR_BATTERY_STYLE);

        int batteryStyle = Settings.System.getInt(resolver, Settings.System.STATUS_BAR_BATTERY_STYLE, 0);
        mStatusBarBattery.setValue(String.valueOf(batteryStyle));
        mStatusBarBattery.setSummary(mStatusBarBattery.getEntry());
        mStatusBarBattery.setOnPreferenceChangeListener(this);

        mStatusBarBatteryShowPercent = (ListPreference) findPreference(STATUS_BAR_SHOW_BATTERY_PERCENT);

        int batteryShowPercent = Settings.System.getInt(resolver, Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0);
        mStatusBarBatteryShowPercent.setValue(String.valueOf(batteryShowPercent));
        mStatusBarBatteryShowPercent.setSummary(mStatusBarBatteryShowPercent.getEntry());
        mStatusBarBatteryShowPercent.setOnPreferenceChangeListener(this);

        // Quick Pulldown
        mStatusBarQuickQsPulldown = (SwitchPreference) getPreferenceScreen()
                .findPreference(STATUS_BAR_QUICK_QS_PULLDOWN);
        mStatusBarQuickQsPulldown.setChecked((Settings.System.getInt(getActivity()
                .getApplicationContext().getContentResolver(),
                Settings.System.STATUS_BAR_QUICK_QS_PULLDOWN, 0) == 1));
        mStatusBarQuickQsPulldown.setOnPreferenceChangeListener(this);
}

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

        // status bar brightness control
        if (preference == mStatusBarBrightnessControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_BRIGHTNESS_CONTROL,
                    value ? 1 : 0);
            return true;
        }

        // status bar battery percentage style
        ContentResolver resolver = getActivity().getContentResolver();
        else if (preference == mStatusBarBattery) {
            int batteryStyle = Integer.valueOf((String) objValue);
            int index = mStatusBarBattery.findIndexOfValue((String) objValue);
            Settings.System.putInt(resolver,
                    Settings.System.STATUS_BAR_BATTERY_STYLE, batteryStyle);
            mStatusBarBattery.setSummary(mStatusBarBattery.getEntries()[index]);
            enableStatusBarBatteryDependents((String) objValue);
            return true;
        } else if (preference == mStatusBarBatteryShowPercent) {
            int batteryShowPercent = Integer.valueOf((String) objValue);
            int index = mStatusBarBatteryShowPercent.findIndexOfValue((String) objValue);
            Settings.System.putInt(resolver,
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, batteryShowPercent);
            mStatusBarBatteryShowPercent.setSummary(mStatusBarBatteryShowPercent.getEntries()[index]);
            return true;
        }
	// status bar quick pull down
         else if (preference == mStatusBarQuickQsPulldown) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_QUICK_QS_PULLDOWN, value ? 1 : 0);
            return true;
	}
        return false;
    }
}

    private void enableStatusBarBatteryDependents(String value) {
        boolean enabled = !(value.equals(STATUS_BAR_BATTERY_STYLE_TEXT)
                || value.equals(STATUS_BAR_BATTERY_STYLE_HIDDEN));
        mStatusBarBatteryShowPercent.setEnabled(enabled);
    }
