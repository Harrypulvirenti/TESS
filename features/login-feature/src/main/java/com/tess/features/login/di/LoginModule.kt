package com.tess.features.login.di

import com.tess.features.login.LoginHomeFragment
import com.tess.features.login.viewModel.LoginViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel { LoginViewModel() }

    fragment { LoginHomeFragment(get()) }
}
