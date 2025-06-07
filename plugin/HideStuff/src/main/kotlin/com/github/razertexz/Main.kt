package com.github.razertexz

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*

import com.discord.utilities.color.ColorCompat
import com.discord.widgets.guilds.list.WidgetGuildListAdapter
import com.discord.widgets.guilds.contextmenu.WidgetGuildContextMenu
import com.discord.widgets.guilds.contextmenu.GuildContextMenuViewModel

import com.lytefast.flexinput.R

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    override fun start(context: Context) {
        lateinit var adapter: WidgetGuildListAdapter

        val items = WidgetGuildListAdapter::class.java.getDeclaredField("items").apply { isAccessible = true }

        val viewId = View.generateViewId()
        val icon = context.getDrawable(R.e.ic_visibility_grey_24dp)!!.mutate()

        patcher.patch(WidgetGuildListAdapter::class.java.declaredConstructors[0], Hook { adapter = this })

        patcher.after<WidgetGuildContextMenu>("configureUI", GuildContextMenuViewModel.ViewState::class.java) {
            val layout = this.requireView().getChildAt(0) as ViewGroup
            if (layout.findViewById<View>(viewId) == null) {
                val textView = TextView(layout.context, null, 0, R.i.ContextMenuTextOption).apply {
                    id = viewId
                    text = "Hide server"

                    icon.setTint(ColorCompat.getThemedColor(this, R.b.colorInteractiveNormal))
                    setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null)

                    setOnClickListener {
                        val guildId = (it.args[0] as GuildContextMenuViewModel.ViewState).guildId
                        val oldItems = items.get(adapter)
                        val newItems = oldItems.filter { it.guildId != guildId }
                        adapter.setItems(newItems)

                        layout.visibility = View.GONE
                    }
                }

                layout.addView(textView, layout.getChildCount())
            }
        }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
