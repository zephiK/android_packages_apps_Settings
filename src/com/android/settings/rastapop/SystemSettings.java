
package com.android.settings.rastapop;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SystemSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // status bar native battery percentage
    private static final String STATUS_BAR_NATIVE_BATTERY_PERCENTAGE = "status_bar_native_battery_percentage";

    // volume key adjust sound
    private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
    // volume key cursor control
    private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";

    // status bar native battery percentage
    private SwitchPreference mStatusBarNativeBatteryPercentage;
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
