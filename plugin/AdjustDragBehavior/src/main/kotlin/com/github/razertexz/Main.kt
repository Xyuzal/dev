package com.github.razertexz

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*

import com.discord.widgets.guilds.list.GuildsDragAndDropCallback

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    private val moveThreshold = settings.getFloat("moveThreshold", 0.2f)

    init {
        settingsTab = SettingsTab(PluginSettings::class.java).withArgs(settings)
    }

    override fun start(context: Context) {
        patcher.instead<GuildsDragAndDropCallback>("getMoveThreshold", RecyclerView.ViewHolder::class.java) { moveThreshold }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
