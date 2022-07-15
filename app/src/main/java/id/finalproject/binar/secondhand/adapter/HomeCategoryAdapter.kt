package id.finalproject.binar.secondhand.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.finalproject.binar.secondhand.R
import id.finalproject.binar.secondhand.databinding.ItemCategoryBinding
import id.finalproject.binar.secondhand.model.local.entity.Category

class HomeCategoryAdapter(
    private val context: Context,
    private val onClickListener: (id: Int, category: Category) -> Unit
) :
    RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(category: List<Category>) = listDiffer.submitList(category)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position], position)
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var selected = 0

        fun bind(category: Category, position: Int) {
            binding.apply {
                tvCategory.text = category.name

                itemCategory.setOnClickListener {
                    onClickListener.invoke(category.id, category)
                    selected = position
                    notifyDataSetChanged()
                }

                if (selected == position) {
                    background.setBackgroundResource(R.color.dark_purple)
                    tvCategory.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                    ImageViewCompat.setImageTintList(
                        ivIcon,
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                    )
                } else {
                    background.setBackgroundResource(R.color.light_purple)
                    tvCategory.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.black
                        )
                    )
                    ImageViewCompat.setImageTintList(
                        ivIcon,
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
                    )
                }


//                if (selected == position) {
//                    itemCategory.setBackgroundColor(R.color.dark_purple)
//                    tvCategory.setTextColor(R.color.white)
//                } else {
//                    itemCategory.setBackgroundColor(R.color.light_purple)
//                    tvCategory.setTextColor(R.color.black)
//                }

            }
        }
    }

}

//class HomeCategoryAdapter :
//    ListAdapter<Category, HomeCategoryAdapter.CategoryViewHolder>(ProductComparator()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
//        val binding =
//            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return CategoryViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
//        val currentItem = getItem(position)
//        if (currentItem != null) {
//            holder.bind(currentItem)
//        }
//    }
//
//    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(category: Category) {
//            binding.apply {
//                btCategory.text = category.name
//
////                itemProduct.setOnClickListener {
////                    onclicklistener.invoke(product.id, product)
////                }
//            }
//        }
//    }
//
//    class ProductComparator : DiffUtil.ItemCallback<Category>() {
//        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
//            oldItem.name == newItem.name
//
//        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
//            oldItem == newItem
//    }
//
//}