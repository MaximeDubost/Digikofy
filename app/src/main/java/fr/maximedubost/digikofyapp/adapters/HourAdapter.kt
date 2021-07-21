package fr.maximedubost.digikofyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import fr.maximedubost.digikofyapp.R

class HourAdapter (
    private val onClickRemoveHour: (position: Int) -> Unit,
    private val hourList: MutableList<String>,
) : RecyclerView.Adapter<HourAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvItemIndex: TextView = itemView.findViewById(R.id.tv_item_index)
        var tvItemHour: TextView = itemView.findViewById(R.id.tv_item_hour)
        var ivItemRemove: ImageView = itemView.findViewById(R.id.tv_item_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_hour, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("---")
        println("position: $position")
        println("hourList.size: ${hourList.size}")
        when (position) {
            0 -> holder.itemView.setPadding(32, 0, 0, 0)
            hourList.size - 1 -> holder.itemView.setPadding(0, 0, 32, 0)
            else -> holder.itemView.setPadding(0, 0, 0, 0)
        }
        holder.tvItemIndex.text = (position + 1).toString()
        holder.tvItemHour.text = hourList.elementAt(position)
        holder.ivItemRemove.setOnClickListener { onClickRemoveHour(position) }
    }

    override fun getItemCount(): Int = hourList.size

}