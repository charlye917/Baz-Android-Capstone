package com.carteagal.baz_android.utils

import android.util.Log
import java.util.*

enum class NameCrypto(val value: String){
    AAVE("Aave"),
    ADA("Cardano"),
    ALGO("Algorand"),
    APE("ApeCoin"),
    AXS("Axie Infinity"),
    BAL("Balancer"),
    BAT("Basic Attention"),
    BCH("Bitcoin Cash"),
    BTC("Bitcoin"),
    CHZ("Chiliz"),
    COMP("Compound"),
    CRV("Curve DAO"),
    DAI("Dai"),
    DOGE("Dogecoin"),
    DOT("Dot"),
    DYDX("Dydx"),
    ENJ("Enjin Coin"),
    ETH("Ethereum"),
    EUR("Euro"),
    FTM("Fantom"),
    GALA("Gala"),
    GRT("The Graph"),
    LDO("Lido DAO"),
    LINK("Chainlink"),
    LRC("Loopring"),
    LTC("Litecoin"),
    MANA("Decentraland"),
    MATIC("MATIC"),
    MKR("Maker"),
    OMG("OMG Network"),
    PAXG("PAX Gold"),
    QNT("Quant"),
    SAND("The Sandbox"),
    SHIB("Shiba Inu"),
    SNX("Synthetix"),
    SOL("Solana"),
    SUSHI("SushiSwap"),
    TIGRES("Tigres"),
    TRX("Tron"),
    TUSD("TrueUSD"),
    UNI("Uniswap"),
    USD("USD Coin"),
    USDT("Tether"),
    XLM("Stellar"),
    XRP("Ripple"),
    XFI("Yearn.finance");

    companion object{
        fun getFullName(name: String): String{
            return try {
                valueOf(name.uppercase(Locale.getDefault())).value
            }catch (e: java.lang.Exception){
                name
            }
        }
    }
}