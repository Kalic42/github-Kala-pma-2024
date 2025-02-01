package com.example.vanocniaplikace

import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Načti obrázek rolničky
        val rolnickaImage: ImageView = findViewById(R.id.btnRolnicka)

        // Načti animaci
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale)

        // Načti zvuk rolničky
        val mediaPlayer = MediaPlayer.create(this, R.raw.rolnicka) // Ujisti se, že máš zvukový soubor ve složce res/raw

        // Nastav posluchač kliknutí na obrázek rolničky
        rolnickaImage.setOnClickListener {
            // Přehrát zvuk rolničky
            mediaPlayer.start()

            // Spustit animaci zvětšení a zmenšení
            rolnickaImage.startAnimation(scaleAnimation)

            // Po dokončení přehrávání zvuku zastavit animaci
            mediaPlayer.setOnCompletionListener {
                rolnickaImage.clearAnimation()
            }
        }
    }
}
