package fr.maximedubost.digikofyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import fr.maximedubost.digikofyapp.fragments.HomeFragment
import fr.maximedubost.digikofyapp.fragments.MachineFragment
import fr.maximedubost.digikofyapp.fragments.PreparationFragment
import fr.maximedubost.digikofyapp.repositories.BaseRepository
import fr.maximedubost.digikofyapp.repositories.HomeRepository
import fr.maximedubost.digikofyapp.repositories.MachineRepository
import fr.maximedubost.digikofyapp.repositories.PreparationRepository
import kotlinx.android.synthetic.main.fragment_machine.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Chargement du fragment par défaut
        loadFragment(HomeFragment(), HomeRepository())

        // Import de la BottomNavigationView
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        val radius = resources.getDimension(R.dimen.short_dimen);

        val bottomBarBackground: MaterialShapeDrawable =
            navigationView.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground
            .shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()

        // Sélection de l'item "Menu" par défaut
        navigationView.menu.findItem(R.id.home_page).isChecked = true

        // Chargement du fragment en fonction de la BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.machine_page -> {
                    loadFragment(MachineFragment(), MachineRepository())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.home_page -> {
                    loadFragment(HomeFragment(), HomeRepository())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.coffee_page -> {
                    loadFragment(PreparationFragment(), PreparationRepository())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    /**
     * Chargement du fragment
     *
     * @param fragment Fragment à charger
     */
    private fun loadFragment(fragment: Fragment, repository: BaseRepository) {
        // Mise à jour des éléments affichés
        repository.updateData {
            // Injection du fragment
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}