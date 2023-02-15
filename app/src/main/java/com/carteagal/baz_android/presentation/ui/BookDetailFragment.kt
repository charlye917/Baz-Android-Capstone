package com.carteagal.baz_android.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.remote.network.CheckInternetConnectionTwo
import com.carteagal.baz_android.databinding.FragmentBookDetailBinding
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.presentation.adapter.AskBindsAdapter
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import com.carteagal.baz_android.utils.AlertError
import com.carteagal.baz_android.utils.TypeAskBid.ASKS
import com.carteagal.baz_android.utils.TypeAskBid.BIDS
import com.carteagal.baz_android.utils.extension.View.loadImage
import com.carteagal.baz_android.utils.extension.getAbbreviation
import com.carteagal.baz_android.utils.extension.toAmountFormat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
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
    private var asks = listOf<AskBindUI>()
    private var bids = listOf<AskBindUI>()

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

        executeService()
        viewModelObserver()

        setConfigTabLayout()
        setUpRecyclerView()
    }

    private fun executeService(){
        cryptoViewModel.getTicker(bookName)
        cryptoViewModel.getAskBind(bookName)
    }

    private fun viewModelObserver(){
        cryptoViewModel.apply {
            loading.observe(viewLifecycleOwner){
                binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
            ticker.observe(viewLifecycleOwner){
                setUpView(it)
            }
            askBindList.observe(viewLifecycleOwner){ listAskBind ->
                asks = listAskBind.filter { it.type == ASKS }
                bids = listAskBind.filter { it.type == BIDS }
                askBindAdapter.submitList(bids)
            }
            isError.observe(viewLifecycleOwner){
                binding.itemError.itemError.visibility = if(it) View.VISIBLE else View.GONE
                binding.constraintInfo.visibility = if(it) View.GONE else View.VISIBLE
            }
            CheckInternetConnectionTwo(requireActivity().application)
                .observe(viewLifecycleOwner){
                    if(!it) AlertError.showAlertError(requireContext()){executeService()}
                }
        }
    }

    private fun setUpView(ticker: TickerUI){
        binding.itemCardInfo.apply {
            txtNameBook.text = ticker.bookName
            txtAbreviation.text = ticker.fullName?.getAbbreviation()
            txtPrice.text = getString(R.string.price, ticker.price?.toAmountFormat(), ticker.typeMoney)
            txtHighPrice.text = getString(R.string.price_hight, ticker.highPrice?.toAmountFormat(), ticker.typeMoney)
            txtLowPrice.text = getString(R.string.price_low, ticker.lowPrice?.toAmountFormat(), ticker.typeMoney)
            txtAsk.text = getString(R.string.ask, ticker.ask?.toAmountFormat(), ticker.typeMoney)
            txtBind.text = getString(R.string.bid, ticker.bind?.toAmountFormat(), ticker.typeMoney)
            txtLastModification.text = getString(R.string.last_update, ticker.lastModification)
            ticker.urlBook?.let { imgLogo.loadImage(it) }
        }
    }

    private fun setUpRecyclerView(){
        binding.rvAskBinds.apply {
            askBindAdapter = AskBindsAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = askBindAdapter
        }
    }

    private fun setConfigTabLayout(){
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