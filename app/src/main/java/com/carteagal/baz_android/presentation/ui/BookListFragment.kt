package com.carteagal.baz_android.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.remote.network.CheckInternetConnectionTwo
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.databinding.FragmentBookListBinding
import com.carteagal.baz_android.presentation.adapter.BooksAdapter
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import com.carteagal.baz_android.utils.AlertError
import com.carteagal.baz_android.utils.TypeSorts
import com.carteagal.baz_android.utils.TypeSorts.SORT_MAX
import com.carteagal.baz_android.utils.TypeSorts.SORT_MIN
import com.carteagal.baz_android.utils.TypeSorts.SORT_NAME_AZ
import com.carteagal.baz_android.utils.TypeSorts.SORT_NAME_ZA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookListFragment : Fragment() {

    private lateinit var _binding: FragmentBookListBinding
    private val binding: FragmentBookListBinding get() = _binding

    private lateinit var booksAdapter: BooksAdapter
    private val cryptoViewModel: CryptoViewModel by activityViewModels()

    private val list = arrayListOf<AvailableBookUI>()

    private lateinit var checkNetworkConnection: CheckInternetConnectionTwo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookListBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executeService()
        viewModelObserver()

        setUpMenu()
        setUpRecyclerView()
    }

    private fun executeService(){
        cryptoViewModel.getAvailableBooks()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModelObserver(){
        cryptoViewModel.apply {
            availableBooks.observe(viewLifecycleOwner) {
                list.clear()
                booksAdapter.notifyDataSetChanged()
                list.addAll(it)
                booksAdapter.notifyItemRangeInserted(0, list.size)
                booksAdapter.submitList(list)
            }

            loading.observe(viewLifecycleOwner){
                binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
            }

            isError.observe(viewLifecycleOwner){
                binding.itemError.itemError.visibility = if(it)  View.VISIBLE else View.GONE
            }
        }

        CheckInternetConnectionTwo(requireActivity().application)
            .observe(viewLifecycleOwner){
                if(!it) AlertError.showAlertError(requireContext()) { executeService() }
            }
    }

    private fun setUpMenu(){
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.order_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.action_search -> {Log.d("__tag search", "dio click search")}
                    R.id.action_sort_name_az -> { cryptoViewModel.orderListBooks(SORT_NAME_AZ) }
                    R.id.action_sort_name_za -> { cryptoViewModel.orderListBooks(SORT_NAME_ZA)  }
                    R.id.action_sort_max -> { cryptoViewModel.orderListBooks(SORT_MAX) }
                    R.id.action_sort_min -> { cryptoViewModel.orderListBooks(SORT_MIN) }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setUpRecyclerView(){
        binding.rvBooks.apply {
            booksAdapter = BooksAdapter{ changeFragmentOnClick(it) }
            layoutManager = LinearLayoutManager(requireContext())
            adapter = booksAdapter
        }
    }

    private fun changeFragmentOnClick(bookInfo: AvailableBookUI){
        findNavController().navigate(BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(bookInfo.fullName ?: "", bookInfo.imageUrl ?: ""))
    }
}