package com.carteagal.baz_android.presentation.ui

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
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.databinding.FragmentBookListBinding
import com.carteagal.baz_android.presentation.adapter.BooksAdapter
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookListFragment : Fragment() {

    private lateinit var _binding: FragmentBookListBinding
    private val binding: FragmentBookListBinding get() = _binding

    private lateinit var booksAdapter: BooksAdapter
    private val cryptoViewModel: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookListBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoViewModel.getAvailableBooks()

        setUpMenu()
        loadData()
        setUpRecyclerView()
    }

    private fun loadData(){
        cryptoViewModel.apply {
            availableBooks.observe(viewLifecycleOwner) { booksAdapter.submitList(it) }
            loading.observe(viewLifecycleOwner){
                binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setUpMenu(){
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem) = true
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