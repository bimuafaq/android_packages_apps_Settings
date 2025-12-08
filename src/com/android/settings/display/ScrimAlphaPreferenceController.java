package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.core.AbstractPreferenceController;

public class ScrimAlphaPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String KEY_SCRIM_ALPHA = "system_qs_scrim_alpha";
    private static final int DEFAULT_ALPHA = 216;

    public ScrimAlphaPreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_SCRIM_ALPHA;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        SeekBarPreference pref = screen.findPreference(KEY_SCRIM_ALPHA);
        if (pref != null) {
            pref.setMax(255);
            pref.setMin(0);
            updateState(pref);
        }
    }

    @Override
    public void updateState(Preference preference) {
        int value = Settings.System.getInt(mContext.getContentResolver(), 
                KEY_SCRIM_ALPHA, DEFAULT_ALPHA);
        ((SeekBarPreference) preference).setProgress(value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        int val = (Integer) newValue;
        Settings.System.putInt(mContext.getContentResolver(), KEY_SCRIM_ALPHA, val);
        return true;
    }
}
