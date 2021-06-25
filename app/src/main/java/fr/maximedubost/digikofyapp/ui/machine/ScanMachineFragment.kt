package fr.maximedubost.digikofyapp.ui.machine

import android.R.attr
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.maximedubost.digikofyapp.databinding.ScanMachineFragmentBinding


class ScanMachineFragment : Fragment() {

    companion object {
        fun newInstance() = ScanMachineFragment()
    }

    private lateinit var viewModel: MachineViewModel
    private lateinit var binding: ScanMachineFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(MachineViewModel::class.java)
        binding = ScanMachineFragmentBinding.inflate(inflater)

        binding.btnScan.setOnClickListener {


        }
        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}