package com.android.settings.chroma;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class VolumeRockerSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String VOLUME_ROCKER_WAKE = "volume_rocker_wake";
    public static final String VOLUME_MUSIC_CONTROLS = "volume_music_controls";
    private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
    private static final String KEY_VOLUME_MUSIC_CONTROLS = "volbtn_music_controls";
    private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";

    private SwitchPreference mVolumeRockerWake;
    private SwitchPreference mVolumeMusicControl;
    private SwitchPreference mVolumeKeyAdjustSound;
    private SwitchPreference mVolumeWakeScreen;
    private SwitchPreference mVolumeMusicControls;
    private ListPreference mVolumeKeyCursorControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.chroma_settings_volume);

        // volume rocker wake
        mVolumeRockerWake = (SwitchPreference) findPreference(VOLUME_ROCKER_WAKE);
        mVolumeRockerWake.setOnPreferenceChangeListener(this);
        int volumeRockerWake = Settings.System.getInt(getContentResolver(),
                VOLUME_ROCKER_WAKE, 0);
        mVolumeRockerWake.setChecked(volumeRockerWake != 0);

        // volume rocker music control
        mVolumeMusicControl = (SwitchPreference) findPreference(VOLUME_MUSIC_CONTROLS);
        mVolumeMusicControl.setOnPreferenceChangeListener(this);
        int volumeMusicControl = Settings.System.getInt(getContentResolver(),
                VOLUME_MUSIC_CONTROLS, 1);
        mVolumeMusicControl.setChecked(volumeMusicControl != 0);

        // volume key adjust sound
        mVolumeKeyAdjustSound = (SwitchPreference) findPreference(VOLUME_KEY_ADJUST_SOUND);
        mVolumeKeyAdjustSound.setOnPreferenceChangeListener(this);
        mVolumeKeyAdjustSound.setChecked(Settings.System.getInt(getContentResolver(),
                VOLUME_KEY_ADJUST_SOUND, 1) != 0);

        // wake screen dependency
        mVolumeWakeScreen = (SwitchPreference) findPreference(Settings.System.VOLUME_ROCKER_WAKE);
        mVolumeMusicControls = (SwitchPreference) findPreference(VOLUME_MUSIC_CONTROLS);

        if (mVolumeWakeScreen != null) {
            if (mVolumeMusicControls != null) {
                mVolumeMusicControls.setDependency(Settings.System.VOLUME_ROCKER_WAKE);
                mVolumeWakeScreen.setDisableDependentsState(true);
            }
        }

        // volume cursor control
	    mVolumeKeyCursorControl = (ListPreference) findPreference(VOLUME_KEY_CURSOR_CONTROL);
            if (mVolumeKeyCursorControl != null) {
                mVolumeKeyCursorControl.setOnPreferenceChangeListener(this);
                mVolumeKeyCursorControl.setValue(Integer.toString(Settings.System.getInt(getActivity()
                        .getContentResolver(), Settings.System.VOLUME_KEY_CURSOR_CONTROL, 0)));
                mVolumeKeyCursorControl.setSummary(mVolumeKeyCursorControl.getEntry());
            }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mVolumeRockerWake) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_ROCKER_WAKE,
                    value ? 1 : 0);
            return true;
        } else if (preference == mVolumeMusicControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_MUSIC_CONTROLS,
                    value ? 1 : 0);
            return true;
        } else if (preference == mVolumeKeyAdjustSound) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_KEY_ADJUST_SOUND,
                    value ? 1 : 0);
            return true;
        } else if (preference == mVolumeKeyCursorControl) {
            String volumeKeyCursorControl = (String) objValue;
            int volumeKeyCursorControlValue = Integer.parseInt(volumeKeyCursorControl);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.VOLUME_KEY_CURSOR_CONTROL, volumeKeyCursorControlValue);
            int volumeKeyCursorControlIndex = mVolumeKeyCursorControl.findIndexOfValue(volumeKeyCursorControl);
            mVolumeKeyCursorControl.setSummary(mVolumeKeyCursorControl.getEntries()[volumeKeyCursorControlIndex]);
            return true;
            }
        return false;
    }
}
