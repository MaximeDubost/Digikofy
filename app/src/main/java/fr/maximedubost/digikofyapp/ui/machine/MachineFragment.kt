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

    companion object {
        fun newInstance() = MachineFragment()
    }

    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: MachineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("onCreateView >>>>>>>>", "")
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        binding = MachineFragmentBinding.inflate(inflater)

        val loading = binding.loading
        val lavNoMachine = binding.lavNoMachine
        val tvNoMachine = binding.tvNoMachine
        val tvErrorMachine = binding.tvErrorMachine
        val rvMachines = binding.rvMachines

        //rvMachines.adapter = MachineAdapter(MachineRepository.Singleton.machineList, this.activity as MainActivity)

        loading.visibility = View.VISIBLE
        lavNoMachine.visibility = View.GONE
        tvNoMachine.visibility = View.GONE
        tvErrorMachine.visibility = View.GONE

        viewModel.findAll(requireActivity().applicationContext)

        viewModel.machineFindAllResponseSuccess.observe(viewLifecycleOwner, {
            Log.d("SUCCESS >>>>>>>>", it.data.body().toString())

            rvMachines.adapter = MachineAdapter({ machineId ->

                view?.findNavController()?.navigate(
                    MainFragmentDirections.actionMainFragmentToDetailMachineFragment(machineId)
                )

            }, it.data.body()!!, this.activity as MainActivity)

            loading.visibility = View.GONE
            lavNoMachine.visibility = if (it.data.body()!!.isEmpty()) View.VISIBLE else View.GONE
            tvNoMachine.visibility = if (it.data.body()!!.isEmpty()) View.VISIBLE else View.GONE
            tvErrorMachine.visibility = View.GONE
        })

        viewModel.machineFindAllResponseError.observe(viewLifecycleOwner, {
            Log.d("ERROR >>>>>>>>", it.toString())
            rvMachines.adapter = MachineAdapter({}, listOf(), this.activity as MainActivity)

            loading.visibility = View.GONE
            lavNoMachine.visibility = View.GONE
            tvNoMachine.visibility = View.GONE
            tvErrorMachine.visibility = View.VISIBLE
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

}