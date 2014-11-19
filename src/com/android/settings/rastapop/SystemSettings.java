
package com.android.settings.rastapop;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SystemSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // status bar native battery percentage
    private static final String STATUS_BAR_NATIVE_BATTERY_PERCENTAGE = "status_bar_native_battery_percentage";

    // navigation bar height
    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";

    // volume rocker wake
    private static final String VOLUME_ROCKER_WAKE = "volume_rocker_wake";
    // volume key adjust sound
    private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
    // volume key cursor control
    private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";

    // status bar native battery percentage
    private SwitchPreference mStatusBarNativeBatteryPercentage;
    // navigation bar height
    private ListPreference mNavigationBarHeight;
    // volume rocker wake
    private SwitchPreference mVolumeRockerWake;
    // volume key adjust sound
    private SwitchPreference mVolumeKeyAdjustSound;
    // volume key cursor control
    private ListPreference mVolumeKeyCursorControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.system_settings);

        // status bar native battery percentage
        mStatusBarNativeBatteryPercentage = (SwitchPreference) findPreference(STATUS_BAR_NATIVE_BATTERY_PERCENTAGE);
        mStatusBarNativeBatteryPercentage.setOnPreferenceChangeListener(this);
        int statusBarNativeBatteryPercentage = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_NATIVE_BATTERY_PERCENTAGE, 0);
        mStatusBarNativeBatteryPercentage.setChecked(statusBarNativeBatteryPercentage != 0);
        
        // navigation bar height
        mNavigationBarHeight = (ListPreference) findPreference(NAVIGATION_BAR_HEIGHT);
        mNavigationBarHeight.setOnPreferenceChangeListener(this);
        int statusNavigationBarHeight = Settings.System.getInt(getContentResolver(),
                Settings.System.NAVIGATION_BAR_HEIGHT, 48);
        mNavigationBarHeight.setValue(String.valueOf(statusNavigationBarHeight));
        mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntry());

        // volume rocker wake
        mVolumeRockerWake = (SwitchPreference) findPreference(VOLUME_ROCKER_WAKE);
        mVolumeRockerWake.setOnPreferenceChangeListener(this);
        int volumeRockerWake = Settings.System.getInt(getContentResolver(),
                VOLUME_ROCKER_WAKE, 0);
        mVolumeRockerWake.setChecked(volumeRockerWake != 0);

        // volume key adjust sound
        mVolumeKeyAdjustSound = (SwitchPreference) findPreference(VOLUME_KEY_ADJUST_SOUND);
        mVolumeKeyAdjustSound.setOnPreferenceChangeListener(this);
        mVolumeKeyAdjustSound.setChecked(Settings.System.getInt(getContentResolver(),
                VOLUME_KEY_ADJUST_SOUND, 1) != 0);

        // volume key cursor control
        mVolumeKeyCursorControl = (ListPreference) findPreference(VOLUME_KEY_CURSOR_CONTROL);
        if (mVolumeKeyCursorControl != null) {
            mVolumeKeyCursorControl.setOnPreferenceChangeListener(this);
            mVolumeKeyCursorControl.setValue(Integer.toString(Settings.System.getInt(
                    getContentResolver(),
                    Settings.System.VOLUME_KEY_CURSOR_CONTROL, 0)));
            mVolumeKeyCursorControl.setSummary(mVolumeKeyCursorControl.getEntry());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {

        // status bar native battery percentage
        if (preference == mStatusBarNativeBatteryPercentage) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_NATIVE_BATTERY_PERCENTAGE,
                    value ? 1 : 0);
            return true;
        }

        // navigation bar height
        else if (preference == mNavigationBarHeight) {
            int statusNavigationBarHeight = Integer.valueOf((String) objValue);
            int index = mNavigationBarHeight.findIndexOfValue((String) objValue);
            Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT,
                    statusNavigationBarHeight);
            mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntries()[index]);
        return true;
        }

        // volume rocker wake
        else if (preference == mVolumeRockerWake) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_ROCKER_WAKE,
                    value ? 1 : 0);
            return true;
        }

        // volume key adjust sound
        else if (preference == mVolumeKeyAdjustSound) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_KEY_ADJUST_SOUND,
                    value ? 1 : 0);
            return true;
        }

        // volume key cursor control
        else if (preference == mVolumeKeyCursorControl) {
            String volumeKeyCursorControl = (String) objValue;
            int volumeKeyCursorControlValue = Integer.parseInt(volumeKeyCursorControl);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.VOLUME_KEY_CURSOR_CONTROL, volumeKeyCursorControlValue);
            int volumeKeyCursorControlIndex = mVolumeKeyCursorControl
                    .findIndexOfValue(volumeKeyCursorControl);
            mVolumeKeyCursorControl
                    .setSummary(mVolumeKeyCursorControl.getEntries()[volumeKeyCursorControlIndex]);
            return true;
        }
        return false;
    }
}
