package com.terabyte.insight.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.insight.R
import com.terabyte.insight.model.RecyclerMainItem

class RecyclerMainAdapter(
    private val items: List<RecyclerMainItem>,
    private val inflater: LayoutInflater,
    private val context: Context
) : RecyclerView.Adapter<RecyclerMainAdapter.RecyclerMainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMainHolder {
        val view = inflater.inflate(R.layout.recycler_item_main, parent, false)
        return RecyclerMainHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerMainHolder, position: Int) {
        val item = items[position]
        val drawable = ResourcesCompat.getDrawable(context.resources, items[position].drawableId, context.theme)

        holder.textTitle.text = item.title
        holder.textDescription.text = item.description
        holder.image.setImageDrawable(drawable)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, item.activityToStart)
            context.startActivity(intent)
        }
    }

    class RecyclerMainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle = view.findViewById<TextView>(R.id.textRecyclerItemMainTitle)
        val textDescription = view.findViewById<TextView>(R.id.textRecyclerItemMainDesc)
        val image = view.findViewById<ImageView>(R.id.imageRecyclerItemMain)
    }
}