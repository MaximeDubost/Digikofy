package fr.maximedubost.digikofyapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import fr.maximedubost.digikofyapp.R
import fr.maximedubost.digikofyapp.databinding.MainFragmentBinding
import fr.maximedubost.digikofyapp.ui.home.HomeFragment
import fr.maximedubost.digikofyapp.ui.machine.MachineFragment
import fr.maximedubost.digikofyapp.ui.preparation.PreparationFragment
import fr.maximedubost.digikofyapp.ui.preparation.PreparationViewModel

class MainFragment : Fragment() {
    private lateinit var viewModel: PreparationViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)

        binding.tvMainPageTitle.text = resources.getString(R.string.home_page_title)
        binding.ivMainPageAction.setOnClickListener {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToUserFragment()
            )
        }

        loadFragment(HomeFragment(this, binding.bnvHome.menu))
        createBottomNavigationBar(binding.bnvHome)

        // Sélection de l'item "Menu" par défaut
        binding.bnvHome.menu.findItem(R.id.home_page).isChecked = true

        binding.bnvHome.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.machine_page -> {
                    updateView(R.id.machine_page)
                    loadFragment(MachineFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_page -> {
                    updateView(R.id.home_page)
                    loadFragment(HomeFragment(this, binding.bnvHome.menu))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.coffee_page -> {
                    updateView(R.id.coffee_page)
                    loadFragment(PreparationFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PreparationViewModel::class.java)
    }

    /**
     * Update element of the view
     *
     * @param menuItemId id of view's menu item
     */
    internal fun updateView(menuItemId: Int) {
        when(menuItemId) {
            R.id.machine_page -> {
                binding.tvMainPageTitle.text = resources.getString(R.string.machine_page_title)
                binding.ivMainPageAction.setOnClickListener {
                    Log.d("######## MainFragment", "actionMainFragmentToCreateMachineFragment()")
                    view?.findNavController()?.navigate(
                        MainFragmentDirections.actionMainFragmentToCreateMachineFragment()
                    )
                }
                binding.ivMainPageAction.setImageDrawable(AppCompatResources.getDrawable(
                    requireActivity().applicationContext, R.drawable.ic_add
                ))
            }

            R.id.home_page -> {
                binding.tvMainPageTitle.text = resources.getString(R.string.home_page_title)
                binding.ivMainPageAction.setOnClickListener {
                    view?.findNavController()?.navigate(
                        MainFragmentDirections.actionMainFragmentToUserFragment()
                    )
                }
                binding.ivMainPageAction.setImageDrawable(AppCompatResources.getDrawable(
                    requireActivity().applicationContext, R.drawable.ic_settings
                ))
            }

            R.id.coffee_page -> {
                binding.tvMainPageTitle.text = resources.getString(R.string.preparation_page_title)
                binding.ivMainPageAction.setOnClickListener {
                    // TODO : Define header button action
                }
                binding.ivMainPageAction.setImageDrawable(AppCompatResources.getDrawable(
                    requireActivity().applicationContext, R.drawable.ic_add
                ))
            }
        }
    }

    /**
     * Fragment loading
     *
     * @param fragment Fragment to load
     */
    internal fun loadFragment(fragment: Fragment) {
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Create the bottom navigation bar
     *
     * @param navigationView navigationView
     */
    private fun createBottomNavigationBar(navigationView: BottomNavigationView) {
        val radius = resources.getDimension(R.dimen.short_dimen);
        val bottomBarBackground: MaterialShapeDrawable =
            navigationView.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground
            .shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()
    }

    private fun goToPreparationsTab() {

    }

}