package fr.maximedubost.digikofyapp.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.HomeFragmentBinding
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.ui.machine.MachineFragment
import fr.maximedubost.digikofyapp.ui.main.MainFragment
import fr.maximedubost.digikofyapp.ui.main.MainFragmentDirections
import fr.maximedubost.digikofyapp.ui.preparation.PreparationFragment
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class HomeFragment(private val mainFragment: MainFragment, private val menu: Menu) : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)

        binding.cvNextPreparation.visibility = View.GONE
        binding.tvNextPreparations.visibility = View.INVISIBLE
        binding.tvNoNextPreparation.visibility = View.GONE
        binding.tvErrorNextPreparation.visibility = View.GONE
        binding.cvLastPreparation.visibility = View.GONE
        binding.tvPastPreparations.visibility = View.INVISIBLE
        binding.tvNoLastPreparation.visibility = View.GONE
        binding.tvErrorLastPreparation.visibility = View.GONE

        binding.btnNewPreparation.setOnClickListener {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToCreatePreparationFragment()
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.findNext(requireActivity().applicationContext)

        viewModel.preparationFindNextResponseSuccess.observe(viewLifecycleOwner, {
            Log.d("FindNext", "OK")

            val nextPreparation: PreparationModel? =
                if(it.data.body() == null)
                    null
                else
                    it.data.body()!!

            binding.loadingNextPreparation.visibility = View.GONE

            if(nextPreparation == null) {
                binding.tvNoNextPreparation.visibility = View.VISIBLE
            }
            else {
                binding.tvNextPreparationCoffee.visibility =
                    if (nextPreparation.name == null) View.GONE
                    else View.VISIBLE
                binding.cvNextPreparation.visibility = View.VISIBLE
                binding.tvNextPreparations.visibility = View.VISIBLE
                binding.tvNextPreparationName.text =
                    if (nextPreparation.name == null) nextPreparation.coffee!!.name
                    else nextPreparation.name
                binding.tvNextPreparationCoffee.text = nextPreparation.coffee!!.name
                binding.tvNextPreparationWhen.text = StringDateTimeFormatter
                    .durationBetweenNowAnd(nextPreparation.nextTime!!)

                binding.cvNextPreparation.setOnClickListener {
                    view.findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToPreparationDetailsFragment(nextPreparation.id!!)
                    )
                }

                binding.tvNextPreparations.setOnClickListener {
                    menu.findItem(R.id.coffee_page).isChecked = true
                    mainFragment.updateView(R.id.coffee_page)
                    mainFragment.loadFragment(PreparationFragment(nextPreparationsTab = true))
                }
            }
        })

        viewModel.preparationFindNextResponseError.observe(viewLifecycleOwner, {
            Log.d("FindNext", "Nope")
            binding.tvErrorNextPreparation.visibility = View.VISIBLE
            binding.loadingNextPreparation.visibility = View.GONE
        })




        viewModel.findLast(requireActivity().applicationContext)

        viewModel.preparationFindLastResponseSuccess.observe(viewLifecycleOwner, {
            Log.d("FindLast", "OK")

            val lastPreparation: PreparationModel? =
                if(it.data.body() == null)
                    null
                else
                    it.data.body()!!

            binding.loadingLastPreparation.visibility = View.GONE

            if(lastPreparation == null) {
                binding.tvNoLastPreparation.visibility = View.VISIBLE
            }
            else {
                binding.tvLastPreparationCoffee.visibility =
                    if (lastPreparation.name == null) View.GONE
                    else View.VISIBLE
                binding.cvLastPreparation.visibility = View.VISIBLE
                binding.tvPastPreparations.visibility = View.VISIBLE
                binding.tvLastPreparationName.text =
                    if (lastPreparation.name == null) lastPreparation.coffee!!.name
                    else lastPreparation.name
                binding.tvLastPreparationCoffee.text = lastPreparation.coffee!!.name
                binding.tvLastPreparationWhen.text = StringDateTimeFormatter
                    .durationBetweenNowAnd(lastPreparation.lastTime!!, true)


                binding.cvLastPreparation.setOnClickListener {
                    view.findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToPreparationDetailsFragment(lastPreparation.id!!)
                    )
                }

                binding.tvPastPreparations.setOnClickListener {
                    menu.findItem(R.id.coffee_page).isChecked = true
                    mainFragment.updateView(R.id.coffee_page)
                    mainFragment.loadFragment(PreparationFragment(pastPreparationsTab = true))
                }

            }
        })

        viewModel.preparationFindLastResponseError.observe(viewLifecycleOwner, {
            Log.d("FindLast", "Nope")
            binding.tvErrorLastPreparation.visibility = View.VISIBLE
            binding.loadingLastPreparation.visibility = View.GONE
        })

    }

}