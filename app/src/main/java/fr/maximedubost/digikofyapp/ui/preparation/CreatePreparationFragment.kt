package fr.maximedubost.digikofyapp.ui.preparation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.adapters.HourAdapter
import fr.maximedubost.digikofyapp.databinding.CreatePreparationFragmentBinding
import fr.maximedubost.digikofyapp.models.CoffeeModel

class CreatePreparationFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: PreparationViewModel
    private lateinit var binding: CreatePreparationFragmentBinding

    private var monday: Boolean = false
    private var tuesday: Boolean = false
    private var wednesday: Boolean = false
    private var thursday: Boolean = false
    private var friday: Boolean = false
    private var saturday: Boolean = false
    private var sunday: Boolean = false

    private var hour: Int = 0
    private var minute: Int = 0

    private val preparationHourList = mutableListOf<String>()

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreatePreparationFragmentBinding.inflate(inflater)

        binding.ivBack.setOnClickListener { view?.findNavController()?.popBackStack() }

        ArrayAdapter.createFromResource(
            requireActivity().applicationContext,
            R.array.machine_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spnCoffees.adapter = adapter
        }

        binding.spnCoffees.onItemSelectedListener = this

        binding.tpPreparationTime.setIs24HourView(true)

        binding.clCreateSavedPreparation.visibility = View.GONE
        binding.clCreateUniquePreparation.visibility = View.VISIBLE
        binding.tvUniquePlaningPreparation.setTextColor(Color.BLACK)
        binding.tvMultiplePlaningPreparation.setTextColor(Color.GRAY)

        binding.switchPlaningType.setOnClickListener {
            if (binding.switchPlaningType.isChecked) {
                binding.clCreateSavedPreparation.visibility = View.VISIBLE
                binding.clCreateUniquePreparation.visibility = View.GONE
                binding.tvUniquePlaningPreparation.setTextColor(Color.GRAY)
                binding.tvMultiplePlaningPreparation.setTextColor(resources.getColor(R.color.blue, null))
            }
            else {
                binding.clCreateSavedPreparation.visibility = View.GONE
                binding.clCreateUniquePreparation.visibility = View.VISIBLE
                binding.tvUniquePlaningPreparation.setTextColor(Color.BLACK)
                binding.tvMultiplePlaningPreparation.setTextColor(Color.GRAY)
            }
        }

        binding.clPreparationLater.visibility = View.GONE
        binding.tvPreparationNow.setTextColor(Color.BLACK)
        binding.tvPreparationLater.setTextColor(Color.GRAY)

        binding.switchWhenPreparation.setOnClickListener {
            if (binding.switchWhenPreparation.isChecked) {
                binding.clPreparationLater.visibility = View.VISIBLE
                binding.tvPreparationNow.setTextColor(Color.GRAY)
                binding.tvPreparationLater.setTextColor(resources.getColor(R.color.blue, null))
            }
            else {
                binding.clPreparationLater.visibility = View.GONE
                binding.tvPreparationNow.setTextColor(Color.BLACK)
                binding.tvPreparationLater.setTextColor(Color.GRAY)
            }
        }

        binding.btnMonday.setOnClickListener {
            onClickWeekdayButton(binding.btnMonday, 0, monday)
        }
        binding.btnTuesday.setOnClickListener {
            onClickWeekdayButton(binding.btnTuesday, 1, tuesday)
        }
        binding.btnWednesday.setOnClickListener {
            onClickWeekdayButton(binding.btnWednesday, 2, wednesday)
        }
        binding.btnThursday.setOnClickListener {
            onClickWeekdayButton(binding.btnThursday, 3, thursday)
        }
        binding.btnFriday.setOnClickListener {
            onClickWeekdayButton(binding.btnFriday, 4, friday)
        }
        binding.btnSaturday.setOnClickListener {
            onClickWeekdayButton(binding.btnSaturday, 5, saturday)
        }
        binding.btnSunday.setOnClickListener {
            onClickWeekdayButton(binding.btnSunday, 6, sunday)
        }




        // Set DatePicker to 24h format
        binding.tpPreparationTimeMultiple.setIs24HourView(true)

        // TODO : remove this part of code
        binding.tvNoHour.visibility =
            if(preparationHourList.size == 0)
                View.VISIBLE
            else
                View.GONE

        // Define default hour and minute
        hour = binding.tpPreparationTimeMultiple.hour
        minute = binding.tpPreparationTimeMultiple.minute

        // When TimePicker changes
        binding.tpPreparationTimeMultiple.setOnTimeChangedListener { _, hour, minute ->
            this.hour = hour
            this.minute = minute
        }

        // When fab add click
        binding.fabAddHour.setOnClickListener {
            val newHour = "${if(hour < 10) "0$hour" else hour}:${if(minute < 10) "0$minute" else minute}"

            if(!preparationHourList.contains(newHour)) {
                addPreparationHour(newHour)
            }
            else {
                Toast.makeText(requireActivity().applicationContext,
                "Cet horaire a déjà été ajouté dans la liste",
                Toast.LENGTH_SHORT).show()
            }


        }

        // Adapter of list hour with remove callback
        binding.rvPreparationHours.adapter = HourAdapter(
            { removePreparationHour(it) },
            preparationHourList
        )

        return binding.root
    }

    private fun addPreparationHour(hour: String) {
        binding.tvNoHour.visibility = View.GONE
        if(preparationHourList.size <= 12) {
            preparationHourList.add(hour)
            preparationHourList.sort()
            binding.rvPreparationHours.adapter?.notifyDataSetChanged()
        }
        else
            Toast.makeText(
                requireActivity().applicationContext,
            "Vous ne pouvez pas ajouter plus de 12 horaires par préparation",
                Toast.LENGTH_SHORT
            ).show()

    }

    private fun removePreparationHour(index: Int) {
        preparationHourList.removeAt(index)
        preparationHourList.sort()
        binding.rvPreparationHours.adapter?.notifyDataSetChanged()
        binding.tvNoHour.visibility =
            if(preparationHourList.size == 0)
                View.VISIBLE
            else
                View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PreparationViewModel::class.java)

        if("Data was fetched".isNotBlank()) {
            // TODO : call API to get coffee list
            val coffees: List<CoffeeModel> = mutableListOf(
                CoffeeModel("", "Ristretto", ""),
                CoffeeModel("", "Macchiato", ""),
                CoffeeModel("", "Flat white", ""),
                CoffeeModel("", "Galão", ""),
                CoffeeModel("", "Doppio", ""),
                CoffeeModel("", "Moka", ""),
                CoffeeModel("", "Espresso", ""),
                CoffeeModel("", "Lungo", ""),
                CoffeeModel("", "Latte", ""),
                CoffeeModel("", "Café au Lait", ""),
                CoffeeModel("", "Cappuccino", ""),
                CoffeeModel("", "Red eye", ""),
                CoffeeModel("", "Irlandais", ""),
                CoffeeModel("", "Americano", ""),
                CoffeeModel("", "Affogato", ""),
                CoffeeModel("", "Noir", ""),
                CoffeeModel("", "Cortado", "")
            ) // response.body()

            val coffeeNames: MutableList<String> = mutableListOf()

            coffees.forEach { coffeeNames.add(it.name) }

            binding.spnCoffees.adapter = ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                coffeeNames
            )
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        parent?.getItemAtPosition(pos)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NewApi", "UseCompatLoadingForDrawables")
    fun onClickWeekdayButton(weekdayButton: Button, weekdayIndex: Int, isWeekdaySelected: Boolean) {

        if(isWeekdaySelected) {
            weekdayButton.background = resources.getDrawable(R.drawable.grey_rounded_corner, null)
            weekdayButton.setTextColor(resources.getColor(R.color.grey, null))
        }
        else {
            weekdayButton.background = resources.getDrawable(R.drawable.blue_rounded_corner, null)
            weekdayButton.setTextColor(resources.getColor(R.color.blue, null))
        }

        when(weekdayIndex) {
            0 -> monday = !monday
            1 -> tuesday = !tuesday
            2 -> wednesday = !wednesday
            3 -> thursday = !thursday
            4 -> friday = !friday
            5 -> saturday = !saturday
            6 -> sunday = !sunday
        }

    }
}