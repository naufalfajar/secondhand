package id.fajarjudo.binar.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*

class home : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
        val profilfragment=profil()
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.person->setCurrentFragment(profilfragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment:Fragment)=
        childFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}
