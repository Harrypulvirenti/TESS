package com.tess.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tess.core.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

//    private var state: Boolean = false
//    private val myRef: DatabaseReference by lazy {
//        FirebaseDatabase.getInstance().getReference("state")
//    }

    private val testViewModel: TestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSwitch.setOnClickListener {

            testViewModel.getData()
        }


        finish()

//        val repo = SettingRepository()
//
//        repo.addDocument("name", Setting("enrico", "pulvirenti"))
//
//        val doc = FirebaseFirestore.getInstance().collection("Settings").document("name")
//
//        doc.update("name", "giovanni")

//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                state = p0.getValue(Boolean::class.java) ?: false
//            }
//        })
//        buttonSwitch.setOnClickListener {
//            myRef.setValue(!state)
//        }
    }
}
