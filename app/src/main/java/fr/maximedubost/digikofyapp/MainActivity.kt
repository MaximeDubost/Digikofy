package fr.maximedubost.digikofyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.maximedubost.digikofyapp.fragments.HomeFragment
import fr.maximedubost.digikofyapp.fragments.MachineFragment
import fr.maximedubost.digikofyapp.fragments.PreparationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        // Import de la BottomNavigationView
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        // Sélection de l'item "Menu" par défaut
        navigationView.menu.findItem(R.id.home_page).isChecked = true

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.machine_page -> {
                    loadFragment(MachineFragment(this), R.string.machine_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.coffee_page -> {
                    loadFragment(PreparationFragment(this), R.string.coffee_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment, stringResource: Int) {
        // Chargement du repository
        // val homeRepository = HomeRepository()

        // Actualisation du titre de la page
        val pageTitle = findViewById<TextView>(R.id.page_title)
        pageTitle.text = resources.getString(stringResource)
        when(stringResource) {
            R.string.machine_page_title, R.string.coffee_page_title -> pageTitle.textSize = 32.0F
            R.string.home_page_title -> pageTitle.textSize = 36.0F
        }

        // Mise à jour des éléments affichés
        // homeRepository.updateData {
            // Injection du fragment dans la boîte
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        // }
    }

}