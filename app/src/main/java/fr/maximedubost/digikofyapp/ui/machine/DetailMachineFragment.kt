package fr.maximedubost.digikofyapp.ui.machine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.CreateMachineFragmentBinding
import fr.maximedubost.digikofyapp.databinding.DetailMachineFragmentBinding
import fr.maximedubost.digikofyapp.databinding.MachineFragmentBinding
import fr.maximedubost.digikofyapp.enums.MachineType
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class DetailMachineFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMachineFragment()
    }

private val args: DetailMachineFragmentArgs by navArgs()

    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: DetailMachineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // val machineId: String = DetailMachineFragmentArgs.fromBundle(requireArguments()).machineId

        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        binding = DetailMachineFragmentBinding.inflate(inflater)

        //viewModel = ViewModelProvider.NewInstanceFactory().create(MachineViewModel::class.java)

        // viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)

        val tvDetailMachineName = binding.tvDetailMachineName
        val tvDetailMachineCreationDate = binding.tvDetailMachineCreationDate
        val tvDetailMachineLastUpdate = binding.tvDetailMachineLastUpdate
        val tvDetailMachineState = binding.tvDetailMachineState
        val tvDetailMachineType = binding.tvDetailMachineType

        val btnUseMachine = binding.btnUseMachine
        val fabMachineDialogRemove = binding.fabMachineDialogRemove
        // val ivAction = binding.ivAction
        val ivBack = binding.ivBack

        ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        // TODO : call getMachine with machineId and show data
        viewModel.findById(requireActivity().applicationContext, args.machineId)

        viewModel.machineFindByIdResponseSuccess.observe(viewLifecycleOwner, {

            tvDetailMachineName.text = it.data.body()!!.name
            tvDetailMachineType.text = MachineModel.typeToString(it.data.body()!!.type!!)
            tvDetailMachineState.text = MachineModel.stateToString(it.data.body()!!.state!!)
            tvDetailMachineCreationDate.text = it.data.body()!!.creationDate
            tvDetailMachineLastUpdate.text = it.data.body()!!.lastUpdate

            btnUseMachine.setOnClickListener {
                Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show()
            }

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

}