package com.fups.challenge.feature.login.presenter

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.fups.challenge.R
import com.fups.challenge.core.extensions.HorizontalLinearLayoutManager
import com.fups.challenge.core.extensions.observeEvent
import com.fups.challenge.core.platform.BaseFragment
import com.fups.challenge.databinding.FragmentLoginBinding
import com.fups.challenge.feature.login.domain.LoginViewEvent
import com.fups.challenge.feature.login.domain.LoginViewModel
import com.fups.data.remote.response.BannerResponse
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutId = R.layout.fragment_login,
    LoginViewModel::class.java
) {
    override fun getScreenKey(): String {
        //Event takibi için kullanılabilir bu method
        return "Login Page"
    }
    override fun onDataBinding() {
        binding.viewModel = viewModel
        observeEvent(viewModel.event, ::onViewEvent)
        val bannerAdapter = BannerAdapter(viewModel, getBannerList())
        val manager =
            HorizontalLinearLayoutManager(requireContext(), 0.7)
        binding.recyclerView.apply {
            adapter = bannerAdapter
            layoutManager = manager
        }
        binding.apply {
            appCompatImageView2.setOnClickListener {
                spinner.show()
            }
            spinner.apply {

            }
        }

    }

    private fun onViewEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.NavigateToMain ->
                navigateToHome()
            LoginViewEvent.GetInfo -> Toast.makeText(requireContext(), "Info", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun getBannerList(): List<BannerResponse> {
        return listOf<BannerResponse>(
            BannerResponse(
                1, "SOSYAL HESAP", "Sosyal Hesap’larını\n" +
                        "oluşturarak sevdiklerine\n" +
                        "para gönder, iste.",
                R.drawable.ic_image_banner_big_01
            ),
            BannerResponse(
                2,
                "UYGULAMA",
                "Uygulamamızı indirerek yeni özelliklerimizi kullanabilirsiniz",
                R.drawable.ic_image_banner_big_02
            )
        )
    }

}