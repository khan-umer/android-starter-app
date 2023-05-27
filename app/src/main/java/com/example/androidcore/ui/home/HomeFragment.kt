package com.example.androidcore.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcore.R
import com.example.androidcore.adapters.ProductListAdapter
import com.example.androidcore.data.newtwork.NetworkResult
import com.example.androidcore.data.models.ProductListItem
import com.example.androidcore.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "LOG_TAG::" + HomeFragment::class.java.simpleName

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    lateinit var productListAdapter: ProductListAdapter

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView:")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated:")


        setupUIState()
        setupObservers()
    }

    private fun setupUIState() {

        setupMenuProvider()
        productListAdapter = ProductListAdapter()
        productListAdapter.differ.submitList(emptyList<ProductListItem>())

        binding.rvProductList.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            adapter = productListAdapter
        }
    }

    private fun setupObservers() {
        homeViewModel.productList.observe(viewLifecycleOwner) {

            when (it) {
                is NetworkResult.Loading -> {
                    showProgressBar()
                }
                is NetworkResult.Success -> {
                    val listData = it.data ?: emptyList()
                    productListAdapter.setData(listData)
//                    productListAdapter.differ.submitList(it.data)

                    hideProgressBar()
                }
                is NetworkResult.Error -> {
                    hideProgressBar()
                    Toast.makeText(this.requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupMenuProvider() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchItem = menu.findItem(R.id.actionSearch)
                searchView = searchItem.actionView as SearchView
                searchView.apply {
                    imeOptions = EditorInfo.IME_ACTION_DONE
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            productListAdapter.filter.filter(newText)
                            return false
                        }

                    })

                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.actionSearch -> {
                        searchView = menuItem.actionView as SearchView
                    }
                    else -> {
                        Toast.makeText(requireContext(), menuItem.title, Toast.LENGTH_SHORT).show()
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showProgressBar() {
        binding.homeProgressLoader.visibility = View.VISIBLE
        binding.rvProductList.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.homeProgressLoader.visibility = View.INVISIBLE
        binding.rvProductList.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView:")
        _binding = null
    }

}