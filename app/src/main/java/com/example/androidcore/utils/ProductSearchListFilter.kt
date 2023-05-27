package com.example.androidcore.utils

import android.widget.Filter
import com.example.androidcore.adapters.ProductListAdapter
import com.example.androidcore.data.models.ProductListItem

class ProductSearchListFilter(
    private val originalList: List<ProductListItem>,
    private val adapter: ProductListAdapter
) : Filter() {

    private val filteredList = mutableListOf<ProductListItem>()

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        filteredList.clear()
        if (constraint.isNullOrEmpty()) {
            filteredList.addAll(originalList)
        } else {
            val filterQuery = constraint.toString().lowercase().trim()
            originalList.forEach { item ->
                if (item.title.contains(filterQuery)) {
                    filteredList.add(item)
                }
            }
        }

        val filterResult = FilterResults()
        filterResult.values = filteredList
        return filterResult

    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.differ.submitList(filteredList)
    }
}