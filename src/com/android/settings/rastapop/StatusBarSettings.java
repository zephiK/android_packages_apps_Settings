package com.android.settings.rastapop;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.text.TextUtils;
import android.view.View;

import java.util.Locale;
import java.util.Date;
import com.android.settings.rastapop.qs.QSTiles;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.internal.util.rastapop.DeviceUtils;

public class StatusBarSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    // status bar brightness control
    private static final String STATUS_BAR_BRIGHTNESS_CONTROL = "status_bar_brightness_control";
    // status bar battery
    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";
    // quick pulldown
    private static final String PRE_QUICK_PULLDOWN = "quick_pulldown";
    // status bar brightness control
    private SwitchPreference mStatusBarBrightnessControl;
    // status bar battery percentage style
    private ListPreference mStatusBarBattery;
    private ListPreference mStatusBarBatteryShowPercent;
    // customizable tiles
    private Preference mQSTiles;

    // Center clock
    private ListPreference mStatusBarClock;
    private ListPreference mStatusBarAmPm;
    // Date
    private ListPreference mStatusBarDate;
    private ListPreference mStatusBarDateFormat;
    // quick pulldown
    private ListPreference mQuickPulldown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ras_status_bar_settings);

        ContentResolver resolver = getActivity().getContentResolver();

        // status bar brightness control
        mStatusBarBrightnessControl = (SwitchPreference) findPreference(STATUS_BAR_BRIGHTNESS_CONTROL);
        mStatusBarBrightnessControl.setOnPreferenceChangeListener(this);
        int statusBarBrightnessControl = Settings.System.getInt(getContentResolver(),
                STATUS_BAR_BRIGHTNESS_CONTROL, 0);
        mStatusBarBrightnessControl.setChecked(statusBarBrightnessControl != 0);

	// quick settings pulldown
	mQuickPulldown = (ListPreference) findPreference(PRE_QUICK_PULLDOWN);
        if (!DeviceUtils.isPhone(getActivity())) {
            prefs.removePreference(mQuickPulldown);
        } else {
            // Quick Pulldown
            mQuickPulldown.setOnPreferenceChangeListener(this);
            int statusQuickPulldown = Settings.System.getInt(getContentResolver(),
                    Settings.System.STATUS_BAR_QUICK_QS_PULLDOWN, 1);
            mQuickPulldown.setValue(String.valueOf(statusQuickPulldown));
            updateQuickPulldownSummary(statusQuickPulldown);
        }

	// Quick Settings Tile Customization
	mQSTiles = findPreference("qs_order");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
    ContentResolver resolver = getActivity().getContentResolver();

        // status bar brightness control
        if (preference == mStatusBarBrightnessControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_BRIGHTNESS_CONTROL,
                    value ? 1 : 0);
            return true;
	} else if (preference == mQuickPulldown) {
            int statusQuickPulldown = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUS_BAR_QUICK_QS_PULLDOWN,
                    statusQuickPulldown);
            updateQuickPulldownSummary(statusQuickPulldown);
            return true;
	}
	return false;
	}

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void updateQuickPulldownSummary(int value) {
        Resources res = getResources();

        if (value == 0) {
            // quick pulldown deactivated
            mQuickPulldown.setSummary(res.getString(R.string.quick_pulldown_off));
        } else {
            Locale l = Locale.getDefault();
            boolean isRtl = TextUtils.getLayoutDirectionFromLocale(l) == View.LAYOUT_DIRECTION_RTL;
            String direction = res.getString(value == 2
                    ? (isRtl ? R.string.quick_pulldown_right : R.string.quick_pulldown_left)
                    : (isRtl ? R.string.quick_pulldown_left : R.string.quick_pulldown_right));
            mQuickPulldown.setSummary(res.getString(R.string.summary_quick_pulldown, direction));
        }
    }
}
