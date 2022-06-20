//package id.fajarjudo.binar.login
//
//import android.content.Context
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import id.fajarjudo.binar.login.databinding.FragmentSplashScreenBinding
//import kotlinx.coroutines.delay
//
//class SplashScreen : Fragment() {
//    private var _binding: FragmentSplashScreenBinding? = null
//    private val binding get()= _binding!!
//    private lateinit var  sharedPref: SharedPref
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        sharedPref= SharedPref(context)
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launchWhenCreated {
//            splashtologin()
//        }
//    }
//
////    private suspend fun splashtologin(){
////        delay(3000)
////        if (sharedPref.isLogin()){
////            findNavController().navigate(SplashScreenDirections.actionSplashScreenToHomeFragment())
////        }
////        else {
////            findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginFragment())
////        }
////    }
//
//}