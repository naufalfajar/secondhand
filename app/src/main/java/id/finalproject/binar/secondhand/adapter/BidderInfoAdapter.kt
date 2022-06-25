package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemBidderInfoBinding
import id.finalproject.binar.secondhand.model.GetSellerOrder
import id.finalproject.binar.secondhand.model.GetSellerOrderItem

class BidderInfoAdapter(private val onClickListener: (id: Int, bidderInfo: GetSellerOrderItem) -> Unit) :
    RecyclerView.Adapter<BidderInfoAdapter.BidderInfoViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetSellerOrderItem>() {
        override fun areItemsTheSame(
            oldItem: GetSellerOrderItem,
            newItem: GetSellerOrderItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetSellerOrderItem,
            newItem: GetSellerOrderItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(bidderInfo: GetSellerOrder?) = listDiffer.submitList(bidderInfo)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BidderInfoViewHolder {
        val binding =
            ItemBidderInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BidderInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BidderInfoViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class BidderInfoViewHolder(private val binding: ItemBidderInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetSellerOrderItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.product.imageUrl)
                    .into(ivPicture)

                tvProductName.text = item.product.name
                tvProductPrice.text = "Rp" + item.product.basePrice.toString()
                tvNote.text = "Ditawar Rp" + item.price

                btAccept.setOnClickListener {
                    buttonaccept.isVisible = false
                    buttoncontact.isVisible = true
                }

                btContact.setOnClickListener {
                    onClickListener.invoke(item.id, item)
                }

            }
        }
    }

}