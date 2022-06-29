package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemProductHomeBinding
import id.finalproject.binar.secondhand.model.network.response.buyer.GetBuyerProduct
import id.finalproject.binar.secondhand.model.network.response.buyer.GetBuyerProductItem

class ItemProductHomeAdapter(private val onClickListener: (id: Int, product: GetBuyerProductItem) -> Unit) :
    RecyclerView.Adapter<ItemProductHomeAdapter.ItemProductHomeViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetBuyerProductItem>() {
        override fun areItemsTheSame(
            oldItem: GetBuyerProductItem,
            newItem: GetBuyerProductItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetBuyerProductItem,
            newItem: GetBuyerProductItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(product: GetBuyerProduct?) = listDiffer.submitList(product)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemProductHomeViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemProductHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemProductHomeViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class ItemProductHomeViewHolder(private val binding: ItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetBuyerProductItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(ivProductImage)

                tvProductName.text = item.name

                tvProductCategory.text = item.categories[0].name

                tvProductPrice.text = "Rp" + item.basePrice.toString()
            }
        }
    }

}