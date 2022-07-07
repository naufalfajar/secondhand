package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.ItemBannerBinding
import id.finalproject.binar.secondhand.model.local.entity.Banner

class HomeBannerAdapter :
    ListAdapter<Banner, HomeBannerAdapter.BannerViewHolder>(BannerComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding =
            ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class BannerViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: Banner) {
            binding.apply {
                if (banner.image_url != null) {
                    Glide.with(itemView.context)
                        .load(banner.image_url)
                        .into(ivBanner)
                } else {
                    ivBanner.setImageResource(R.drawable.ic_launcher_background)
                }
            }
        }
    }

    class BannerComparator : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner) =
            oldItem.image_url == newItem.image_url

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner) =
            oldItem == newItem
    }

}