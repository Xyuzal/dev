package com.github.razertexz

import android.content.Context
import android.os.CountDownTimer
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.*

import com.discord.widgets.chat.list.adapter.WidgetChatListAdapterItemMessage
import com.discord.models.member.GuildMember

import java.lang.System

@AliucordPlugin(requiresRestart = false)
class Main : Plugin() {
    /*fun android.view.View.logId(identifier: String = "ID:") {
        if (this.id != android.view.View.NO_ID) {
            logger.infoToast("$identifier: ${ this.resources.getResourceName(this.id) }")
        }
    }*/

    override fun start(context: Context) {
        val generatedViewId = View.generateViewId()

        patcher.after<WidgetChatListAdapterItemMessage>(
            "configureCommunicationDisabled",
            GuildMember::class.java,
            Long::class.javaObjectType
        ) {
            val guildMember = it.args[0] as GuildMember? ?: return@after

            val header = this.itemView.findViewById<ConstraintLayout>(0x7F0A0361) ?: return@after
            val existingTextView = header.findViewById<TextView>(generatedViewId) ?: TextView(header.context).apply {
                setTextColor(Color.RED)
                id = generatedViewId
                visibility = View.GONE
            }.also {
                (header.findViewById<TextView>(0x7F0A0363).layoutParams as ConstraintLayout.LayoutParams).apply {
                    topToBottom = generatedViewId
                    topToTop = ConstraintLayout.LayoutParams.UNSET
                }
                header.addView(it)
            }

            val existingTimer = existingTextView.tag as CountDownTimer?
            if (guildMember.isCommunicationDisabled()) {
                if (existingTimer == null) {
                    existingTextView.tag = object : CountDownTimer(guildMember.getCommunicationDisabledUntil().g() - System.currentTimeMillis(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val seconds = millisUntilFinished / 1000L
                            val minutes = seconds / 60L
                            val hours = minutes / 60L
                            existingTextView.text = "${ hours }h ${ minutes % 60L }m ${ seconds % 60L }s timeout remaining"
                        }

                        override fun onFinish() {
                            existingTextView.tag = null
                            existingTextView.visibility = View.GONE
                        }
                    }.start()
                    existingTextView.visibility = View.VISIBLE
                }
            } else {
                if (existingTimer != null) {
                    existingTimer.cancel()
                    existingTextView.tag = null
                    existingTextView.visibility = View.GONE
                }
            }
        }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
