package com.carteagal.baz_android.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.databinding.FragmentBookDetailBinding
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import com.carteagal.baz_android.utils.extension.View.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private lateinit var _binding: FragmentBookDetailBinding
    private val binding: FragmentBookDetailBinding get() = _binding

    private val args: BookDetailFragmentArgs by navArgs<BookDetailFragmentArgs>()
    private val cryptoViewModel: CryptoViewModel by activityViewModels()

    private lateinit var bookName: String
    private lateinit var urlBookImage: String

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
        loadData()
        setUpTransactionParams()
    }

    private fun loadData(){
        cryptoViewModel.ticker.observe(viewLifecycleOwner) { response ->
            Log.d("__tag ticker", response.toString())
            when (response) {
                is Resources.Success -> {
                    Log.d("__tag success", response.data.toString())
                    setUpView(response.data)
                }
                is Resources.Error -> {
                    Log.d("__tag error", response.error.message)
                }
            }
        }
    }

    private fun setUpView(ticker: TickerUI){
        binding.itemCardInfo.apply {
            txtNameBook.text = ticker.bookName
            txtPrice.text = ticker.lastModification
            txtHighPrice.text = ticker.highPrice.toString()
            txtLowPrice.text = ticker.lowPrice.toString()
            txtAsk.text = ticker.ask.toString()
            txtBind.text = ticker.bind.toString()
            txtLastModification.text = getString(R.string.last_update, ticker.lastModification)
            imgLogo.loadImage(ticker.urlBook)
        }
    }

    private fun setUpTransactionParams(){
        _binding.tabLayout.apply {
            context.resources.getStringArray(R.array.transactions_list)
                .map { addTab(newTab().setText(it)) }
        }
    }
}