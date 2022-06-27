package id.finalproject.binar.secondhand

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.finalproject.binar.secondhand.databinding.ActivityMainBinding
import id.finalproject.binar.secondhand.fragment.account.ProfilFragment
import id.finalproject.binar.secondhand.fragment.home.HomeFragment
import id.finalproject.binar.secondhand.fragment.notification.NotificationFragment
import id.finalproject.binar.secondhand.fragment.sell.DaftarJualFragment
import id.finalproject.binar.secondhand.fragment.sell.FormJualFragment
import id.finalproject.binar.secondhand.model.network.Resource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setupWithNavController(bottomNavigationView, navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
                R.id.registerFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
                R.id.bidderInfoFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
                R.id.formJualFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
                R.id.previewFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        doubleBackToExit()

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // put your code here
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.notification -> {
                    // put your code here
                    val fragment = NotificationFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.formJual -> {
                    // put your code here
                    val fragment = FormJualFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.daftarJual -> {
                    // put your code here
                    val fragment = DaftarJualFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profil -> {
                    val fragment = ProfilFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                    // put your code here
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun doubleBackToExit() {
        var doubleBackPressed: Long = 0
        val toast = Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT)
        this@MainActivity.onBackPressedDispatcher.addCallback(this) {
            if (doubleBackPressed + 2000 > System.currentTimeMillis()) {
                finishAffinity()
                finish()
                toast.cancel()
            } else {
                toast.show()
            }
            doubleBackPressed = System.currentTimeMillis()
        }
    }
}