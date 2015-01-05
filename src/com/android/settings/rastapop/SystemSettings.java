
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

    // status bar show battery percent
    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";
    // status bar brightness control
    private static final String STATUS_BAR_BRIGHTNESS_CONTROL = "status_bar_brightness_control";

    // navigation bar height
    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";

    // volume rocker wake
    private static final String VOLUME_ROCKER_WAKE = "volume_rocker_wake";
    // volume key adjust sound
    private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
    // volume key cursor control
    private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";

    // advanced reboot
    private static final String ADVANCED_REBOOT = "advanced_reboot";
    // kill-app long press back
    private static final String KILL_APP_LONGPRESS_BACK = "kill_app_longpress_back";

    // status bar show battery percent
    private SwitchPreference mStatusBarShowBatteryPercent;
    // status bar brightness control
    private SwitchPreference mStatusBarBrightnessControl;
    // navigation bar height
    private ListPreference mNavigationBarHeight;
    // volume rocker wake
    private SwitchPreference mVolumeRockerWake;
    // volume key adjust sound
    private SwitchPreference mVolumeKeyAdjustSound;
    // volume key cursor control
    private ListPreference mVolumeKeyCursorControl;
    // advanced reboot
    private SwitchPreference mAdvancedReboot;
    // kill-app long press back
    private SwitchPreference mKillAppLongPressBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ras_system_settings);
        //addPreferencesFromResource(R.xml.system_settings);

        // status bar show battery percent
        mStatusBarShowBatteryPercent = (SwitchPreference) findPreference(STATUS_BAR_SHOW_BATTERY_PERCENT);
        mStatusBarShowBatteryPercent.setOnPreferenceChangeListener(this);
        int statusBarShowBatteryPercent = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_SHOW_BATTERY_PERCENT, 0);
        mStatusBarShowBatteryPercent.setChecked(statusBarShowBatteryPercent != 0);

        // status bar native brightness control
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

        // status bar show battery percent
        if (preference == mStatusBarShowBatteryPercent) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_SHOW_BATTERY_PERCENT,
                    value ? 1 : 0);
            return true;
        }

        // status bar brightness control
        else if (preference == mStatusBarBrightnessControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_BRIGHTNESS_CONTROL,
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
