package fr.maximedubost.digikofyapp.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import fr.maximedubost.digikofyapp.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding  = RegisterFragmentBinding.inflate(inflater)

        val etRegisterEmail = binding.etRegisterEmail
        val etRegisterPassword = binding.etRegisterPassword
        val btnRegister = binding.btnRegister
        val tvLogin = binding.tvLogin

        tvLogin.setOnClickListener { view?.findNavController()?.popBackStack() }

        btnRegister.setOnClickListener {
            when {
                TextUtils.isEmpty(etRegisterEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Saisissez une addresse email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etRegisterPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Saisissez un mot de passe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = etRegisterEmail.text.toString().trim { it <= ' ' }
                    val password = etRegisterPassword.text.toString().trim { it <= ' ' }

                    viewModel.register(email, password)

                    viewModel.registerResponseSuccess.observe(viewLifecycleOwner, {
                        Log.d("SUCCESS >>>>>>>>> ", it.toString())
                        Toast.makeText(
                            requireActivity().applicationContext,
                            "Inscription réussie",
                            Toast.LENGTH_SHORT
                        ).show()

                        view?.findNavController()?.popBackStack()
                    })

                    viewModel.registerResponseError.observe(viewLifecycleOwner, {
                        Log.d("ERROR >>>>>>>>> ", it.toString())
                        Toast.makeText(
                            requireActivity().applicationContext,
                            "Echec de l'inscription",
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
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}