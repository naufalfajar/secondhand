package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemNotificationBinding
import id.finalproject.binar.secondhand.model.GetNotification
import id.finalproject.binar.secondhand.model.GetNotificationItem

class NotificationAdapter(private val onClickListener: (id: Int, notification: GetNotificationItem) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetNotificationItem>() {
        override fun areItemsTheSame(
            oldItem: GetNotificationItem,
            newItem: GetNotificationItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetNotificationItem,
            newItem: GetNotificationItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(notification: GetNotification?) = listDiffer.submitList(notification)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetNotificationItem) {
            binding.apply {
                if (item.status == "bid") {
                    tvType.text = "Penawaran Produk"
                }
                tvTime.text = item.createdAt
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(ivPicture)
                tvProductName.text = item.productId.toString()
                tvNote.text = "Ditawar Rp" + item.bidPrice.toString()
                itemNotification.setOnClickListener {
                    onClickListener.invoke(item.id, item)
                }

            }
        }
    }

}