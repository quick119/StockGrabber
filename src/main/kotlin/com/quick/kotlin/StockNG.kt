package com.quick.kotlin

data class StockNG(
    val 成交筆數: String,
    val 成交股數: String,
    val 成交金額: String,
    val 收盤價: String,
    val 最低價: String,
    val 最高價: String,
    val 漲跌價差: String,
    val 證券代號: String,
    val 證券名稱: String,
    val 開盤價: String
) {
    fun toStock():Stock {
        return Stock(成交筆數.replace(",", "").toLong(), 成交股數.replace(",", "").toLong(),
            成交金額.replace(",", "").toLong(), 收盤價.replace(",", "").toFloat(),
            最低價.replace(",", "").toFloat(), 最高價.replace(",", "").toFloat(),
            漲跌價差.replace("X", "").replace(",", "").toFloat(), 證券代號, 證券名稱, 開盤價.replace(",", "").toFloat())
    }
}

data class Stock(var volume: Long, var share: Long, var value: Long,
                 var close: Float, var low: Float, var high: Float, var diff: Float,
                 var symbol: String, var name: String, var open:Float) {

}