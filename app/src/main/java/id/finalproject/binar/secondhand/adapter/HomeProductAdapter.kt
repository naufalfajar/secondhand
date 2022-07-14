package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.ItemProductHomeBinding
import id.finalproject.binar.secondhand.model.local.entity.Product
import java.text.NumberFormat
import java.util.*


class HomeProductAdapter(private val onClickListener: (id: Int, product: Product) -> Unit) :
    RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>() {

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
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class ProductViewHolder(private val binding: ItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
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
                    category = category + "," + i.name
                }

                //Tanda koma didepan masih kena print
                tvProductCategory.text = category

                tvProductPrice.text = rupiah(product.base_price)

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


