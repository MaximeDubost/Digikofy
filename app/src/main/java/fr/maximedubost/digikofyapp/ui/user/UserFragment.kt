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
        val btnLogoutUser = binding.btnLogoutUser
        val btnDeleteUser = binding.btnDeleteUser

        ivBack.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        btnLogoutUser.setOnClickListener {
            if(DigikofySession.exists(requireActivity().applicationContext)) {
                viewModel.revoke(DigikofySession.getRefreshToken(requireActivity().applicationContext)!!)
            }
            DigikofySession.clear(requireActivity().applicationContext)

            Toast.makeText(
                requireActivity().applicationContext,
                "Déconnecté avec succès",
                Toast.LENGTH_SHORT
            ).show()

            view?.findNavController()?.popBackStack(R.id.loginFragment, false)
            //.navigate(
            //    UserFragmentDirections.actionUserFragmentToLoginFragment()
            //)
        }

        btnDeleteUser.setOnClickListener {
            viewModel.delete(requireActivity().applicationContext)

            viewModel.userResponseSuccess.observe(viewLifecycleOwner, {
                DigikofySession.clear(requireActivity().applicationContext)

                Toast.makeText(
                    requireActivity().applicationContext,
                    "Compte supprimé avec succès",
                    Toast.LENGTH_LONG
                ).show()

                view?.findNavController()?.popBackStack(R.id.loginFragment, false)
                //view?.findNavController()?.navigate(
                //    UserFragmentDirections.actionUserFragmentToLoginFragment()
                //)
            })

            viewModel.userResponseError.observe(viewLifecycleOwner, {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Erreur lors de la suppression du comtpe",
                    Toast.LENGTH_LONG
                ).show()
            })

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}