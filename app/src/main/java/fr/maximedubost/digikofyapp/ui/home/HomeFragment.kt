package fr.maximedubost.digikofyapp.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.maximedubost.digikofyapp.databinding.HomeFragmentBinding
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter

class HomeFragment : Fragment() {
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
        binding.tvLastPreparations.visibility = View.INVISIBLE
        binding.tvNoLastPreparation.visibility = View.GONE
        binding.tvErrorLastPreparation.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.findNext(requireActivity().applicationContext)

        viewModel.preparationFindNextResponseSuccess.observe(viewLifecycleOwner, {
            Log.d("FindNext", "OK")

            binding.loadingNextPreparation.visibility = View.GONE

            if(it.data.body() == null) {
                binding.tvNoNextPreparation.visibility = View.VISIBLE
            }
            else {
                if (it.data.body()!!.name == null)
                    binding.tvNextPreparationCoffee.visibility = View.GONE
                else
                    binding.tvNextPreparationCoffee.visibility = View.VISIBLE

                binding.cvNextPreparation.visibility = View.VISIBLE
                binding.tvNextPreparations.visibility = View.VISIBLE
                binding.tvNextPreparationName.text =
                    if (it.data.body()!!.name == null) it.data.body()!!.coffee!!.name
                    else it.data.body()!!.name
                binding.tvNextPreparationCoffee.text = it.data.body()!!.coffee!!.name
                binding.tvNextPreparationWhen.text = StringDateTimeFormatter.durationBetweenNowAnd(it.data.body()!!.nextTime!!)
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

            binding.loadingLastPreparation.visibility = View.GONE

            if(it.data.body() == null) {
                binding.tvNoLastPreparation.visibility = View.VISIBLE
            }
            else {
                if (it.data.body()!!.name == null)
                    binding.tvLastPreparationCoffee.visibility = View.GONE
                else
                    binding.tvLastPreparationCoffee.visibility = View.VISIBLE

                binding.cvLastPreparation.visibility = View.VISIBLE
                binding.tvLastPreparations.visibility = View.VISIBLE
                binding.tvLastPreparationName.text =
                    if (it.data.body()!!.name == null) it.data.body()!!.coffee!!.name
                    else it.data.body()!!.name
                binding.tvLastPreparationCoffee.text = it.data.body()!!.coffee!!.name
                binding.tvLastPreparationWhen.text = StringDateTimeFormatter.durationBetweenNowAnd(it.data.body()!!.lastTime!!, true)
            }
        })

        viewModel.preparationFindLastResponseError.observe(viewLifecycleOwner, {
            Log.d("FindLast", "Nope")
            binding.tvErrorLastPreparation.visibility = View.VISIBLE
            binding.loadingLastPreparation.visibility = View.GONE
        })

    }

}