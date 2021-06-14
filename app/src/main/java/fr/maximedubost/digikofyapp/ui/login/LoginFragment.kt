package fr.maximedubost.digikofyapp.ui.login

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
import com.google.firebase.auth.FirebaseAuth
import fr.maximedubost.digikofyapp.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = LoginFragmentBinding.inflate(inflater)

        val etLoginEmail = binding.etLoginEmail
        val etLoginPassword = binding.etLoginPassword
        val btnLogin = binding.btnLogin
        val tvRegister = binding.tvRegister

        tvRegister.setOnClickListener {
            view?.findNavController()?.navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        btnLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Saisissez une addresse email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Saisissez un mot de passe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = etLoginEmail.text.toString().trim { it <= ' ' }
                    val password = etLoginPassword.text.toString().trim { it <= ' ' }

                    viewModel.login(email, password)

                    viewModel.loginResponseSuccess.observe(viewLifecycleOwner, {
                        Log.d("SUCCESS >>>>>>>>> ", it.toString())
                        Toast.makeText(
                            context,
                            "Connexion réussie",
                            Toast.LENGTH_SHORT
                        ).show()

                        view?.findNavController()?.navigate(
                            LoginFragmentDirections.actionLoginFragmentToMainFragment()
                        )
                        // TODO : SharedPreferences
                    })

                    viewModel.loginResponseError.observe(viewLifecycleOwner, {
                        Log.d("ERROR >>>>>>>>> ", it.toString())
                        Toast.makeText(
                            context,
                            "Echec de la connexion",
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
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}