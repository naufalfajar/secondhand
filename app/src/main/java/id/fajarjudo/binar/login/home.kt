package id.fajarjudo.binar.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import id.fajarjudo.binar.login.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_home.*

class home : Fragment(R.layout.fragment_home) {
    private lateinit var binding: ActivityMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val firstFragment=home()
//        val profilfragment=profilFragment()
//        setCurrentFragment(firstFragment)
//        bottomNavigationView.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.person->setCurrentFragment(profilfragment)
//            }
//            true
//        }
//        val navHostFragment =
//            childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController

//        binding.bottom.setupWithNavController(navController)
//
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.login -> binding.bottomNavigationView.visibility = View.INVISIBLE
//                R.id.register ->binding.bottomNavigationView.visibility = View.INVISIBLE
//                R.id.changeAcc ->binding.bottomNavigationView.visibility = View.INVISIBLE
//                else -> binding.bottomNavigationView.visibility = View.VISIBLE
//            }
//        }
//
    }

    private fun setCurrentFragment(fragment:Fragment)=
        childFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }

