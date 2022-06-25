package id.finalproject.binar.secondhand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SwipeImageAdapter(private var images: List<Int>)
    : RecyclerView.Adapter<SwipeImageAdapter.ImageViewHolder>(){

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemImage: ImageView = itemView.findViewById(R.id.iv_swipe_image)
//        val  itemDesc: TextView = itemView.findViewById(R.id.tv_deskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_swipe_image, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}