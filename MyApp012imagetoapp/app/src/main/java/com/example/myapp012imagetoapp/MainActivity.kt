package com.example.myapp012imagetoapp

import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp012imagetoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace bindingu pro layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení kontraktu pro výběr obrázku z galerie
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.ivImage.setImageURI(uri) // Zobrazení obrázku z galerie
            }
        }

        // Nastavení kontraktu pro pořízení fotografie
        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmap?.let {
                binding.ivImage.setImageBitmap(it) // Zobrazení pořízené fotografie
            }
        }

        // Nastavení listeneru pro tlačítko, které zobrazí dialog s možnostmi
        binding.btnTakeImage.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Vyberte zdroj obrázku")
                setItems(arrayOf("Galerie", "Fotoaparát")) { _, which ->
                    when (which) {
                        0 -> getContent.launch("image/*") // Otevření galerie
                        1 -> takePhoto.launch(null)       // Otevření fotoaparátu
                    }
                }
                show()
            }
        }
    }
}