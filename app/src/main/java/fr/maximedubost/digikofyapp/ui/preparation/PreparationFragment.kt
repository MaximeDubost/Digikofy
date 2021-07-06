package fr.maximedubost.digikofyapp.ui.preparation

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.adapters.PreparationAdapter
import fr.maximedubost.digikofyapp.databinding.PreparationFragmentBinding
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.oldrepositories.PreparationRepository
import fr.maximedubost.digikofyapp.ui.main.MainFragmentDirections

class PreparationFragment : Fragment() {

    companion object {
        fun newInstance() = PreparationFragment()
    }

    private lateinit var viewModel: PreparationViewModel
    private lateinit var binding: PreparationFragmentBinding

    fun onPreparationSelected(): Any = { preparationId: String ->
        view?.findNavController()?.navigate(
            MainFragmentDirections.actionMainFragmentToPreparationDetailsFragment(preparationId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(PreparationViewModel::class.java)
        binding = PreparationFragmentBinding.inflate(inflater)

        /*
         * binding :
         * - loading
         * - lavNoPreparation
         * - tvNoPreparation
         * - rvPreparations
         * - tvErrorPreparation
         */
        binding.loading.visibility = View.VISIBLE
        binding.lavNoPreparation.visibility = View.GONE
        binding.tvNoPreparation.visibility = View.GONE
        binding.tvErrorPreparation.visibility = View.GONE
        binding.llPreparationsNavbar.visibility = View.INVISIBLE

        viewModel.findAll(requireActivity().applicationContext)

        viewModel.preparationFindAllResponseSuccess.observe(viewLifecycleOwner, {
            Log.d("SUCCESS >>>>>>>>", it.data.body().toString())

            binding.rvPreparations.adapter = PreparationAdapter({ preparationId ->
                view?.findNavController()?.navigate(
                    MainFragmentDirections.actionMainFragmentToPreparationDetailsFragment(preparationId)
                )
            }, it.data.body()!!
                .filter { preparation -> preparation.saved }
                .sortedBy { preparation -> preparation.name },
                isSavedPreparationsPage = true,
                context = this.activity as MainActivity
            )

            binding.loading.visibility = View.GONE
            binding.lavNoPreparation.visibility = if (it.data.body()!!.isEmpty()) View.VISIBLE else View.GONE
            binding.tvNoPreparation.visibility = if (it.data.body()!!.isEmpty()) View.VISIBLE else View.GONE
            binding.tvErrorPreparation.visibility = View.GONE

            initNavBar(it.data.body()!!)
        })

        viewModel.preparationFindAllResponseError.observe(viewLifecycleOwner, {
            Log.d("ERROR >>>>>>>>", it.toString())
            binding.rvPreparations.adapter = PreparationAdapter(
                {},
                listOf(),
                isSavedPreparationsPage = true,
                context = this.activity as MainActivity
            )

            binding.loading.visibility = View.GONE
            binding.lavNoPreparation.visibility = View.GONE
            binding.tvNoPreparation.visibility = View.GONE
            binding.tvErrorPreparation.visibility = View.VISIBLE
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PreparationViewModel::class.java)
        // TODO: Use the ViewModel
    }

    /**
     * initNavBar
     */
    private fun initNavBar(preparations: List<PreparationModel>) {
        binding.llPreparationsNavbar.visibility = View.VISIBLE
        binding.tvSavedPreparationsTab.setTextColor(Color.BLACK)

        /*
         * Saved preparations tab
         */
        binding.tvSavedPreparationsTab.setOnClickListener {
            binding.tvSavedPreparationsTab.setTextColor(Color.BLACK)
            binding.tvNextPreparationsTab.setTextColor(Color.GRAY)
            binding.tvPastPreparationsTab.setTextColor(Color.GRAY)
            binding.rvPreparations.adapter = PreparationAdapter({ preparationId ->
                    view?.findNavController()?.navigate(
                        MainFragmentDirections.actionMainFragmentToPreparationDetailsFragment(preparationId)
                    )
                },
                preparations.filter { it.saved }.sortedBy { it.name },
                isSavedPreparationsPage = true,
                context = this.activity as MainActivity
            )
        }

        /*
         * Oncoming preparations tab
         */
        binding.tvNextPreparationsTab.setOnClickListener {
            binding.tvSavedPreparationsTab.setTextColor(Color.GRAY)
            binding.tvNextPreparationsTab.setTextColor(Color.BLACK)
            binding.tvPastPreparationsTab.setTextColor(Color.GRAY)
            binding.rvPreparations.adapter = PreparationAdapter(
                {}, // TODO : Demander la confirmation du lancemenent de la préparation
                preparations.filter { it.isFuturePreparation() }.sortedBy { it.nextTime },
                isNextPreparationsPage = true,
                context = this.activity as MainActivity
            )
        }

        /*
         * Past preparations tab
         */
        binding.tvPastPreparationsTab.setOnClickListener {
            binding.tvSavedPreparationsTab.setTextColor(Color.GRAY)
            binding.tvNextPreparationsTab.setTextColor(Color.GRAY)
            binding.tvPastPreparationsTab.setTextColor(Color.BLACK)
            binding.rvPreparations.adapter = PreparationAdapter(
                {}, // TODO : Demander la confirmation du lancemenent de la préparation
                preparations.filter { it.lastTime != null }.sortedByDescending { it.lastTime },
                isPastPreparationsPage = true,
                context = this.activity as MainActivity
            )
        }


    }

}