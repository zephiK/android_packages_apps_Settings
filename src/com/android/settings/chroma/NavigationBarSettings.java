package com.android.settings.chroma;

import android.os.Bundle;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class NavigationBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {
    private static final String KILL_APP_LONGPRESS_BACK = "kill_app_longpress_back";
    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";

    private SwitchPreference mKillAppLongPressBack;
    private ListPreference mNavigationBarHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.chroma_settings_navigation);

        // kill-app long press back
        mKillAppLongPressBack = (SwitchPreference) findPreference(KILL_APP_LONGPRESS_BACK);
        mKillAppLongPressBack.setOnPreferenceChangeListener(this);
        int killAppLongPressBack = Settings.Secure.getInt(getContentResolver(),
                KILL_APP_LONGPRESS_BACK, 0);
        mKillAppLongPressBack.setChecked(killAppLongPressBack != 0);

        // navigation bar height
        mNavigationBarHeight = (ListPreference) findPreference(NAVIGATION_BAR_HEIGHT);
        mNavigationBarHeight.setOnPreferenceChangeListener(this);
        int statusNavigationBarHeight = Settings.System.getInt(getContentResolver(),
                Settings.System.NAVIGATION_BAR_HEIGHT, 48);
        mNavigationBarHeight.setValue(String.valueOf(statusNavigationBarHeight));
        mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntry());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        // kill-app long press back
        if (preference == mKillAppLongPressBack) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putInt(getContentResolver(), KILL_APP_LONGPRESS_BACK,
                    value ? 1 : 0);
            return true;
        } else if (preference == mNavigationBarHeight) {
            // navigation bar height
            int statusNavigationBarHeight = Integer.valueOf((String) objValue);
            int index = mNavigationBarHeight.findIndexOfValue((String) objValue);
            Settings.System.putInt(getContentResolver(), NAVIGATION_BAR_HEIGHT,
                    statusNavigationBarHeight);
            mNavigationBarHeight.setSummary(mNavigationBarHeight.getEntries()[index]);
            return true;
        }
        return false;
    }
}
