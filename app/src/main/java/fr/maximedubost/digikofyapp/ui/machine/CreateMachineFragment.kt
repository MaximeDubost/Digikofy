package fr.maximedubost.digikofyapp.ui.machine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.CreateMachineFragmentBinding
import fr.maximedubost.digikofyapp.models.MachineModel

class CreateMachineFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = CreateMachineFragment()
    }

    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: CreateMachineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("######## CreateMachine", "onCreateView()")
        binding = CreateMachineFragmentBinding.inflate(inflater)

        val ivBack = binding.ivBack
        val btnCreateMachine = binding.btnCreateMachine
        val etMachineId = binding.etMachineId
        val etMachineName = binding.etMachineName
        // val ivScanMachine = binding.ivScanMachine
        val spnMachineType = binding.spnMachineType

        ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireActivity().applicationContext,
            R.array.machine_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnMachineType.adapter = adapter
        }

        spnMachineType.onItemSelectedListener = this

        btnCreateMachine.setOnClickListener {
            when {
                TextUtils.isEmpty(etMachineId.text.toString().trim { it <= ' ' })
                        || etMachineId.text.toString().length != 20 -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Saisissez un identifiant de machine valide de 20 caractères",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    val machineId = etMachineId.text.toString().trim { it <= ' ' }
                    val machineName =
                        if (TextUtils.isEmpty(etMachineName.text.toString().trim { it <= ' ' }))
                            String.format("M-%s", etMachineId.text.toString())
                        else
                            etMachineName.text.toString().trim { it <= ' ' }

                    viewModel.create(requireActivity().applicationContext,
                        MachineModel(
                            machineId,
                            machineName,
                            spnMachineType.selectedItemPosition,
                            0, // TODO : null
                            null,
                            null
                        )
                    )

                    viewModel.machineCreateResponseSuccess.observe(viewLifecycleOwner, {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            "Machine synchronysée avec succès",
                            Toast.LENGTH_SHORT
                        ).show()

                        view?.findNavController()?.popBackStack()

                    })

                    viewModel.machineCreateResponseError.observe(viewLifecycleOwner, {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            "Echec de la synchronysation de la machine",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }
            }
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        parent?.getItemAtPosition(pos)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}