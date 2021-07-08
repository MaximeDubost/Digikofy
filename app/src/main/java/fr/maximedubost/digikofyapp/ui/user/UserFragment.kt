package fr.maximedubost.digikofyapp.ui.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("USER INFO >>>>>>>>", DigikofySession.toMap(requireActivity().applicationContext).toString())

        binding  = UserFragmentBinding.inflate(inflater)

        val ivBack = binding.ivBack
        val btnLogoutUser = binding.btnLogoutUser
        val btnDeleteUser = binding.btnDeleteUser

        val test1 = binding.test1
        val test2 = binding.test2
        val test3 = binding.test3
        val test4 = binding.test4

        test1.text = DigikofySession.getEmail(requireActivity().applicationContext)
        test2.text = DigikofySession.getIdToken(requireActivity().applicationContext)?.substring(0, 16)
        test3.text = DigikofySession.getRefreshToken(requireActivity().applicationContext)?.substring(0, 16)
        test4.text = DigikofySession.exists(requireActivity().applicationContext).toString()

        Log.d("TOKEN >>>>>>>>", DigikofySession.getIdToken(requireActivity().applicationContext)?.substring(0, 16).toString())

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

}