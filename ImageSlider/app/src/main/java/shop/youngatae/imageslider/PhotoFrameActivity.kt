package shop.youngatae.imageslider

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.mutableIntListOf

class PhotoFrameActivity: AppCompatActivity() {
    private val photoList = mutableListOf<Uri>()
    private var currentPosition = 0
    private var timeHandler: Handler? = null

    private lateinit var photoImageView: ImageView
    private lateinit var backgroundPhotoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)

        photoImageView = findViewById(R.id.imageView)
        backgroundPhotoImageView = findViewById(R.id.photoImageView)

//        getPhoto
    }
    private fun getPhotoUriFromIntent(){
        val size = intent.getIntExtra("photoListSize",0)
        for(i in 0 until size){

        }
    }
}