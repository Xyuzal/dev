package com.github.razertexz

import android.content.Context
import android.widget.TextView
import android.text.method.LinkMovementMethod
import com.google.android.material.card.MaterialCardView

import com.aliucord.views.Divider
import com.aliucord.widgets.LinearLayout

import com.lytefast.flexinput.R

class PluginFinderCard(context: Context) : MaterialCardView(context) {
    val titleView: TextView
    val descriptionView: TextView
    val installButton: TextView

    init {
        titleView = TextView(context).apply {
            setMovementMethod(LinkMovementMethod.getInstance())
        }
        descriptionView = TextView(context, null, 0, R.i.UiKit_Settings_Item_Addition)
        installButton = TextView(context)

        val root = LinearLayout(context)
        root.addView(titleView)
        root.addView(Divider(context))
        root.addView(descriptionView)
        root.addView(installButton)

        addView(root)
    }
}
