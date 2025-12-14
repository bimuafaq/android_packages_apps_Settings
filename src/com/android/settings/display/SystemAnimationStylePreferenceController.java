package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

public class SystemAnimationStylePreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {

    private static final String SYSTEM_ANIMATION_STYLE = "system_animation_style";

    public SystemAnimationStylePreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        ListPreference preference = screen.findPreference(getPreferenceKey());
        if (preference != null) {
            int value = Settings.System.getInt(mContext.getContentResolver(),
                    SYSTEM_ANIMATION_STYLE, 0);
            preference.setValue(String.valueOf(value));
            preference.setSummary(preference.getEntry());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference instanceof ListPreference) {
            try {
                int value = Integer.parseInt((String) newValue);

                Settings.System.putInt(mContext.getContentResolver(),
                        SYSTEM_ANIMATION_STYLE, value);

                int index = ((ListPreference) preference).findIndexOfValue((String) newValue);
                preference.setSummary(((ListPreference) preference).getEntries()[index]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof ListPreference) {
            int value = Settings.System.getInt(mContext.getContentResolver(),
                    SYSTEM_ANIMATION_STYLE, 0);
            ((ListPreference) preference).setValue(String.valueOf(value));
            ((ListPreference) preference).setSummary(((ListPreference) preference).getEntry());
        }
    }
}
