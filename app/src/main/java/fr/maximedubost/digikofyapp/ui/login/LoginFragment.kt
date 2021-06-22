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
import com.google.common.base.Strings
import fr.maximedubost.digikofyapp.databinding.LoginFragmentBinding
import fr.maximedubost.digikofyapp.models.LoginResponseModel
import fr.maximedubost.digikofyapp.session.DigikofySession
import fr.maximedubost.digikofyapp.utils.Constants

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
        if (DigikofySession.isLoggedIn(requireActivity().applicationContext)) {
            view?.findNavController()?.navigate(
                LoginFragmentDirections.actionLoginFragmentToMainFragment()
            )
        }

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
                        requireActivity().applicationContext,
                        "Saisissez une addresse email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Saisissez un mot de passe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = etLoginEmail.text.toString().trim { it <= ' ' }
                    val password = etLoginPassword.text.toString().trim { it <= ' ' }

                    viewModel.login(email, password)

                    viewModel.loginResponseSuccess.observe(viewLifecycleOwner, {
                        // Log.d("SUCCESS >>>>>>>>> ", it.toString())
                        Toast.makeText(
                            requireActivity().applicationContext,
                            Constants.CONNECTION_SUCCESSED,
                            Toast.LENGTH_SHORT
                        ).show()

                        DigikofySession.create(requireActivity().applicationContext,
                            LoginResponseModel(
                                it.data.body()!!.idToken,
                                it.data.body()!!.email,
                                it.data.body()!!.refreshToken,
                                it.data.body()!!.expiresIn,
                                it.data.body()!!.localId,
                                it.data.body()!!.registered
                            )
                        )

                        view?.findNavController()?.navigate(
                            LoginFragmentDirections.actionLoginFragmentToMainFragment()
                        )
                    })

                    viewModel.loginResponseError.observe(viewLifecycleOwner, {
                        // Log.d("ERROR >>>>>>>>> ", it.toString())
                        Toast.makeText(
                            requireActivity().applicationContext,
                            Constants.CONNECTION_FAILED,
                            Toast.LENGTH_SHORT
                        ).show()
                    })

                    etLoginEmail.text.clear()
                    etLoginPassword.text.clear()
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d(">>>>>>>>>>>>> Resume F", "Resume F")

        if (DigikofySession.exists(requireActivity().applicationContext)) {
            view?.findNavController()?.navigate(
                LoginFragmentDirections.actionLoginFragmentToMainFragment()
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}