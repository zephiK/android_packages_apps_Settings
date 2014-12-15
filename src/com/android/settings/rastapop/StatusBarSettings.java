
package com.android.settings.rastapop;

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

    // status bar battery percentage style
    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";
    // status bar brightness control
    private static final String STATUS_BAR_BRIGHTNESS_CONTROL = "status_bar_brightness_control";
    // status bar power menu
    private static final String STATUS_BAR_POWER_MENU = "status_bar_power_menu";

    // status bar battery percentage style
    private ListPreference mStatusBarBatteryPercentageStyle;
    // status bar brightness control
    private SwitchPreference mStatusBarBrightnessControl;
    // status bar power menu
    private ListPreference mStatusBarPowerMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ras_status_bar_settings);

        // status bar battery percentage style
        mStatusBarBatteryPercentageStyle = (ListPreference) findPreference(STATUS_BAR_SHOW_BATTERY_PERCENT);
        int statusBarBatteryPercentageStyle = Settings.System.getInt(getContentResolver(),
                Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT, 0);
        mStatusBarBatteryPercentageStyle.setValue(String.valueOf(statusBarBatteryPercentageStyle));
        mStatusBarBatteryPercentageStyle.setSummary(mStatusBarBatteryPercentageStyle.getEntry());
        mStatusBarBatteryPercentageStyle.setOnPreferenceChangeListener(this);

        // status bar brightness control
        mStatusBarBrightnessControl = (SwitchPreference) findPreference(STATUS_BAR_BRIGHTNESS_CONTROL);
        mStatusBarBrightnessControl.setOnPreferenceChangeListener(this);
        int statusBarBrightnessControl = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_BRIGHTNESS_CONTROL, 0);
        mStatusBarBrightnessControl.setChecked(statusBarBrightnessControl != 0);
        try {
            if (Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                mStatusBarBrightnessControl.setEnabled(false);
                mStatusBarBrightnessControl.setSummary(R.string.status_bar_brightness_control_info);
            }
        } catch (SettingNotFoundException e) {
            // what do you expect me to do?
        }

        // status bar power menu
        mStatusBarPowerMenu = (ListPreference) findPreference(STATUS_BAR_POWER_MENU);
        mStatusBarPowerMenu.setOnPreferenceChangeListener(this);
        int statusBarPowerMenu = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_POWER_MENU, 0);
        mStatusBarPowerMenu.setValue(String.valueOf(statusBarPowerMenu));
        mStatusBarPowerMenu.setSummary(mStatusBarPowerMenu.getEntry());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

        // status bar battery percentage style
        if (preference == mStatusBarBatteryPercentageStyle) {
            int statusBarBatteryPercentageStyleValue = Integer.valueOf((String) objValue);
            int statusBarBatteryPercentageStyleIndex = mStatusBarBatteryPercentageStyle
                    .findIndexOfValue((String) objValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_BATTERY_PERCENT,
                    statusBarBatteryPercentageStyleValue);
            mStatusBarBatteryPercentageStyle.setSummary(mStatusBarBatteryPercentageStyle
                    .getEntries()[statusBarBatteryPercentageStyleIndex]);
            return true;
        }

        // status bar brightness control
        else if (preference == mStatusBarBrightnessControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_BRIGHTNESS_CONTROL,
                    value ? 1 : 0);
            return true;
        }

        // status bar power menu
        else if (preference == mStatusBarPowerMenu) {
            String statusBarPowerMenu = (String) objValue;
            int statusBarPowerMenuValue = Integer.parseInt(statusBarPowerMenu);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_POWER_MENU, statusBarPowerMenuValue);
            int statusBarPowerMenuIndex = mStatusBarPowerMenu
                    .findIndexOfValue(statusBarPowerMenu);
            mStatusBarPowerMenu
                    .setSummary(mStatusBarPowerMenu.getEntries()[statusBarPowerMenuIndex]);
            return true;
        }
        return false;
    }
}
