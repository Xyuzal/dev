package com.github.razertexz

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*

import com.discord.widgets.user.usersheet.`WidgetUserSheet$onViewCreated$6`
import com.discord.databinding.UserProfileVoiceSettingsViewBinding
import com.discord.views.calls.VolumeSliderView

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    override fun start(context: Context) {
        lateinit var volumeLabel: TextView

        patcher.after<UserProfileVoiceSettingsViewBinding>(
            LinearLayout::class.java,
            SwitchMaterial::class.java,
            SwitchMaterial::class.java,
            TextView::class.java,
            VolumeSliderView::class.java
        ) {
            val volumeSliderViewBinding = (it.args[4] as VolumeSliderView).j

            val volumeSlider = volumeSliderViewBinding.d
            volumeSlider.max = 400

            volumeLabel = it.args[3] as TextView
            volumeLabel.post {
                volumeLabel.text = "Volume (${ volumeSlider.progress }%)"
            }
        }

        patcher.after<`WidgetUserSheet$onViewCreated$6`>(
            "invoke",
            Float::class.java,
            Boolean::class.java
        ) {
            if (it.args[1] as Boolean) {
                val newValue = (it.args[0] as Float).toInt()
                volumeLabel.text = "Volume ($newValue%)"
            }
        }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
