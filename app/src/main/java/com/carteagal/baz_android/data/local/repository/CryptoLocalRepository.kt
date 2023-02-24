package com.carteagal.baz_android.data.local.repository

import com.carteagal.baz_android.data.local.dao.AskBindDao
import com.carteagal.baz_android.data.local.dao.AvailableBookDao
import com.carteagal.baz_android.data.local.dao.TickerDao
import com.carteagal.baz_android.data.local.entities.askBindEntityToUI
import com.carteagal.baz_android.data.local.entities.askBindUItoEntity
import com.carteagal.baz_android.data.local.entities.availableEntitysToUI
import com.carteagal.baz_android.data.local.entities.availableUIToEntity
import com.carteagal.baz_android.data.local.entities.tickerEntityToResponse
import com.carteagal.baz_android.data.local.entities.tickerResponseToEntity
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.domain.model.TickerUI
import javax.inject.Inject

class CryptoLocalRepository @Inject constructor(
    private val availableBookDao: AvailableBookDao,
    private val askBindDao: AskBindDao,
    private val tickerDao: TickerDao
){
    suspend fun insertAllBooks(listBookUI: List<AvailableBookUI>){
        availableBookDao.deleteAllBooks()
        availableBookDao.insertAllBooks(listBookUI.availableUIToEntity())
    }

    suspend fun getAllBooks(): List<AvailableBookUI> =
        availableBookDao
            .getAllBooks()
            .availableEntitysToUI()

    suspend fun insertAskBind(listAskBind: List<AskBindUI>, book: String){
        askBindDao.deleteAsksBindsList(book)
        askBindDao.insertAskBindsList(listAskBind.askBindUItoEntity())
    }

    suspend fun getAllAskBind(book: String): List<AskBindUI> =
        askBindDao
            .getAllAsksBindsList(book)
            .askBindEntityToUI()

    suspend fun insertTicker(tickerUI: TickerUI){
        tickerUI.fullName?.let { tickerDao.deleteTicker(it) }
        tickerDao.insertTicker(tickerUI.tickerResponseToEntity())
    }

    suspend fun getTickerUI(book: String): TickerUI =
        tickerDao
            .getTicker(book)
            .tickerEntityToResponse()
}