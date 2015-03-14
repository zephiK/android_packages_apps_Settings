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
    public static final String VOLUME_ROCKER_MUSIC_CONTROLS = "volume_music_controls";
    private static final String VOLUME_KEY_ADJUST_SOUND = "volume_key_adjust_sound";
    private static final String KEY_VOLUME_MUSIC_CONTROLS = "volbtn_music_controls";

    private SwitchPreference mVolumeRockerWake;
    private SwitchPreference mVolumeRockerMusicControl;
    private SwitchPreference mVolumeKeyAdjustSound;
    private SwitchPreference mVolumeWakeScreen;
    private SwitchPreference mVolumeMusicControls;

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
        mVolumeRockerMusicControl = (SwitchPreference) findPreference(VOLUME_ROCKER_MUSIC_CONTROLS);
        mVolumeRockerMusicControl.setOnPreferenceChangeListener(this);
        int volumeRockerMusicControl = Settings.System.getInt(getContentResolver(),
                VOLUME_ROCKER_MUSIC_CONTROLS, 1);
        mVolumeRockerMusicControl.setChecked(volumeRockerMusicControl != 0);

        // volume key adjust sound
        mVolumeKeyAdjustSound = (SwitchPreference) findPreference(VOLUME_KEY_ADJUST_SOUND);
        mVolumeKeyAdjustSound.setOnPreferenceChangeListener(this);
        mVolumeKeyAdjustSound.setChecked(Settings.System.getInt(getContentResolver(),
                VOLUME_KEY_ADJUST_SOUND, 1) != 0);

        // wake screen dependency
        mVolumeWakeScreen = (SwitchPreference) findPreference(Settings.System.VOLUME_ROCKER_WAKE);
        mVolumeMusicControls = (SwitchPreference) findPreference(VOLUME_ROCKER_MUSIC_CONTROLS);

        if (mVolumeWakeScreen != null) {
            if (mVolumeMusicControls != null) {
                mVolumeMusicControls.setDependency(Settings.System.VOLUME_ROCKER_WAKE);
                mVolumeWakeScreen.setDisableDependentsState(true);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        // volume rocker wake
        if (preference == mVolumeRockerWake) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_ROCKER_WAKE,
                    value ? 1 : 0);
            return true;
        }

        // volume rocker music control
        else if (preference == mVolumeRockerMusicControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), VOLUME_ROCKER_MUSIC_CONTROLS,
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
        return false;
    }
}
