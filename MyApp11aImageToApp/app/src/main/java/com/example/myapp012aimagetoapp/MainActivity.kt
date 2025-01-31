package com.example.myapp012aimagetoapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp012aimagetoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rotationAngle = 0f  // Úhel rotace
    private var isFlippedHorizontally = false  // Zda je obrázek zrcadlený horizontálně

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace bindingu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            binding.ivImage.setImageURI(uri)
        }

        binding.btnTakeImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnRotateImage.setOnClickListener {
            rotateImage()
        }

        binding.btnFlipImage.setOnClickListener {
            flipImageHorizontally()
        }
    }

    private fun rotateImage() {
        rotationAngle += 90f  // Zvyšujeme úhel o 90 stupňů
        binding.ivImage.rotation = rotationAngle % 360  // Nastavení rotace na ImageView
    }

    private fun flipImageHorizontally() {
        isFlippedHorizontally = !isFlippedHorizontally  // Přepínáme stav zrcadlení
        binding.ivImage.scaleX = if (isFlippedHorizontally) -1f else 1f  // Změní měřítko horizontálně
    }
}
