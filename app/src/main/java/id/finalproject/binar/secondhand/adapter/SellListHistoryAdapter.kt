package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemHistoryBinding
import id.finalproject.binar.secondhand.model.local.entity.History
import id.finalproject.binar.secondhand.model.network.response.GetHistory
import id.finalproject.binar.secondhand.model.network.response.GetHistoryItem
import id.finalproject.binar.secondhand.model.network.response.seller.GetSellerOrderItem
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SellListHistoryAdapter() :
    RecyclerView.Adapter<SellListHistoryAdapter.HistoryViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetHistoryItem>() {
        override fun areItemsTheSame(oldItem: GetHistoryItem, newItem: GetHistoryItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GetHistoryItem, newItem: GetHistoryItem) =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(history: List<GetHistoryItem>?) = listDiffer.submitList(history)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: GetHistoryItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(history.imageUrl)
                    .into(ivPicture)
                tvStatus.text = history.status
                tvProductName.text = history.productName
                tvProductPrice.text = rupiah(history.price)

                val dateTime = history.transactionDate!!
                tvDate.text = formatDate(
                    dateTime.slice(0..9),
                    "dd MMM yyyy"
                )

//                itemHistory.setOnClickListener {
//                    onClickListener.invoke(history.id!!, history)
//                }
            }
        }
    }

    private fun rupiah(number: Int?): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(number).toString()
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