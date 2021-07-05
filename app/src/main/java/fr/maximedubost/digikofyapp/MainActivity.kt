package fr.maximedubost.digikofyapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
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

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext

        Log.d(">>>>>>>>>>>>> Create", "Create")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if(DigikofySession.exists(applicationContext)) {
            viewModel.refreshToken(DigikofySession.getRefreshToken(applicationContext)!!)

            viewModel.mainResponseSuccess.observe(this, {
                if(it.data.body()?.idToken.isNullOrBlank()) {
                    DigikofySession.setIdToken(applicationContext, it.data.body()!!.idToken)
                }
                if(it.data.body()?.refreshToken.isNullOrBlank()) {
                    DigikofySession.setRefreshToken(applicationContext, it.data.body()!!.refreshToken)
                }
            })

            viewModel.mainResponseError.observe(this, {
                if(!DigikofySession.getRefreshToken(applicationContext).isNullOrBlank()) {
                    userViewModel.revoke(DigikofySession.getRefreshToken(applicationContext)!!)
                }
                DigikofySession.clear(applicationContext)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(">>>>>>>>>>>>> Resume", "Resume")
        if(DigikofySession.exists(applicationContext)) {
            viewModel.refreshToken(DigikofySession.getRefreshToken(applicationContext)!!)

            viewModel.mainResponseSuccess.observe(this, {
                if(it.data.body()?.idToken.isNullOrBlank()) {
                    DigikofySession.setIdToken(applicationContext, it.data.body()!!.idToken)
                }
                if(it.data.body()?.refreshToken.isNullOrBlank()) {
                    DigikofySession.setRefreshToken(applicationContext, it.data.body()!!.refreshToken)
                }
            })

            viewModel.mainResponseError.observe(this, {
                if(!DigikofySession.getRefreshToken(applicationContext).isNullOrBlank()) {
                    userViewModel.revoke(DigikofySession.getRefreshToken(applicationContext)!!)
                }
                DigikofySession.clear(applicationContext)
            })
        }

    }

    override fun onRestart() {
        super.onRestart()
        Log.d(">>>>>>>>>>>>> Restart", "Restart")
        if(DigikofySession.exists(applicationContext)) {
            viewModel.refreshToken(DigikofySession.getRefreshToken(applicationContext)!!)

            viewModel.mainResponseSuccess.observe(this, {
                if(it.data.body()?.idToken.isNullOrBlank()) {
                    DigikofySession.setIdToken(applicationContext, it.data.body()!!.idToken)
                }
                if(it.data.body()?.refreshToken.isNullOrBlank()) {
                    DigikofySession.setRefreshToken(applicationContext, it.data.body()!!.refreshToken)
                }
            })

            viewModel.mainResponseError.observe(this, {
                if(!DigikofySession.getRefreshToken(applicationContext).isNullOrBlank()) {
                    userViewModel.revoke(DigikofySession.getRefreshToken(applicationContext)!!)
                }
                DigikofySession.clear(applicationContext)
            })
        }
    }
}