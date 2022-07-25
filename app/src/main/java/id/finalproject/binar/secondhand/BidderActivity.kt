package id.finalproject.binar.secondhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import id.finalproject.binar.secondhand.databinding.ActivityBidderBinding
import id.finalproject.binar.secondhand.fragment.notification.BidderInfoFragment

class BidderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBidderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBidderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            val bundle = intent.extras

            val fragment: Fragment = BidderInfoFragment()
            val transaction = supportFragmentManager.beginTransaction()
            fragment.arguments = bundle
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.commit()
        }

    }
}