package id.finalproject.binar.secondhand

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import id.finalproject.binar.secondhand.databinding.ActivityMainBinding
import id.finalproject.binar.secondhand.fragment.account.ProfilFragment
import id.finalproject.binar.secondhand.fragment.home.HomeFragment
import id.finalproject.binar.secondhand.fragment.notification.NotificationFragment
import id.finalproject.binar.secondhand.fragment.sell.DaftarJualFragment

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
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        setupWithNavController(bottomNavigationView, navController)
//
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
//                R.id.registerFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
//                R.id.bidderInfoFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
//                R.id.formJualFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
//                R.id.previewFragment -> binding.bottomNavigationView.visibility = View.INVISIBLE
//                else -> binding.bottomNavigationView.visibility = View.VISIBLE
//            }
//        }

//        setupBottomNavigationBar()
        doubleBackToExit()

        if (intent.extras != null) {
            val bundle = intent.extras
            snackbarAddProduct(bundle!!.getBoolean("addProduct"))
            bundle.remove("addProduct")
        }

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> {
                        val fragment = HomeFragment()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragmentContainerView,
                            fragment,
                            fragment.javaClass.simpleName
                        )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.notificationFragment -> {
                        val fragment = NotificationFragment()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragmentContainerView,
                            fragment,
                            fragment.javaClass.simpleName
                        )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.formJualFragment -> {
                        val intent = Intent(this, SellActivity::class.java)
                        startActivity(intent)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.daftarJualFragment -> {
                        val fragment = DaftarJualFragment()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragmentContainerView,
                            fragment,
                            fragment.javaClass.simpleName
                        )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.profilFragment2 -> {
                        val fragment = ProfilFragment()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragmentContainerView,
                            fragment,
                            fragment.javaClass.simpleName
                        )
                            .commit()
                        return@OnNavigationItemSelectedListener true
                    }
            }
            false
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
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

    private fun snackbarAddProduct(addProduct: Boolean) {
        if (addProduct) {

            val position = binding.myCoordinatorLayout
            val layout = binding.mainActivity

            val customSnackbar = LayoutInflater.from(this).inflate(R.layout.custom_snackbar, null)
            val snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackbarLayout.setPadding(0, 0, 0, 0)

            customSnackbar.findViewById<ImageButton>(R.id.btn_snackbar_close).setOnClickListener {
                snackbar.dismiss()
            }
            snackbarLayout.addView(customSnackbar)
            snackbar.anchorView = position
            snackbar.show()
        }
    }
}