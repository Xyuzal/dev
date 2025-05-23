package com.github.razertexz

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

import com.aliucord.fragments.SettingsPage
import com.aliucord.Utils

class PluginFinder : SettingsPage() {
    private class PluginItem(val name: String)

    private class CustomAdapter(private val context: Context, private val items: List<PluginItem>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
        private class CustomViewHolder(val card: PluginFinderCard) : RecyclerView.ViewHolder(card)

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomViewHolder {
            return CustomViewHolder(PluginFinderCard(context))
        }

        override fun onBindViewHolder(viewHolder: CustomViewHolder, position: Int) {
            val plugin = items[position]
        }

        override fun getItemCount(): Int = items.size
    }

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        Utils.showToast("YOLO!")

        setActionBarTitle("Plugin Finder")
        setActionBarSubtitle("By RazerTexz")
        removeScrollView()

        val context = requireContext()
        val recyclerView = RecyclerView(context).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomAdapter(context, listOf(PluginItem("1"), PluginItem("2"), PluginItem("3"), PluginItem("4"), PluginItem("5"), PluginItem("6"), PluginItem("7"), PluginItem("8"), PluginItem("9"), PluginItem("10"), PluginItem("11"), PluginItem("12"), PluginItem("13"), PluginItem("14"), PluginItem("15"), PluginItem("16"), PluginItem("17")))
            setHasFixedSize(true)
        }

        addView(recyclerView)
    }
}
