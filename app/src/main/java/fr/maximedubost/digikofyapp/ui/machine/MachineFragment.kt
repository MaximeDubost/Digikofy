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
import androidx.navigation.fragment.findNavController
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.adapters.MachineAdapter
import fr.maximedubost.digikofyapp.databinding.MachineFragmentBinding
import fr.maximedubost.digikofyapp.ui.main.MainFragment
import fr.maximedubost.digikofyapp.ui.main.MainFragmentDirections

class MachineFragment : Fragment() {
    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: MachineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MachineFragmentBinding.inflate(inflater)

        binding.loading.visibility = View.VISIBLE
        binding.lavNoMachine.visibility = View.GONE
        binding.tvNoMachine.visibility = View.GONE
        binding.tvErrorMachine.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)

        viewModel.findAll(requireActivity().applicationContext)

        viewModel.machineFindAllResponseSuccess.observe(viewLifecycleOwner, {
            binding.rvMachines.adapter = MachineAdapter({ machineId ->
                view.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMachineDetailsFragment(machineId)
                )},
                it.data.body()!!
            )

            binding.loading.visibility = View.GONE
            binding.lavNoMachine.visibility =
                if (it.data.body()!!.isEmpty()) View.VISIBLE
                else View.GONE
            binding.tvNoMachine.visibility =
                if (it.data.body()!!.isEmpty()) View.VISIBLE
                else View.GONE
            binding.tvErrorMachine.visibility = View.GONE
        })

        viewModel.machineFindAllResponseError.observe(viewLifecycleOwner, {
            binding.rvMachines.adapter = MachineAdapter(
                {},
                listOf()
            )

            binding.loading.visibility = View.GONE
            binding.lavNoMachine.visibility = View.GONE
            binding.tvNoMachine.visibility = View.GONE
            binding.tvErrorMachine.visibility = View.VISIBLE
        })
    }

}