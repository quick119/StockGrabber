package com.quick.kotlin

import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.layout.GridPane
import okhttp3.*
import tornadofx.*
import java.io.IOException

class StockTableView : View() {
    override val root = GridPane()
    init {
        val data = FirebaseDatabase.getInstance().getReference("/data")
        data.setValue("123", null)
        //read data from open data
        val request = Request.Builder()
            .url("http://quality.data.gov.tw/dq_download_json.php?nid=11549&md5_url=bb878d47ffbe7b83bfc1b41d0b24946e")
            .build()
        OkHttpClient().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    println(e?.message);
                }

                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    val stockType = object : TypeToken<List<StockNG>>(){}.type
                    var stockNGs : List<StockNG> = Gson().fromJson(json, stockType)
                    var stockList : MutableList<Stock> = ArrayList()
                    for (stock in stockNGs) {
                        println("${stock.證券代號}:${stock.證券名稱}")
                        stockList.add(stock.toStock())
                    }
                    //save to Firebase
                    FirebaseDatabase.getInstance().getReference("stockPerfect")
                        .setValue(stockList, null)

                    var stocks = FXCollections.observableArrayList<StockNG>(stockNGs)
                    //tableview
                    Platform.runLater({
                        with(root) {
                            row {
                                vbox {
                                    label { "Stock table" }
                                    tableview(stocks) {
                                        readonlyColumn("代號", StockNG::證券代號)
                                        readonlyColumn("名稱", StockNG::證券名稱)
                                        readonlyColumn("收盤", StockNG::收盤價)
                                        readonlyColumn("價差", StockNG::漲跌價差)
                                        resizeColumnsToFitContent()
                                    }
                                }
                            }
                        }
                    })

                }

            })
    }
}