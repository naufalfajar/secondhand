package id.finalproject.binar.secondhand

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.finalproject.binar.secondhand.databinding.ActivityBuyerBinding
import id.finalproject.binar.secondhand.fragment.home.ItemDetailFragment

@AndroidEntryPoint
class BuyerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            val bundle = intent.extras

            val fragment: Fragment = ItemDetailFragment()
            val transaction = supportFragmentManager.beginTransaction()
            fragment.arguments = bundle
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.commit()
        }
    }
}