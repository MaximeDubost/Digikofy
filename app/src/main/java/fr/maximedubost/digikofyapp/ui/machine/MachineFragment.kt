package fr.maximedubost.digikofyapp.ui.machine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.adapters.MachineAdapter
import fr.maximedubost.digikofyapp.databinding.HomeFragmentBinding
import fr.maximedubost.digikofyapp.databinding.MachineFragmentBinding
import fr.maximedubost.digikofyapp.repositories.MachineRepository

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

        binding  = MachineFragmentBinding.inflate(inflater)

        val lavNoMachine = binding.lavNoMachine
        val tvNoMachine = binding.tvNoMachine
        lavNoMachine.visibility = if (MachineRepository.Singleton.machineList.size == 0) View.VISIBLE else View.GONE
        tvNoMachine.visibility = if (MachineRepository.Singleton.machineList.size == 0) View.VISIBLE else View.GONE
        val rvMachines = binding.rvMachines
        rvMachines.adapter = MachineAdapter(MachineRepository.Singleton.machineList, this.activity as MainActivity)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}