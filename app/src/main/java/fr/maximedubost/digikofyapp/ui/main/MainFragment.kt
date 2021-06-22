package fr.maximedubost.digikofyapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import fr.maximedubost.digikofyapp.oldrepositories.BaseRepository
import fr.maximedubost.digikofyapp.oldrepositories.HomeRepository
import fr.maximedubost.digikofyapp.oldrepositories.MachineRepository
import fr.maximedubost.digikofyapp.oldrepositories.PreparationRepository
import fr.maximedubost.digikofyapp.ui.home.HomeFragment
import fr.maximedubost.digikofyapp.ui.machine.MachineFragment
import fr.maximedubost.digikofyapp.ui.preparation.PreparationFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d("######## MainFragment", "onCreateView()")

        binding = MainFragmentBinding.inflate(inflater)

        val tvMainPageTitle = binding.tvMainPageTitle
        val ivMainPageAction = binding.ivMainPageAction

        tvMainPageTitle.text = resources.getString(R.string.home_page_title)
        ivMainPageAction.setOnClickListener {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToUserFragment()
            )
        }

        // Chargement du fragment par défaut
        loadFragment(HomeFragment(), HomeRepository())

        // Import de la BottomNavigationView
        val navigationView = binding.navigationView

        // Définition du style de la navbar
        defineBottomNavigationBar(navigationView)

        // Sélection de l'item "Menu" par défaut
        navigationView.menu.findItem(R.id.home_page).isChecked = true

        // Chargement du fragment en fonction de la BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.machine_page -> {
                    tvMainPageTitle.text = resources.getString(R.string.machine_page_title)
                    ivMainPageAction.setOnClickListener {
                        Log.d("######## MainFragment", "actionMainFragmentToCreateMachineFragment()")
                        view?.findNavController()?.navigate(
                            MainFragmentDirections.actionMainFragmentToCreateMachineFragment()
                        )
                    }
                    ivMainPageAction.setImageDrawable(AppCompatResources.getDrawable(
                        requireActivity().applicationContext, R.drawable.ic_add
                    ))
                    loadFragment(MachineFragment(), MachineRepository())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_page -> {
                    tvMainPageTitle.text = resources.getString(R.string.home_page_title)
                    ivMainPageAction.setOnClickListener {
                        view?.findNavController()?.navigate(
                            MainFragmentDirections.actionMainFragmentToUserFragment()
                        )
                    }
                    ivMainPageAction.setImageDrawable(AppCompatResources.getDrawable(
                        requireActivity().applicationContext, R.drawable.ic_user
                    ))
                    loadFragment(HomeFragment(), HomeRepository())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.coffee_page -> {
                    tvMainPageTitle.text = resources.getString(R.string.preparation_page_title)
                    ivMainPageAction.setOnClickListener {
                        // TODO : Define header button action
                    }
                    ivMainPageAction.setImageDrawable(AppCompatResources.getDrawable(
                        requireActivity().applicationContext, R.drawable.ic_more
                    ))
                    loadFragment(PreparationFragment(), PreparationRepository())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }


    /**
     * Chargement du fragment
     *
     * @param fragment Fragment à charger
     */
    private fun loadFragment(fragment: Fragment, repository: BaseRepository) {
        Log.d("######## MainFragment", "loadFragment()")
        // Mise à jour des éléments affichés
        // repository.updateData {
            //Injection du fragment
            val transaction = (context as FragmentActivity)
                .supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        // }
    }

    /**
     * defineBottomNavigationBar
     */
    private fun defineBottomNavigationBar(navigationView: BottomNavigationView) {
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

}