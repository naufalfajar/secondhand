package id.finalproject.binar.secondhand.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.R
import java.util.*

class BannerAdapter(val context: Context, private val imageList: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View =
            mLayoutInflater.inflate(R.layout.item_corousel_banner, container, false)

        val imageView: ImageView = itemView.findViewById<View>(R.id.iv_banner) as ImageView

        imageList[position].let {
            Glide.with(context)
                .load(it)
                .into(imageView)
        }

        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as RelativeLayout)
    }
}

//class BannerAdapter(private val context: Context, private var imageList: ArrayList<String>) : PagerAdapter() {
//    override fun getCount(): Int {
//        return imageList.size
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view === `object`
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.item_corousel_banner, null)
//        val ivImages = view.findViewById<ImageView>(R.id.iv_banner)
//
//        imageList[position].let {
//            Glide.with(context)
//                .load(it)
//                .into(ivImages);
//        }
//
//
//        val vp = container as ViewPager
//        vp.addView(view, 0)
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val vp = container as ViewPager
//        val view = `object` as View
//        vp.removeView(view)
//    }
//}