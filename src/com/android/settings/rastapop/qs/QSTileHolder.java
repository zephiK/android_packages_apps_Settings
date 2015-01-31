/*
 * Copyright (C) 2015 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.rastapop.qs;

import android.content.Context;

import com.android.internal.util.cm.QSConstants;
import com.android.internal.util.cm.QSUtils;
import com.android.settings.R;

/**
 * This class holds the icon, the name - or the string the user sees,
 * and the value which will be stored
 */
public class QSTileHolder {
    public static final String TILE_ADD_DELETE = "";

    public final String resourceName;
    public final String value;
    public final String name;

    public QSTileHolder(String resourceName, String value, String name) {
        this.resourceName = resourceName;
        this.value = value;
        this.name = name;
    }

    public static QSTileHolder from(Context context, String tileType) {
        String resourceName = null;
        int stringId = -1;

        if (!TILE_ADD_DELETE.equals(tileType) &&
                !QSUtils.getAvailableTiles(context).contains(tileType)) {
            return null;
        }

        switch (tileType) {
            case TILE_ADD_DELETE:
                break;
            case QSConstants.TILE_WIFI:
                resourceName = "ic_qs_wifi_full_4";
                stringId = R.string.qs_tile_wifi;
                break;
            case QSConstants.TILE_BLUETOOTH:
                resourceName = "ic_qs_bluetooth_on";
                stringId = R.string.qs_tile_bluetooth;
                break;
            case QSConstants.TILE_CELLULAR:
                resourceName = "ic_qs_signal_full_4";
                stringId = R.string.qs_tile_cellular_data;
                break;
            case QSConstants.TILE_AIRPLANE:
                resourceName = "ic_qs_airplane_on";
                stringId = R.string.qs_tile_airplane_mode;
                break;
            case QSConstants.TILE_ROTATION:
                resourceName = "ic_qs_rotation_locked";
                stringId = R.string.qs_tile_display_rotation;
                break;
            case QSConstants.TILE_FLASHLIGHT:
                resourceName = "ic_qs_flashlight_on";
                stringId = R.string.qs_tile_flashlight;
                break;
            case QSConstants.TILE_LOCATION:
                resourceName = "ic_qs_location_on";
                stringId = R.string.qs_tile_location;
                break;
            case QSConstants.TILE_CAST:
                resourceName = "ic_qs_cast_on";
                stringId = R.string.qs_tile_cast_screen;
                break;
            case QSConstants.TILE_HOTSPOT:
                resourceName = "ic_qs_hotspot_on";
                stringId = R.string.qs_tile_hotspot;
                break;
            case QSConstants.TILE_NOTIFICATIONS:
                resourceName = "ic_qs_zen_on";
                stringId = R.string.qs_tile_notifications;
                break;
            default:
                return null;

        if (tileType.equals("wifi")) {
            resId = R.drawable.ic_settings_wireless;
            stringId = R.string.wifi_quick_toggle_title;
        } else if (tileType.equals("bt")) {
            resId = R.drawable.ic_settings_bluetooth2;
            stringId = R.string.bluetooth_settings_title;
        } else if (tileType.equals("inversion")) {
            resId = R.drawable.ic_settings_accessibility;
            stringId = R.string.accessibility_display_inversion_preference_title;
        } else if (tileType.equals("cell")) {
            resId = R.drawable.ic_qs_signal;
            stringId = R.string.cellular_data_title;
        } else if (tileType.equals("airplane")) {
            resId = R.drawable.ic_qs_airplane;
            stringId = R.string.airplane_mode;
        } else if (tileType.equals("rotation")) {
            resId = R.drawable.ic_qs_rotation;
            stringId = R.string.display_rotation_title;
        } else if (tileType.equals("flashlight")) {
            resId = R.drawable.ic_qs_flashlight;
            stringId = R.string.power_flashlight;
        } else if (tileType.equals("location")) {
            resId = R.drawable.ic_settings_location;
            stringId = R.string.location_title;
        } else if (tileType.equals("cast")) {
            resId = R.drawable.ic_qs_cast;
            stringId = R.string.cast_screen;
        } else if (tileType.equals("hotspot")) {
            resId = R.drawable.ic_qs_hotspot;
            stringId = R.string.hotspot;
        } else if (tileType.equals("notifications")) {
            resId = R.drawable.ic_qs_ringer_audible;
            stringId = R.string.notifications;
        } else if (tileType.equals("data")) {
            resId = R.drawable.ic_qs_data_on;
            stringId = R.string.mobile_data;
        }

        String name = stringId != -1 ? context.getString(stringId) : null;
        return new QSTileHolder(resourceName, tileType, name);
    }
}
