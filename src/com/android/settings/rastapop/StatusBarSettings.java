package com.android.settings.rastapop;

import android.content.ContentResolver;
<<<<<<< HEAD:src/com/android/settings/rastapop/StatusBarSettings.java
import android.database.ContentObserver;
import android.net.Uri;
=======
import android.content.res.Configuration;
import android.content.res.Resources;
>>>>>>> eb755f5... Status Bar Clock: rewrite for lollipop and add left clock (2/2):src/com/android/settings/terminus/StatusBarSettings.java
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.format.DateFormat;
import android.view.View;

import com.android.settings.rastapop.qs.QSTiles;
import java.util.Date;

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
    // customizable tiles
    private Preference mQSTiles;
    public static final String STATUS_BAR_QUICK_QS_PULLDOWN = "status_bar_quick_qs_pulldown";

    // Cendter clock
    private static final String STATUS_BAR_CLOCK_STYLE = "status_bar_clock";
    private static final String STATUS_BAR_AM_PM = "status_bar_am_pm";

    // Date
    private static final String STATUS_BAR_DATE_STYLE = "status_bar_date";
    private static final String STATUS_BAR_DATE_FORMAT = "status_bar_date_format";

    // Statusbar battery percent
    private ListPreference mStatusBarBattery;
    private ListPreference mStatusBarBatteryShowPercent;
    // Quick Pulldown
    private SwitchPreference mStatusBarQuickQsPulldown;
    // Center clock
    private ListPreference mStatusBarClock;
    private ListPreference mStatusBarAmPm;
    // Date
    private ListPreference mStatusBarDate;
    private ListPreference mStatusBarDateFormat;

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
	// Quick Settings Tile Customization
	mQSTiles = findPreference("qs_order");
}
        // Center clock
        mStatusBarClock = (ListPreference) findPreference(STATUS_BAR_CLOCK_STYLE);
        mStatusBarAmPm = (ListPreference) findPreference(STATUS_BAR_AM_PM);
        mStatusBarDate = (ListPreference) findPreference(STATUS_BAR_DATE_STYLE);
        mStatusBarDateFormat = (ListPreference) findPreference(STATUS_BAR_DATE_FORMAT);

        int clockStyle = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_CLOCK, 1);
        mStatusBarClock.setValue(String.valueOf(clockStyle));
        mStatusBarClock.setSummary(mStatusBarClock.getEntry());
        mStatusBarClock.setOnPreferenceChangeListener(this);

        if (DateFormat.is24HourFormat(getActivity())) {
            mStatusBarAmPm.setEnabled(false);
            mStatusBarAmPm.setSummary(R.string.status_bar_am_pm_info);
        } else {
            int statusBarAmPm = Settings.System.getInt(resolver,
                    Settings.System.STATUS_BAR_AM_PM, 2);
            mStatusBarAmPm.setValue(String.valueOf(statusBarAmPm));
            mStatusBarAmPm.setSummary(mStatusBarAmPm.getEntry());
            mStatusBarAmPm.setOnPreferenceChangeListener(this);
        }
        enableStatusBarClockDependents();

        // Date
        int dateStyle = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_DATE, 0);
        mStatusBarDate.setValue(String.valueOf(dateStyle));
        mStatusBarDate.setSummary(mStatusBarDate.getEntry());
        mStatusBarDate.setOnPreferenceChangeListener(this);

        mStatusBarDateFormat.setOnPreferenceChangeListener(this);
        mStatusBarDateFormat.setSummary(mStatusBarDateFormat.getEntry());
        if (mStatusBarDateFormat.getValue() == null) {
            mStatusBarDateFormat.setValue("EEE");
        }
        parseClockDateFormats();
        enableStatusBarClockDependents(); 
        enableStatusBarDateDependents();   
    }

    @Override
    public void onResume() {
        super.onResume();
        // Adjust clock position for RTL if necessary
        Configuration config = getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                mStatusBarClock.setEntries(getActivity().getResources().getStringArray(
                        R.array.status_bar_clock_style_entries_rtl));
                mStatusBarClock.setSummary(mStatusBarClock.getEntry());
        }
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
    ContentResolver resolver = getActivity().getContentResolver();

        // status bar brightness control
        if (preference == mStatusBarBrightnessControl) {
            boolean value = (Boolean) objValue;
            Settings.System.putInt(getContentResolver(), STATUS_BAR_BRIGHTNESS_CONTROL,
                    value ? 1 : 0);
            return true;
	// battery
        } else if (preference == mStatusBarBattery) {
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
        } else if (preference == mStatusBarClock) {
            int clockStyle = Integer.parseInt((String) objValue);
            int index = mStatusBarClock.findIndexOfValue((String) objValue);
            Settings.System.putInt(resolver,
                    STATUS_BAR_CLOCK_STYLE, clockStyle);
            mStatusBarClock.setSummary(mStatusBarClock.getEntries()[index]);
            enableStatusBarClockDependents();
            return true;
        } else if (preference == mStatusBarAmPm) {
            int statusBarAmPm = Integer.valueOf((String) objValue);
            int index = mStatusBarAmPm.findIndexOfValue((String) objValue);
            Settings.System.putInt(resolver,
                    STATUS_BAR_AM_PM, statusBarAmPm);
            mStatusBarAmPm.setSummary(mStatusBarAmPm.getEntries()[index]);
            return true;
        } else if (preference == mStatusBarDate) {
            int statusBarDate = Integer.valueOf((String) objValue);
            int index = mStatusBarDate.findIndexOfValue((String) objValue);
            Settings.System.putInt(resolver,
                    STATUS_BAR_DATE_STYLE, statusBarDate);
            mStatusBarDate.setSummary(mStatusBarDate.getEntries()[index]);
	    enableStatusBarDateDependents();
            return true;
        } else if (preference ==  mStatusBarDateFormat) {
            int index = mStatusBarDateFormat.findIndexOfValue((String) objValue);
            if ((String) newValue != null) {
                Settings.System.putString(resolver,
                   Settings.System.STATUS_BAR_DATE_FORMAT, (String) objValue);
            mStatusBarDateFormat.setSummary(mStatusBarDateFormat.getEntries()[index]);
            }
            return true;
        }
        return false;
    }

    private void enableStatusBarBatteryDependents(String value) {
        boolean enabled = !(value.equals(STATUS_BAR_BATTERY_STYLE_TEXT)
                || value.equals(STATUS_BAR_BATTERY_STYLE_HIDDEN));
        mStatusBarBatteryShowPercent.setEnabled(enabled);
    }

    @Override
    public void onResume() {
	super.onResume();

        int qsTileCount = QSTiles.determineTileCount(getActivity());
        mQSTiles.setSummary(getResources().getQuantityString(R.plurals.qs_tiles_summary,
                    qsTileCount, qsTileCount));
	}
    private void enableStatusBarClockDependents() {
        int clockStyle = Settings.System.getInt(getActivity()
                .getContentResolver(), Settings.System.STATUS_BAR_CLOCK, 1);
        if (clockStyle == 0) {
            mStatusBarDate.setEnabled(false);
            mStatusBarDateFormat.setEnabled(false);
	    mStatusBarAmPm.setEnabled(false);
        } else {
            mStatusBarDate.setEnabled(true);
            mStatusBarDateFormat.setEnabled(true);
	    mStatusBarAmPm.setEnabled(true);
        }
    }

   private void enableStatusBarDateDependents() {
        int dateStyle = Settings.System.getInt(getActivity()
                .getContentResolver(), Settings.System.STATUS_BAR_DATE, 1);
        if (dateStyle == 0) {
            mStatusBarDateFormat.setEnabled(false);
        } else {
            mStatusBarDateFormat.setEnabled(true);

    private void parseClockDateFormats() {
        // Parse and repopulate mClockDateFormats's entries based on current date.
        String[] dateEntries = getResources().getStringArray(R.array.status_bar_date_format_entries_values);
        CharSequence parsedDateEntries[];
        parsedDateEntries = new String[dateEntries.length];
        Date now = new Date();
        int lastEntry = dateEntries.length - 1;
        for (int i = 0; i < dateEntries.length; i++) {
            if (i == lastEntry) {
                parsedDateEntries[i] = dateEntries[i];
            } else {
                String newDate;
                CharSequence dateString = DateFormat.format(dateEntries[i], now);
                newDate = dateString.toString().toLowerCase();
                parsedDateEntries[i] = newDate;
            }
        }
        mStatusBarDateFormat.setEntries(parsedDateEntries);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
