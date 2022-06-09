package com.fups.challenge.core.platform

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.fups.challenge.core.extensions.observeEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    internal val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutId) as DB
    }
    val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    abstract fun onDataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        onDataBinding()
    }


    open fun finishApp() {
        finish()
        val finishIntent =
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

        startActivity(finishIntent)
    }
}
