package com.example.cityplatecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.cityplatecode.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var binding: ActivityMainBinding

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        binding.button.setOnClickListener {
            if (binding.editTextNumber.text.isNotEmpty()) {
                binding.textView.text = "Aranıyor..."
                var plateCode = binding.editTextNumber.text.toString().toInt()
                println("plate"+ plateCode)
                // CoroutineScope oluşturulur
                val scope = CoroutineScope(Dispatchers.Main)
                // fetchProvinceData asenkron olarak çağrılır
                scope.launch {
                    val province = fetchProvinceData(plateCode)
                    println("province: $province")
                    binding.textView.text = province
                }
//                val province = getProvinceFromApi(plateCode)
//
            } else {
                binding.textView.text = "Please Enter City Plate Code"
            }
        }
    }

    // Suspend fonksiyon olarak fetchProvinceData tanımlanır
    suspend fun fetchProvinceData(id:Int):String{
       val provinceApi = RetrofitHelper.getInstance().create(ProvinceApi::class.java)


        // CoroutineScope oluşturulur
        val scope = CoroutineScope(Dispatchers.Default)

        val result = scope.async {
            val response = provinceApi.getProvince(id)
            if (response.isSuccessful) {
                response.body()?.data?.name.toString()
            } else {
                "Şehir Bulunamadı"
            }
        }
        return  result.await()
    }

}