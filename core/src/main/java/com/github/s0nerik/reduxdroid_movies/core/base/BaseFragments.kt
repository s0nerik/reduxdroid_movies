package com.github.s0nerik.reduxdroid_movies.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.s0nerik.reduxdroid.navigation.middleware.NavigationMiddleware
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseFragment(
        protected val layoutId: Int
) : Fragment() {
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return createView(inflater, container, savedInstanceState)
    }

    protected open fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutId, container, false)
    }
}

abstract class BaseBoundFragment<out TBinding : ViewDataBinding>(layoutId: Int) : BaseFragment(layoutId) {
    private lateinit var innerBinding: TBinding
    protected val binding
        get() = innerBinding

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        innerBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)!!
        innerBinding.setLifecycleOwner(this)
        return innerBinding.root
    }
}

abstract class BaseBoundVmFragment<out TBinding : ViewDataBinding, out TViewModel : ViewModel>(
        layoutId: Int,
        private val vmClass: KClass<TViewModel>,
        private val vmSetter: (TBinding) -> (TViewModel) -> Unit,
        private val useActivityVmStore: Boolean = false
) : BaseBoundFragment<TBinding>(layoutId) {
    protected val vm: TViewModel by viewModelByClass(vmClass, from = { if (useActivityVmStore) activity else this })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmSetter(binding)(vm)
    }
}

abstract class BaseBoundVmNavigationFragment<out TBinding : ViewDataBinding, out TViewModel : ViewModel>(
    layoutId: Int,
    vmClass: KClass<TViewModel>,
    vmSetter: (TBinding) -> (TViewModel) -> Unit,
    private val navHostFragmentId: Int,
    useActivityVmStore: Boolean = false
) : BaseBoundVmFragment<TBinding, TViewModel>(layoutId, vmClass, vmSetter, useActivityVmStore) {
    private val navigationMiddleware: NavigationMiddleware by inject()

    protected val navController: NavController
        get() = childFragmentManager.findFragmentById(navHostFragmentId)!!.findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationMiddleware.attachNavController(navController)
    }

    override fun onDestroyView() {
        navigationMiddleware.detachNavController(navController)
        super.onDestroyView()
    }
}