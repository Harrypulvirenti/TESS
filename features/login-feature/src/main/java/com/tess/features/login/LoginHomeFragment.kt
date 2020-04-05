package com.tess.features.login

import android.os.Bundle
import android.view.View
import com.tess.architecture.sdk.base.BaseFragment
import com.tess.features.login.viewModel.LoginViewModel

internal class LoginHomeFragment(private val viewModel: LoginViewModel) : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_login_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}