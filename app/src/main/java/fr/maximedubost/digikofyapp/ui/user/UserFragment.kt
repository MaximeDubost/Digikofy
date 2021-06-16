package fr.maximedubost.digikofyapp.ui.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.HomeFragmentBinding
import fr.maximedubost.digikofyapp.databinding.UserFragmentBinding
import fr.maximedubost.digikofyapp.session.DigikofySession
import fr.maximedubost.digikofyapp.ui.main.MainFragmentDirections

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding  = UserFragmentBinding.inflate(inflater)

        val ivBack = binding.ivBack
        val btnLogout = binding.btnLogout

        ivBack.setOnClickListener {
            view?.findNavController()?.navigate(
                UserFragmentDirections.actionUserFragmentToMainFragment()
            )
        }

        btnLogout.setOnClickListener {
            Toast.makeText(
                context,
                "Déconnecté avec succès",
                Toast.LENGTH_SHORT
            ).show()
            DigikofySession.clear(requireContext())
            // TODO : faire l'appel API à /revoke
            view?.findNavController()?.navigate(
                UserFragmentDirections.actionUserFragmentToLoginFragment()
            )
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}