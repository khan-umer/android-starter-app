package com.example.androidcore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidcore.R
import com.example.androidcore.data.models.ProductListItem
import com.example.androidcore.databinding.ProductListItemBinding
import com.example.androidcore.utils.ProductSearchListFilter

private const val TAG = "ProductListAdapter"

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>(),
    Filterable {

    lateinit var completeProductList: List<ProductListItem>

    inner class ProductListViewHolder(val itemviewBinding: ProductListItemBinding) :
        RecyclerView.ViewHolder(itemviewBinding.root) {
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductListItem,
            newItem: ProductListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding =
            ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    fun setData(list: List<ProductListItem>) {
        completeProductList = list
        differ.submitList(list)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val productItem = differ.currentList[position]
        with(holder) {
            with(productItem) {
                itemviewBinding.tvProductName.text = productItem.title
                val formattedPriceValue: String =
                    holder.itemView.context.getString(R.string.price, productItem.price)
                itemviewBinding.tvProductPrice.text = formattedPriceValue
                Glide.with(itemView.context)
                    .load(productItem.image)
                    .placeholder(R.mipmap.ic_launcher_foreground)
                    .error(R.mipmap.ic_launcher_foreground)
                    .into(itemviewBinding.ivProductImage)
            }
        }

    }


    override fun getFilter(): Filter {
        return ProductSearchListFilter(completeProductList, this)
    }

}