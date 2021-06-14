package fr.maximedubost.digikofyapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.dialogs.MachineDialog
import fr.maximedubost.digikofyapp.models.MachineModel

class MachineAdapter(
    private val machineList: List<MachineModel>,
    val context: MainActivity
) : RecyclerView.Adapter<MachineAdapter.ViewHolder>() {

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var tvMachineName: TextView = itemView.findViewById(R.id.tv_machine_name)
        var tvMachineType: TextView = itemView.findViewById(R.id.tv_machine_type)
        var tvMachineAvailability: TextView = itemView.findViewById(R.id.tv_machine_availability)
        var fabUseMachine: FloatingActionButton = itemView.findViewById(R.id.fab_use_machine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_machine, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val machine = machineList[position]
        holder.tvMachineName.text = machine.name
        holder.tvMachineType.text = machine.typeToString()
        holder.tvMachineAvailability.text = machine.stateToString()

        if (holder.tvMachineAvailability.text == MachineModel.MACHINE_STATE_UNAVAILABLE)
            holder.tvMachineAvailability.setTextColor(Color.LTGRAY)

        holder.itemView.setOnClickListener { MachineDialog(this, machine).show() }
        holder.fabUseMachine.setOnClickListener {
            Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = machineList.size

}