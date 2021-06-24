package fr.maximedubost.digikofyapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.adapters.MachineAdapter
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.ui.machine.MachineViewModel
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class MachineDialog(
    private val adapter: MachineAdapter,
    private val machine: MachineModel
) : Dialog(adapter.context) {

    init { this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }

    private lateinit var viewModel: MachineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = NewInstanceFactory().create(MachineViewModel::class.java)

        // viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_machine)
        
        findViewById<TextView>(R.id.tv_detail_machine_name).text = machine.name
        findViewById<TextView>(R.id.tv_detail_machine_type).text = machine.typeToString()
        findViewById<TextView>(R.id.tv_detail_machine_state).text = machine.stateToString()
        findViewById<TextView>(R.id.tv_detail_machine_creation_date).text = StringDateTimeFormatter.from(machine.creationDate!!)
        findViewById<TextView>(R.id.tv_detail_machine_last_update).text = StringDateTimeFormatter.from(machine.lastUpdate!!)

        findViewById<ImageView>(R.id.iv_close_machine_dialog).setOnClickListener {
            dismiss()
        }

        findViewById<Button>(R.id.btn_machine_dialog_use).setOnClickListener {
            Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

        findViewById<FloatingActionButton>(R.id.fab_machine_dialog_remove).setOnClickListener {
            // MachineRepository().deleteMachine(machine)
            viewModel.delete(MainActivity.appContext, machine.id)
/*
            viewModel.machineDeleteResponseSuccess.observe(viewLifecycleOwner, {
                Log.d("######## Delete", "Success")
                Toast.makeText(context, "Machine désynchronisée avec succès", Toast.LENGTH_SHORT).show()
                viewModel.findAll(requireActivity().applicationContext)
            })

            viewModel.machineDeleteResponseError.observe(viewLifecycleOwner, {
                Log.d("######## Delete", "Error")
                Toast.makeText(context, "Impossible de désynchroniser cette machine", Toast.LENGTH_SHORT).show()
            })
*/
            // Toast.makeText(context, "Désynchronisation de la machine...", Toast.LENGTH_SHORT).show()

            // dismiss()
        }
    }
}
