package com.terabyte.insight.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.insight.R
import com.terabyte.insight.model.SystemDetail

class RecyclerSystemDetailsAdapter(
    private val items: List<SystemDetail>,
    private val inflater: LayoutInflater,
) : RecyclerView.Adapter<RecyclerSystemDetailsAdapter.RecyclerMainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMainHolder {
        val view = inflater.inflate(R.layout.recycler_item_system_details, parent, false)
        return RecyclerMainHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerMainHolder, position: Int) {
        val item = items[position]

        holder.textName.text = item.name
        holder.textDescription.text = item.description
        holder.textValue.text = item.value
        if (position+1 == items.size) {
            holder.divider.visibility = View.GONE
        }
    }

    class RecyclerMainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName = view.findViewById<TextView>(R.id.textRecyclerItemSystemDetailsName)
        val textValue = view.findViewById<TextView>(R.id.textRecyclerItemSystemDetailsValue)
        val textDescription = view.findViewById<TextView>(R.id.textRecyclerItemSystemDetailsDesc)
        val divider = view.findViewById<View>(R.id.dividerLineSystemDetails)
    }
}