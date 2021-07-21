package fr.maximedubost.digikofyapp.ui.machine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.MachineDetailsFragmentBinding
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class MachineDetailsFragment : Fragment() {
    private val args: MachineDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: MachineDetailsFragmentBinding
    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MachineDetailsFragmentBinding.inflate(inflater)

        binding.loading.visibility = View.VISIBLE
        binding.ivEdit.visibility = View.INVISIBLE
        binding.body.visibility = View.GONE
        binding.btnDeleteMachine.visibility = View.GONE

        binding.ivBack.setOnClickListener { onClickIvBack() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)

        viewModel.findById(requireActivity().applicationContext, args.machineId)

        viewModel.machineFindByIdResponseSuccess.observe(viewLifecycleOwner, {
            val machine = it.data.body()!!

            showData(machine)

            binding.ivEdit.setOnClickListener { onClickIvEdit(machine) }
            binding.btnUseMachine.setOnClickListener { onClickBtnUseMachine(machine) }
            binding.btnDeleteMachine.setOnClickListener { deleteMachine(machine) }
        })

        viewModel.machineFindByIdResponseError.observe(viewLifecycleOwner, {
            binding.loading.visibility = View.GONE
            Toast.makeText(
                requireActivity().applicationContext,
                "Impossible d'afficher les détails de la machine",
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    /**
     * Switch between read mode and edit mode
     *
     * @param machineName name of machine
     * @param clearEditText true if EditText should be cleared, else false
     */
    private fun toggleEditMode(machineName: String = "", clearEditText: Boolean = false) {
        isEditMode = !isEditMode
        binding.ivEdit.setImageResource(if(isEditMode) R.drawable.ic_check else R.drawable.ic_edit)
        binding.ivBack.setImageResource(if(isEditMode) R.drawable.ic_close else R.drawable.ic_back)
        binding.tvMachineName.visibility = if(isEditMode) View.GONE else View.VISIBLE
        binding.tilMachineName.visibility = if(isEditMode) View.VISIBLE else View.GONE
        binding.btnUseMachine.visibility = if(isEditMode) View.GONE else View.VISIBLE
        binding.btnDeleteMachine.visibility = if(isEditMode) View.VISIBLE else View.GONE
        if(isEditMode)
            binding.etMachineName.setText(machineName)
        if(clearEditText)
            binding.etMachineName.setText("")
    }

    /**
     * Action on click ivBack
     */
    private fun onClickIvBack() {
        // TODO : Close keyboard
        if(isEditMode)
            toggleEditMode(clearEditText = true)
        else
            view?.findNavController()?.popBackStack()
    }

    /**
     * Action on click ivEdit
     *
     * @param machine a machine
     */
    private fun onClickIvEdit(machine: MachineModel) {
        // TODO : Close keyboard
        if(isEditMode && binding.etMachineName.text.toString() != machine.name)
            updateMachine(machine)
        else
            toggleEditMode(machine.name!!)
    }

    /**
     * Action on click btnUseMachine
     *
     * @param machine a machine
     */
    private fun onClickBtnUseMachine(machine: MachineModel) {
        // TODO : Redirect to new preparation view
        Toast.makeText(
            requireActivity().applicationContext,
            "Lancement de la préparation...",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Show fetched machine data
     *
     * @param machine fetched preparation
     */
    private fun showData(machine: MachineModel) {
        binding.loading.visibility = View.GONE
        binding.body.visibility = View.VISIBLE
        binding.ivEdit.visibility = View.VISIBLE
        binding.tvMachineName.text = machine.name
        binding.tvMachineType.text = MachineModel.typeToString(machine.type!!)
        binding.tvMachineState.text = MachineModel.stateToString(machine.state!!)
        binding.tvMachineCreationDate.text = StringDateTimeFormatter.from(machine.creationDate.toString())
        binding.tvMachineLastUpdate.text = StringDateTimeFormatter.from(machine.lastUpdate.toString())
    }

    /**
     * Update a machine
     *
     * @param machine a machine
     */
    private fun updateMachine(machine: MachineModel) {
        viewModel.update(
            requireActivity().applicationContext,
            MachineModel(machine.id, binding.etMachineName.text.toString())
        )

        viewModel.machineUpdateResponseSuccess.observe(viewLifecycleOwner, {
            binding.tvMachineName.text = binding.etMachineName.text.toString()
            Toast.makeText(requireContext().applicationContext, "Modif OK", Toast.LENGTH_SHORT).show()
            toggleEditMode()
        })

        viewModel.machineUpdateResponseError.observe(viewLifecycleOwner, {
            binding.etMachineName.setText(machine.name)
            Toast.makeText(requireContext().applicationContext, "Modif Error", Toast.LENGTH_SHORT).show()
            toggleEditMode()
        })
    }

    /**
     * Delete a machine
     *
     * @param machine a machine
     */
    private fun deleteMachine(machine: MachineModel) {
        viewModel.delete(requireActivity().applicationContext, machine.id!!)

        viewModel.machineDeleteResponseSuccess.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireActivity().applicationContext,
                "Machine désynchronisée avec succès",
                Toast.LENGTH_SHORT
            ).show()
            view?.findNavController()?.popBackStack()
        })

        viewModel.machineDeleteResponseError.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireActivity().applicationContext,
                "Impossible de désynchroniser cette machine",
                Toast.LENGTH_SHORT
            ).show()
        })
    }

}