package com.tess.features.login

import android.os.Bundle
import com.tess.architecture.sdk.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginHomeFragment::class.java, null, null)
            .commit()
    }
}
