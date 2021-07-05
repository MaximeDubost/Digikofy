package fr.maximedubost.digikofyapp.ui.machine

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.DetailMachineFragmentBinding
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class DetailMachineFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMachineFragment()
    }

private val args: DetailMachineFragmentArgs by navArgs()

    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: DetailMachineFragmentBinding
    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // val machineId: String = DetailMachineFragmentArgs.fromBundle(requireArguments()).machineId

        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        binding = DetailMachineFragmentBinding.inflate(inflater)

        //viewModel = ViewModelProvider.NewInstanceFactory().create(MachineViewModel::class.java)

        // viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)

        binding.loading.visibility = View.VISIBLE
        binding.ivEdit.visibility = View.INVISIBLE
        binding.body.visibility = View.GONE
        binding.btnDeleteMachine.visibility = View.GONE

        binding.ivBack.setOnClickListener {

            // TODO : Close keyboard

            if(isEditMode)
                toggleEditMode(clearEditText = true)
            else
                view?.findNavController()?.popBackStack()
        }


        // TODO : call getMachine with machineId and show data
        viewModel.findById(requireActivity().applicationContext, args.machineId)

        viewModel.machineFindByIdResponseSuccess.observe(viewLifecycleOwner, {

            val machine = it.data.body()!!
            Log.d(">>>> creationDate", machine.creationDate.toString())
            Log.d(">>>> type", machine.creationDate!!::class.simpleName!!.toString())
            Log.d(">>>> creationDate", machine.lastUpdate.toString())
            Log.d(">>>> type", machine.lastUpdate!!::class.simpleName!!.toString())


            binding.loading.visibility = View.GONE
            binding.body.visibility = View.VISIBLE
            binding.ivEdit.visibility = View.VISIBLE
            binding.tvMachineName.text = it.data.body()!!.name
            binding.tvMachineType.text = MachineModel.typeToString(machine.type!!)
            binding.tvMachineState.text = MachineModel.stateToString(machine.state!!)
            binding.tvMachineCreationDate.text = StringDateTimeFormatter.from(machine.creationDate.toString())
                //StringDateTimeFormatter.from(machine.creationDate!!)
            binding.tvMachineLastUpdate.text = StringDateTimeFormatter.from(machine.lastUpdate.toString())
                //StringDateTimeFormatter.from(machine.lastUpdate!!)

            binding.ivEdit.setOnClickListener {

                // TODO : Close keyboard

                if(isEditMode && binding.etMachineName.text.toString() != machine.name)
                {
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

                else
                    toggleEditMode(machine.name!!)
            }

            binding.btnUseMachine.setOnClickListener {
                Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()
            }

            binding.btnDeleteMachine.setOnClickListener {

                //Toast.makeText(requireActivity().applicationContext, "Désynchronisation de la machine...", Toast.LENGTH_SHORT).show()

                viewModel.delete(requireActivity().applicationContext, machine.id!!)

                viewModel.machineDeleteResponseSuccess.observe(viewLifecycleOwner, {
                    Log.d("######## Delete", "Success")
                    Toast.makeText(requireActivity().applicationContext, "Machine désynchronisée avec succès", Toast.LENGTH_SHORT).show()
                    // viewModel.findAll(requireActivity().applicationContext)
                    view?.findNavController()?.popBackStack()
                })

                viewModel.machineDeleteResponseError.observe(viewLifecycleOwner, {
                    Log.d("######## Delete", "Error")
                    Toast.makeText(requireActivity().applicationContext, "Impossible de désynchroniser cette machine", Toast.LENGTH_SHORT).show()
                })

            }
/*
            fabMachineDialogRemove.setOnClickListener {
                // MachineRepository().deleteMachine(machine)
                viewModel.delete(MainActivity.appContext, args.machineId)

                viewModel.machineDeleteResponseSuccess.observe(viewLifecycleOwner, {
                    Log.d("######## Delete", "Success")
                    Toast.makeText(requireActivity().applicationContext, "Machine désynchronisée avec succès", Toast.LENGTH_SHORT).show()
                    // viewModel.findAll(requireActivity().applicationContext)
                    view?.findNavController()?.popBackStack()
                })

                viewModel.machineDeleteResponseError.observe(viewLifecycleOwner, {
                    Log.d("######## Delete", "Error")
                    Toast.makeText(context, "Impossible de désynchroniser cette machine", Toast.LENGTH_SHORT).show()
                })

                // Toast.makeText(context, "Désynchronisation de la machine...", Toast.LENGTH_SHORT).show()

                // dismiss()
            }
 */
        })

        viewModel.machineFindByIdResponseError.observe(viewLifecycleOwner, {
            binding.loading.visibility = View.GONE
            Toast.makeText(requireActivity().applicationContext, "Error", Toast.LENGTH_SHORT).show()
        })

        /*
        findViewById<ImageView>(R.id.iv_close_machine_dialog).setOnClickListener {
            dismiss()
        }
        */

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun toggleEditMode(machineName: String = "", clearEditText: Boolean = false) {
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

}