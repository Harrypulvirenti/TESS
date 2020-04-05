package com.tess.architecture.sdk.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.tess.sdk.R
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    @LayoutRes
    open fun getLayoutId(): Int = R.layout.activity_single_fragment
}
