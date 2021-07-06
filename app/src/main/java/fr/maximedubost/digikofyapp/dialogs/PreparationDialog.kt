package fr.maximedubost.digikofyapp.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.maximedubost.digikofyapp.R

import fr.maximedubost.digikofyapp.adapters.PreparationAdapter
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.oldrepositories.PreparationRepository
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class PreparationDialog(
    private val adapter: PreparationAdapter,
    private val preparation: PreparationModel,
    private val isSavedPreparationsPage: Boolean,
    private val isNextPreparationsPage: Boolean,
    private val isPastPreparationsPage: Boolean,
) : Dialog(adapter.context) {
    init { this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_preparation)

        val tvDetailPreparationCoffeeName: TextView = findViewById(R.id.tv_detail_preparation_coffee_name)
        val tvDetailPreparationCreationDate: TextView = findViewById(R.id.tv_detail_preparation_creation_date)
        val tvDetailPreparationLastUpdate: TextView = findViewById(R.id.tv_detail_preparation_last_update)
        val tvDetailPreparationName: TextView = findViewById(R.id.tv_detail_preparation_name)
        val tvDetailPreparationDaysOfWeek: TextView = findViewById(R.id.tv_detail_preparation_days_of_week)
        val tvDetailPreparationHours: TextView = findViewById(R.id.tv_detail_preparation_hours)

        val tvLabelDetailPreparationCreationDate: TextView = findViewById(R.id.tv_label_detail_preparation_creation_date)
        val tvLabelDetailPreparationLastUpdate: TextView = findViewById(R.id.tv_label_detail_preparation_last_update)

        tvDetailPreparationCoffeeName.text = preparation.coffee!!.name
        if(isSavedPreparationsPage) {
            tvLabelDetailPreparationCreationDate.visibility = View.VISIBLE
            tvDetailPreparationCreationDate.visibility = View.VISIBLE
            tvLabelDetailPreparationLastUpdate.visibility = View.VISIBLE
            tvDetailPreparationLastUpdate.visibility = View.VISIBLE
            tvDetailPreparationCreationDate.text = StringDateTimeFormatter.from(preparation.creationDate!!)
            tvDetailPreparationLastUpdate.text = StringDateTimeFormatter.from(preparation.lastUpdate!!)
        }
        else {
            tvLabelDetailPreparationCreationDate.visibility = View.GONE
            tvDetailPreparationCreationDate.visibility = View.GONE
            tvLabelDetailPreparationLastUpdate.visibility = View.GONE
            tvDetailPreparationLastUpdate.visibility = View.GONE
        }

        if (preparation.saved && isSavedPreparationsPage) {
            tvDetailPreparationName.visibility = View.VISIBLE
            tvDetailPreparationHours.visibility = View.VISIBLE
            tvDetailPreparationName.text = preparation.name
            tvDetailPreparationDaysOfWeek.text = StringDateTimeFormatter.weekdays(preparation.weekdays!!)
            tvDetailPreparationHours.text = StringDateTimeFormatter.hours(preparation.hours!!)
        }
        else {
            tvDetailPreparationName.visibility = View.GONE
            if(isNextPreparationsPage) tvDetailPreparationDaysOfWeek.text = StringDateTimeFormatter.from(preparation.nextTime!!)
            if(isPastPreparationsPage) tvDetailPreparationDaysOfWeek.text = StringDateTimeFormatter.from(preparation.lastTime!!)
            tvDetailPreparationHours.visibility = View.GONE
        }

        findViewById<ImageView>(R.id.iv_close_preparation_dialog).setOnClickListener { dismiss() }
        findViewById<Button>(R.id.btn_preparation_dialog_prepare).setOnClickListener {
            Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

        val fabPreparationDialogRemove: FloatingActionButton = findViewById(R.id.fab_preparation_dialog_remove)

        if(isSavedPreparationsPage) {
            fabPreparationDialogRemove.visibility = View.VISIBLE
            fabPreparationDialogRemove.setOnClickListener {
                PreparationRepository().deletePreparation(preparation)
                dismiss()
            }
        }
        else {
            fabPreparationDialogRemove.visibility = View.GONE
        }

    }
}