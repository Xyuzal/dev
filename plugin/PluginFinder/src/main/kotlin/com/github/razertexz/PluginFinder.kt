package com.github.razertexz

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

import com.aliucord.fragments.SettingsPage
import com.aliucord.entities.Plugin
import com.aliucord.Utils

class PluginFinder : SettingsPage() {
    private class CustomAdapter(private val context: Context, private val items: List<Plugin>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
        private class CustomViewHolder(val card: PluginFinderCard) : RecyclerView.ViewHolder(card)

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomViewHolder {
            return CustomViewHolder(PluginFinderCard(context))
        }

        override fun onBindViewHolder(viewHolder: CustomViewHolder, position: Int) {
            val item = items[position]
            val card = viewHolder.card
            card.installButton.text = "Install ${item.name}"
        }

        override fun getItemCount(): Int = items.size
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        val context = requireContext()

        setActionBarTitle("Plugin Finder")
        setActionBarSubtitle("By RazerTexz")
        removeScrollView()

        val recyclerView = RecyclerView(context).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomAdapter(context, emptyList())
            setHasFixedSize(true)
        }
        addView(recyclerView)
    }
}
