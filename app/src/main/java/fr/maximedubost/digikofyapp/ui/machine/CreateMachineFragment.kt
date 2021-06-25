package fr.maximedubost.digikofyapp.ui.machine

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.google.gson.Gson
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.CreateMachineFragmentBinding
import fr.maximedubost.digikofyapp.enums.MachineType
import fr.maximedubost.digikofyapp.models.MachineModel
import kotlin.reflect.typeOf

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
        val ivScanMachine = binding.ivAction
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

        ivScanMachine.setOnClickListener {
            try {
                val intent = Intent("com.google.zxing.client.android.SCAN")
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE") // "PRODUCT_MODE for bar codes
                startActivityForResult(intent, 0)
            } catch (e: Exception) {
                val marketUri: Uri =
                    Uri.parse("market://details?id=com.google.zxing.client.android")
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                startActivity(marketIntent)
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val contents: String? = data?.getStringExtra("SCAN_RESULT")
                Log.d("onActivityResult",contents)
                try {
                    val machineModel : MachineModel = Gson().fromJson(contents, MachineModel::class.java)
                    Log.d("onActivityResult", "$contents Val json : $machineModel")
                    binding.etMachineId.setText(machineModel.id)
                    binding.etMachineName.setText(machineModel.name)
                    when(machineModel.type) {
                        MachineType.STANDARD.ordinal -> binding.spnMachineType.setSelection(MachineType.STANDARD.ordinal)
                        MachineType.ENTERPRISE.ordinal -> binding.spnMachineType.setSelection(MachineType.ENTERPRISE.ordinal)
                    }
                }catch (ex : java.lang.Exception) {
                    //TODO
                    Toast.makeText(requireContext().applicationContext,"Invalid QR Code", Toast.LENGTH_SHORT).show()
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(requireActivity().applicationContext, "Qr Code reading failed", Toast.LENGTH_SHORT).show()
            }
        }

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