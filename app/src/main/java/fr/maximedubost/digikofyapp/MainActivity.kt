package fr.maximedubost.digikofyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

import fr.maximedubost.digikofyapp.databinding.ActivityMainBinding
import fr.maximedubost.digikofyapp.databinding.MainFragmentBinding
import fr.maximedubost.digikofyapp.repositories.BaseRepository
import fr.maximedubost.digikofyapp.repositories.HomeRepository
import fr.maximedubost.digikofyapp.repositories.MachineRepository
import fr.maximedubost.digikofyapp.repositories.PreparationRepository
import fr.maximedubost.digikofyapp.ui.home.HomeFragment
import fr.maximedubost.digikofyapp.ui.machine.MachineFragment
import fr.maximedubost.digikofyapp.ui.preparation.PreparationFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

}