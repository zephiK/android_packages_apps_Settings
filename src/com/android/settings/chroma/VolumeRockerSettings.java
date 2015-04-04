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

    private static final String KEY_VOLUME_WAKE = "pref_volume_wake";
    private static final String KEY_VOLBTN_MUSIC_CTRL = "volbtn_music_controls";
    private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
    private static final String VOLUME_KEY_CURSOR_CONTROL = "volume_key_cursor_control";

    private SwitchPreference mVolumeWake;
    private SwitchPreference mVolumeMusicControl;
    private SwitchPreference mVolumeKeyAdjustSound;
    private ListPreference mVolumeKeyCursorControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.chroma_settings_volume);

        // volume wake options
        mVolumeWake = (SwitchPreference) findPreference(KEY_VOLUME_WAKE);
        mVolumeWake.setChecked(Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.VOLUME_WAKE_SCREEN, 0) == 1);
        mVolumeWake.setOnPreferenceChangeListener(this);

        // volume rocker music control
        mVolumeMusicControl = (SwitchPreference) findPreference(KEY_VOLBTN_MUSIC_CTRL);
        mVolumeMusicControl.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.VOLUME_MUSIC_CONTROLS, 1) != 0);
        mVolumeMusicControl.setOnPreferenceChangeListener(this);

        // volume key adjust sound
        mVolumeKeyAdjustSound = (SwitchPreference) findPreference(VOLUME_KEY_ADJUST_SOUND);
        mVolumeKeyAdjustSound.setOnPreferenceChangeListener(this);
        mVolumeKeyAdjustSound.setChecked(Settings.System.getInt(getContentResolver(),
                VOLUME_KEY_ADJUST_SOUND, 1) != 0);

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
            if (preference == mVolumeKeyAdjustSound) {
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
            } else if (preference == mVolumeWake) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(),
                    Settings.System.VOLUME_WAKE_SCREEN,
                    (Boolean) value ? 1 : 0);
            return true;
        } else if (preference == mVolumeMusicControl) {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.VOLUME_MUSIC_CONTROLS,
                    (Boolean) objValue ? 1 : 0);
            return true;
        }
        return false;
    }
}
