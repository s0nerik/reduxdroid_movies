package com.github.s0nerik.reduxdroid_movies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.s0nerik.reduxdroid.activity_result.ActivityResultMiddleware
import com.github.s0nerik.reduxdroid.navigation.middleware.NavigationMiddleware
import com.github.s0nerik.reduxdroid.state_serializer.AppStateSerializer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppActivity : AppCompatActivity() {
    private val vm: AppViewModel by viewModel()

    private val navMiddleware: NavigationMiddleware by inject()

    private val stateSerializer: AppStateSerializer by inject()

    private val activityResultMiddleware: ActivityResultMiddleware by inject()

    private val navCtrl: NavController
        get() = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!.findNavController()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityResultMiddleware.attach(this)
        navMiddleware.attachNavController(navCtrl)

        if (BuildConfig.DEBUG) {
            stateSerializer.debugMode = true
        }

        if (savedInstanceState != null)
            stateSerializer.restore()

        if (savedInstanceState == null)
            vm.ensureLoggedIn()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        stateSerializer.save()
    }

    override fun onDestroy() {
        navMiddleware.detachNavController(navCtrl)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultMiddleware.notifyResult(requestCode, resultCode, data)
    }
}
