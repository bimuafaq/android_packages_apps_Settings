package com.android.settings.display;

import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public class ScrimColorPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String KEY_SCRIM_HEX = "system_qs_scrim_hex";

    public ScrimColorPreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_SCRIM_HEX;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        EditTextPreference pref = screen.findPreference(KEY_SCRIM_HEX);
        if (pref != null) {
            updateState(pref);
        }
    }

    @Override
    public void updateState(Preference preference) {
        String value = Settings.System.getString(mContext.getContentResolver(), KEY_SCRIM_HEX);
        if (value == null) {
            value = "#FF202024";
        }
        ((EditTextPreference) preference).setText(value);
        preference.setSummary(value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String hex = (String) newValue;

        if (isValidHex(hex)) {
            Settings.System.putString(mContext.getContentResolver(), KEY_SCRIM_HEX, hex);
            preference.setSummary(hex);
            return true;
        } else {
            Toast.makeText(mContext, R.string.qs_scrim_hex_error, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean isValidHex(String hex) {
        if (TextUtils.isEmpty(hex)) return false;
        if (!hex.startsWith("#")) return false;
        if (hex.length() != 7 && hex.length() != 9) return false;

        try {
            Color.parseColor(hex);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
