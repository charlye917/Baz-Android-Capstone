package com.carteagal.baz_android.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.model.AskBindsResponse
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.databinding.FragmentBookDetailBinding
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.presentation.adapter.AskBindsAdapter
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import com.carteagal.baz_android.utils.extension.View.loadImage
import com.carteagal.baz_android.utils.extension.getAbbreviation
import com.carteagal.baz_android.utils.extension.getBookName
import com.carteagal.baz_android.utils.extension.toAmountFormat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private lateinit var _binding: FragmentBookDetailBinding
    private val binding: FragmentBookDetailBinding get() = _binding

    private val args: BookDetailFragmentArgs by navArgs<BookDetailFragmentArgs>()
    private val cryptoViewModel: CryptoViewModel by activityViewModels()

    private lateinit var askBindAdapter: AskBindsAdapter

    private lateinit var bookName: String
    private lateinit var urlBookImage: String
    private var asks = listOf<AskBindsResponse>()
    private var bids = listOf<AskBindsResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookName = args.bookName
        urlBookImage = args.urlBook

        cryptoViewModel.getTicker(bookName)
        cryptoViewModel.getOrderBook(bookName)

        loadDataTicker()
        loadDataOrderBook()

        setUpTransactionParams()
        setUpRecyclerView()
    }

    private fun loadDataTicker(){
        cryptoViewModel.ticker.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Loading -> {
                    _binding.progressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    _binding.progressBar.visibility = View.GONE
                    setUpView(response.data)
                }
                is Error -> {
                    _binding.progressBar.visibility = View.GONE
                    Log.d("__tag error", response.error.message)
                }
            }
        }
    }

    private fun loadDataOrderBook(){
        cryptoViewModel.orderBooks.observe(viewLifecycleOwner){ result ->
            when(result){
                is Loading -> {
                    _binding.progressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    asks = result.data.asks!!
                    bids = result.data.bids!!
                    askBindAdapter.submitList(bids)
                    _binding.progressBar.visibility = View.GONE
                }
                is Error -> {
                    _binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpView(ticker: TickerUI){
        binding.itemCardInfo.apply {
            txtNameBook.text = ticker.bookName
            txtAbreviation.text = ticker.fullName.getAbbreviation()
            txtPrice.text = getString(R.string.price, ticker.price.toAmountFormat(), ticker.typeMoney)
            txtHighPrice.text = getString(R.string.price_hight, ticker.highPrice.toAmountFormat(), ticker.typeMoney)
            txtLowPrice.text = getString(R.string.price_low, ticker.lowPrice.toAmountFormat(), ticker.typeMoney)
            txtAsk.text = getString(R.string.ask, ticker.ask.toAmountFormat(), ticker.typeMoney)
            txtBind.text = getString(R.string.bid, ticker.bind.toAmountFormat(), ticker.typeMoney)
            txtLastModification.text = getString(R.string.last_update, ticker.lastModification)
            imgLogo.loadImage(ticker.urlBook)
        }
    }

    private fun setUpRecyclerView(){
        binding.rvAskBinds.apply {
            askBindAdapter = AskBindsAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = askBindAdapter
        }
    }

    private fun setUpTransactionParams(){
        _binding.tabLayout.apply {
            context.resources.getStringArray(R.array.transactions_list).map { addTab(newTab().setText(it)) }
            addOnTabSelectedListener(object : OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0 -> { askBindAdapter.submitList(bids) }
                        1 -> { askBindAdapter.submitList(asks) }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}