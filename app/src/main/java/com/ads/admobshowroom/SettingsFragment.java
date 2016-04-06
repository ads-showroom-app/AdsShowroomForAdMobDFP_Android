package com.ads.admobshowroom;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private EditTextPreference customIdPref;
    private EditTextPreference customTargetingPref;
    private EditTextPreference customSizePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
        customIdPref = (EditTextPreference)findPreference("pref_custom_id");
        customTargetingPref = (EditTextPreference)findPreference("pref_custom_targeting");
        customSizePref = (EditTextPreference)findPreference("pref_custom_size");

        refreshPreferences();
    }

    protected void refreshPreferences()
    {
        if (isUsingDefaultID())
        {
            removePref("pref_category_id", customIdPref);
        }
        else
        {
            //addCustomIdPref();
            addPref("pref_category_id", "pref_custom_id", customIdPref);

            //SharedPreferences mySharedPreferences = getPreferenceManager().getSharedPreferences();
            //mySharedPreferences.getString("pref_custom_id", AD_UNIT_ID);

            // Set a new summary as a current custom ad unit id
            EditTextPreference pref = (EditTextPreference)findPreference("pref_custom_id");
            String currentText;

            if (pref.getText() == null) currentText = ""; else currentText = pref.getText();
            pref.setSummary(getResources().getString(R.string.pref_custom_id_summ)+"\n"+currentText);
        }

        if (isUsingDfp())
        {
            addPref("pref_category_dfp", "pref_custom_targeting", customTargetingPref);
            addPref("pref_category_dfp", "pref_custom_size", customSizePref);

            // Set a new summary as a current custom ad unit id
            EditTextPreference pref = (EditTextPreference)findPreference("pref_custom_targeting");
            String currentText;

            if (pref.getText() == null) currentText = ""; else currentText = pref.getText();
            pref.setSummary(getResources().getString(R.string.pref_custom_targeting_summ)+"\n"+currentText);
            //pref.setSummary(currentText);

            // for the custom size
            EditTextPreference prefCustomSize = (EditTextPreference)findPreference("pref_custom_size");
            String currentTextCustomSize;

            if (prefCustomSize.getText() == null) currentTextCustomSize = ""; else currentTextCustomSize = prefCustomSize.getText();
            prefCustomSize.setSummary(getResources().getString(R.string.pref_custom_size_summ)+"\n"+currentTextCustomSize);

        }
        else
        {
            removePref("pref_category_dfp", customTargetingPref);
            removePref("pref_category_dfp", customSizePref);
        }
    }

    public void removeCustomIdPref()
    {
        PreferenceCategory adUnitCategory = (PreferenceCategory)findPreference("pref_category_id");
        adUnitCategory.removePreference(customIdPref);
    }

    public void removePref(String categoryId, Preference pref)
    {
        PreferenceCategory category = (PreferenceCategory)findPreference(categoryId);
        category.removePreference(pref);
    }


    public void addCustomIdPref()
    {
        if(null == getPreferenceScreen().findPreference("pref_custom_id"))
        {
            PreferenceCategory adUnitCategory = (PreferenceCategory)findPreference("pref_category_id");
            adUnitCategory.addPreference(customIdPref);
            //getPreferenceScreen().addPreference(customIdPref);
        }
    }

    public void addPref(String categoryId, String prefId, Preference pref)
    {
        if(null == getPreferenceScreen().findPreference(prefId))
        {
            PreferenceCategory category = (PreferenceCategory)findPreference(categoryId);
            category.addPreference(pref);
        }
    }

    public boolean isUsingDefaultID()
    {
        SharedPreferences mySharedPreferences = getPreferenceManager().getSharedPreferences(); //PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        return mySharedPreferences.getBoolean("pref_is_default_id", true);
    }

    public boolean isUsingDfp()
    {
        SharedPreferences mySharedPreferences = getPreferenceManager().getSharedPreferences(); //PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        return mySharedPreferences.getBoolean("pref_is_dfp", true);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        refreshPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
