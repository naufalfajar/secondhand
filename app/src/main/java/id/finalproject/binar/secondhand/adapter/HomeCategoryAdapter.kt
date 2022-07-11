package id.finalproject.binar.secondhand.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.finalproject.binar.secondhand.databinding.ItemCategoryBinding
import id.finalproject.binar.secondhand.model.local.entity.Category

class HomeCategoryAdapter :
    ListAdapter<Category, HomeCategoryAdapter.CategoryViewHolder>(ProductComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.apply {
                btCategory.text = category.name

//                itemProduct.setOnClickListener {
//                    onclicklistener.invoke(product.id, product)
//                }
            }
        }
    }

    class ProductComparator : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }

}