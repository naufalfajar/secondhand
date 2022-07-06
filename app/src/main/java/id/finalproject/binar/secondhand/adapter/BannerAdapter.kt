package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemBannerBinding
import id.finalproject.binar.secondhand.model.network.response.seller.GetBanner
import id.finalproject.binar.secondhand.model.network.response.seller.GetBannerItem


class BannerAdapter(private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetBannerItem>() {
        override fun areItemsTheSame(
            oldItem: GetBannerItem,
            newItem: GetBannerItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetBannerItem,
            newItem: GetBannerItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(banner: GetBanner?) = listDiffer.submitList(banner)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
//        return BannerViewHolder(view)
        val binding =
            ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
        if (position == listDiffer.currentList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class BannerViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetBannerItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(ivBanner)
            }
        }
    }

//    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.iv_banner);
//    }

    private val runnable = Runnable {
        listDiffer.currentList.addAll(listDiffer.currentList)
        notifyDataSetChanged()
    }
}