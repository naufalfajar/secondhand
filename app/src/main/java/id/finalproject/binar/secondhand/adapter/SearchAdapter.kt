package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.ItemSearchBinding
import id.finalproject.binar.secondhand.model.local.entity.Product
import java.text.NumberFormat
import java.util.*

class SearchAdapter(private val onClickListener: (id: Int, product: Product) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ProductViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(product: List<Product>) = listDiffer.submitList(product)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class ProductViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                if (product.image_url != null) {
                    Glide.with(itemView.context)
                        .load(product.image_url)
                        .into(ivPicture)
                } else {
                    ivPicture.setImageResource(R.drawable.noimage)
                }

                tvProductName.text = product.name

                var category = ""

                for (i in product.Categories) {
                    category = category + ", " + i.name
                }

                if (category != "") {
                    category = category.substring(1)
                }

                tvCategory.text = category

                tvProductPrice.text = rupiah(product.base_price)

                itemSearch.setOnClickListener {
                    onClickListener.invoke(product.id!!, product)
                }
            }
        }
    }

    fun rupiah(number: Int?): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.minimumFractionDigits = 0
        return numberFormat.format(number).toString()
    }

}