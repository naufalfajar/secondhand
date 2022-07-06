package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.finalproject.binar.secondhand.databinding.ItemProductHomeBinding
import id.finalproject.binar.secondhand.model.local.entity.Product

class HomeProductAdapter :
    ListAdapter<Product, HomeProductAdapter.ProductViewHolder>(ProductComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ProductViewHolder(private val binding: ItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(product.image_url)
                    .into(ivProductImage)

                tvProductName.text = product.name

                tvProductCategory.text = product.categories

                tvProductPrice.text = "Rp" + product.base_price.toString()

//                itemProduct.setOnClickListener {
//                    onclicklistener.invoke(product.id, product)
//                }
            }
        }
    }

    class ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }

}


