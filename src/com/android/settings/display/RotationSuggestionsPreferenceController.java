package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;
import com.android.settings.core.TogglePreferenceController;

public class RotationSuggestionsPreferenceController extends TogglePreferenceController {

    private static final String KEY_SHOW_ROTATION_SUGGESTIONS = "show_rotation_suggestions";

    public RotationSuggestionsPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(),
                KEY_SHOW_ROTATION_SUGGESTIONS, 1) != 0;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        return Settings.Secure.putInt(mContext.getContentResolver(),
                KEY_SHOW_ROTATION_SUGGESTIONS, isChecked ? 1 : 0);
    }
}
