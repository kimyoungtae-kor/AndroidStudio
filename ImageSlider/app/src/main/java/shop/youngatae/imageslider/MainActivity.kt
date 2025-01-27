package shop.youngatae.imageslider

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity :AppCompatActivity(){
    private lateinit var addPhotoButton: Button
    private lateinit var startPhotoButton: Button

    private lateinit var imageViewList: List<ImageView>
    private val  ImageList: MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addPhotoButton = findViewById(R.id.addPhotoButton)
        startPhotoButton = findViewById(R.id.startPhotoButton)

        imageViewList = listOf(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3)

        )

        addPhotoButton.setOnClickListener {
            navigatePhotos()
        }
        startPhotoButton.setOnClickListener {
            val intent = Intent(this, )
            imageViewList.forEachIndexed{
                index,uri ->
                intent.putExtra("photoListSize",imageViewList.size)
                startActivity(intent)
            }
        }


    }
    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent,500)
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        when (requestCode) {
            500 -> {
                val selectedImageUri : Uri? = data?.data
                if(selectedImageUri != null){
                    if(imageViewList.size == 3){
                        Toast.makeText(this,"3장의 사진 선택이 완료되었습니다",
                            Toast.LENGTH_LONG).show()
                        return
                    }
                    imageViewList.add(selectedImageUri)
                    imageViewList[imageViewList.size - 1].setImageURI(selectedImageUri)
                }
            }
        }
    }


}