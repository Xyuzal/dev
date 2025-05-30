package com.github.razertexz

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*
import com.aliucord.Utils
import com.aliucord.Constants.Fonts

import com.discord.widgets.settings.WidgetSettings
import com.discord.utilities.color.ColorCompat

import com.lytefast.flexinput.R

@AliucordPlugin(requiresRestart = true)
class Main : Plugin() {
    override fun start(context: Context) {
        val font = ResourcesCompat.getFont(context, Fonts.whitney_medium)
        val icon = context.getDrawable(R.e.ic_search_grey_24dp)!!.mutate()

        patcher.patch(WidgetSettings::class.java.getDeclaredMethod("onViewBound", View::class.java), Hook {
            val layout = Utils.nestedChildAt<ViewGroup>(it.args[0] as ViewGroup, 1, 0)
            val idx = layout.indexOfChild(layout.findViewById<View>(Utils.getResId("developer_options_divider", "id")))

            TextView(layout.context, null, 0, R.i.UiKit_Settings_Item_Icon).run {
                text = "Open Plugin Finder"
                setTypeface(font)

                icon.setTint(ColorCompat.getThemedColor(this.context, R.b.colorInteractiveNormal))
                setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)

                setOnClickListener { view -> Utils.openPageWithProxy(view.context, PluginFinder()) }

                layout.addView(this, idx)
            }
        })
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
