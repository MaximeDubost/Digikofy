package fr.maximedubost.digikofyapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.adapters.MachineAdapter
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.repositories.MachineRepository
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MachineDialog(
    private val adapter: MachineAdapter,
    private val machine: MachineModel
) : Dialog(adapter.context) {

    init { this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_machine)
        
        findViewById<TextView>(R.id.tv_detail_machine_name).text = machine.name
        findViewById<TextView>(R.id.tv_detail_machine_type).text = machine.typeToString()
        findViewById<TextView>(R.id.tv_detail_machine_state).text = machine.stateToString()
        findViewById<TextView>(R.id.tv_detail_machine_creation_date).text = StringDateTimeFormatter.from(machine.creationDate)
        findViewById<TextView>(R.id.tv_detail_machine_last_update).text = StringDateTimeFormatter.from(machine.lastUpdate)

        findViewById<ImageView>(R.id.iv_close_machine_dialog).setOnClickListener { dismiss() }
        findViewById<Button>(R.id.btn_machine_dialog_use).setOnClickListener {
            Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()
        }
        findViewById<FloatingActionButton>(R.id.fab_machine_dialog_remove).setOnClickListener {
            MachineRepository().deleteMachine(machine)
            dismiss()
        }
    }
}
