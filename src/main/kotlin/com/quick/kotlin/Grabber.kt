package com.quick.kotlin

import javafx.application.Application
import tornadofx.App

class Grabber : App() {
    override val primaryView = StockTableView::class
    init {
        
    }
}

fun main (args: Array<String>) {
    Application.launch(Grabber::class.java, *args)
}