package ru.epipasha.academy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pushButton : Button = findViewById<Button>(R.id.btn_push)
        pushButton.setOnClickListener {
            moveToDetailScreen()
        }
    }

    private fun moveToDetailScreen(){
        val intent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(intent)
    }
}