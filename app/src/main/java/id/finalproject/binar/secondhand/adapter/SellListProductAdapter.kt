package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.ItemProductHomeBinding
import id.finalproject.binar.secondhand.model.local.entity.ProductSeller
import java.text.NumberFormat
import java.util.*

class SellListProductAdapter(private val onClickListener: (id: Int, product: ProductSeller) -> Unit) :
    RecyclerView.Adapter<SellListProductAdapter.ProductViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ProductSeller>() {
        override fun areItemsTheSame(oldItem: ProductSeller, newItem: ProductSeller) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductSeller, newItem: ProductSeller) =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(product: List<ProductSeller>) = listDiffer.submitList(product)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class ProductViewHolder(private val binding: ItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductSeller) {
            binding.apply {

                if (product.id == -1) {
                    listproduct.isVisible = false
                    nothing.isVisible = true
                } else {
                    if (product.image_url != null) {
                        Glide.with(itemView.context)
                            .load(product.image_url)
                            .into(ivProductImage)
                    } else {
                        ivProductImage.setImageResource(R.drawable.noimage)
                    }

                    tvProductName.text = product.name

                    var category = ""

                    for (i in product.Categories) {
                        category = category + ", " + i.name
                    }

                    if (category != "") {
                        category = category.substring(1)
                    }

                    tvProductCategory.text = category

                    tvProductPrice.text = rupiah(product.base_price)
                }
                itemProduct.setOnClickListener {
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