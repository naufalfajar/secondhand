package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemTerjualBinding
import id.finalproject.binar.secondhand.model.network.response.seller.GetSellerOrderItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TerjualAdapter (val onItemClick: (GetSellerOrderItem) -> Unit) :
    RecyclerView.Adapter<TerjualAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTerjualBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetSellerOrderItem, position: Int) {
            binding.apply {
                root.setOnClickListener {
                    onItemClick(data)
                }
                val txtStatus = "Berhasil Terjual"
                val txtPrice = "Rp ${data.product.basePrice}"
                tvStatus.text = txtStatus
                tvHarga.text = txtPrice
                tvProductName.text = data.product.name
                val dateTime = data.updatedAt!!
                tvTime.text = formatDate(
                    dateTime.slice(0..9),
                    "dd MMM"
                ) + ", " + formatTime(dateTime.slice(11..18), "HH.mm")
                Glide.with(itemView).load(data.product.imageUrl).into(ivPicture)

            }
        }
    }

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

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<GetSellerOrderItem>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerjualAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTerjualBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TerjualAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data, position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
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