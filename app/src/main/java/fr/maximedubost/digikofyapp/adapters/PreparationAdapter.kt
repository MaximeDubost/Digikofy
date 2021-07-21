package fr.maximedubost.digikofyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class PreparationAdapter(
    private val onPreparationSelected: (preparationId: String) -> Unit?,
    private val preparationList: List<PreparationModel>,
    private val isSavedPreparationsPage: Boolean = false,
    private val isNextPreparationsPage: Boolean = false,
    private val isPastPreparationsPage: Boolean = false,
) : RecyclerView.Adapter<PreparationAdapter.ViewHolder>(){
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {

        var tvPreparationName: TextView = view.findViewById(R.id.tv_preparation_name)
        var tvPreparationDays: TextView = view.findViewById(R.id.tv_preparation_days)
        var tvPreparationHours: TextView = view.findViewById(R.id.tv_preparation_hours)
        var tvPreparationCoffee: TextView = view.findViewById(R.id.tv_preparation_coffee)
        var fabStartPreparation: FloatingActionButton = view.findViewById(R.id.fab_start_preparation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        if(isSavedPreparationsPage)
            ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_preparation, parent, false))
        else
            ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_preparation_small, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preparation = preparationList[position]

        holder.tvPreparationCoffee.text = preparation.coffee!!.name

        if (preparation.saved) {
            holder.tvPreparationName.visibility = View.VISIBLE

            holder.tvPreparationHours.visibility = View.VISIBLE
            holder.tvPreparationName.text = preparation.name
            when {
                isSavedPreparationsPage -> holder.tvPreparationDays.text = StringDateTimeFormatter.weekdays(preparation.weekdays!!)
                isNextPreparationsPage -> holder.tvPreparationDays.text = StringDateTimeFormatter.from(preparation.nextTime!!)
                isPastPreparationsPage -> holder.tvPreparationDays.text = StringDateTimeFormatter.from(preparation.lastTime!!)
            }

            holder.tvPreparationHours.text = StringDateTimeFormatter.hours(preparation.hours!!)
        } else {
            holder.tvPreparationName.visibility = View.GONE

            if(isNextPreparationsPage) holder.tvPreparationDays.text = StringDateTimeFormatter.from(preparation.nextTime!!)
            else if(isPastPreparationsPage) holder.tvPreparationDays.text = StringDateTimeFormatter.from(preparation.lastTime!!)

            holder.tvPreparationHours.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onPreparationSelected(preparation.id!!)
        }
        holder.fabStartPreparation.setOnClickListener {
            Toast.makeText(
                MainActivity.appContext,
                "Lancement de la préparation...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = preparationList.size
}