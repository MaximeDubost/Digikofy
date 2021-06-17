package fr.maximedubost.digikofyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import fr.maximedubost.digikofyapp.databinding.ActivityMainBinding
import fr.maximedubost.digikofyapp.session.DigikofySession
import fr.maximedubost.digikofyapp.ui.main.MainViewModel
import fr.maximedubost.digikofyapp.ui.user.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if(DigikofySession.exists(applicationContext)) {
            viewModel.refreshToken(DigikofySession.getRefreshToken(applicationContext)!!)

            viewModel.mainResponseSuccess.observe(this, {
                DigikofySession.setIdToken(applicationContext, it.data.body()!!.idToken)
                DigikofySession.setRefreshToken(applicationContext, it.data.body()!!.refreshToken)
            })

            viewModel.mainResponseError.observe(this, {
                if(DigikofySession.getRefreshToken(applicationContext) != null) {
                    userViewModel.revoke(DigikofySession.getRefreshToken(applicationContext)!!)
                }
                DigikofySession.clear(applicationContext)
            })
        }

    }

}