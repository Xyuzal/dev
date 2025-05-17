package com.github.razertexz

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.text.TextWatcher
import android.text.InputType
import android.text.Editable

import com.aliucord.Utils
import com.aliucord.utils.DimenUtils.defaultPadding
import com.aliucord.fragments.SettingsPage
import com.aliucord.views.TextInput
import com.aliucord.api.SettingsAPI

class PluginSettings(val settings: SettingsAPI) : SettingsPage() {
    private fun createTextInput(context: Context, hint: CharSequence, settingName: String, defaultVal: Float = 0.0f) {
        addView(TextInput(
            context,
            hint,
            settings.getFloat(settingName, defaultVal).toString(),
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    s.toString().toFloatOrNull()?.let {
                        settings.setFloat(settingName, it)
                        Utils.promptRestart()
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            }
        ).apply {
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                defaultPadding.let { setMargins(it, 8, it, 8) }
            }
        })
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        val context = requireContext()

        setActionBarTitle("AdjustDragBehavior")
        setPadding(0)

        createTextInput(context, "Move Threshold", "moveThreshold", 0.2f)
    }
}