package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.ItemNotificationBinding
import id.finalproject.binar.secondhand.model.GetNotification
import id.finalproject.binar.secondhand.model.GetNotificationItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
                    tvNote.text = "Ditawar Rp" + item.bidPrice.toString()
                } else {
                    tvType.text = "Berhasil Diterbitkan"
                    tvNote.isVisible = false
                }

                if (item.read) {
                    ivCircle.isVisible = false
                }

                val dateTime = item.transactionDate
                tvTime.text = formatDate(
                    dateTime.slice(0..9),
                    "dd MMM"
                ) + ", " + formatTime(dateTime.slice(11..18), "HH.mm")

                if (item.imageUrl == null) {
                    ivPicture.setImageResource(R.drawable.ic_launcher_background)
                } else {
                    Glide.with(itemView.context)
                        .load(item.imageUrl)
                        .into(ivPicture)
                }

                tvProductName.text = item.productId.toString()

                itemNotification.setOnClickListener {
                    onClickListener.invoke(item.id, item)
                }

            }
        }
    }

    private fun formatDate(date: String, format: String): String {
        var formattedDate = ""
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val parseDate = sdf.parse(date)
            formattedDate = SimpleDateFormat(format).format(parseDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedDate
    }

    private fun formatTime(time: String, format: String): String {
        var formattedTime = ""
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val parseTime = sdf.parse(time)
            formattedTime = SimpleDateFormat(format).format(parseTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formattedTime
    }

}