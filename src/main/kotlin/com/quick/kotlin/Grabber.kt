package com.quick.kotlin

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import javafx.application.Application
import tornadofx.App
import java.io.FileInputStream

class Grabber : App() {
    override val primaryView = StockTableView::class
    init {
        FirebaseApp.initializeApp(FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(
                FileInputStream("portfolio-d66fc-firebase-adminsdk-ewtbi-83ab7696f0.json")))
            .setDatabaseUrl("https://portfolio-d66fc.firebaseio.com")
            .build())
    }
}

fun main (args: Array<String>) {
    Application.launch(Grabber::class.java, *args)
}