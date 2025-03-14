package com.terabyte.insight.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.insight.R
import com.terabyte.insight.model.DeviceDetail

class RecyclerDeviceDetailsAdapter(
    private val items: List<DeviceDetail>,
    private val inflater: LayoutInflater,
) : RecyclerView.Adapter<RecyclerDeviceDetailsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = inflater.inflate(R.layout.recycler_item_system_details, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]

        holder.textName.text = item.name
        holder.textDescription.text = item.description
        holder.textValue.text = item.value
        if (position+1 == items.size) {
            holder.divider.visibility = View.GONE
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val textName = view.findViewById<TextView>(R.id.textRecyclerItemSystemDetailsName)
        val textValue = view.findViewById<TextView>(R.id.textRecyclerItemSystemDetailsValue)
        val textDescription = view.findViewById<TextView>(R.id.textRecyclerItemSystemDetailsDesc)
        val divider = view.findViewById<View>(R.id.dividerLineSystemDetails)
    }
}