package com.jg.videotest.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(savedInstanceState)
        initializeState(savedInstanceState)
        initializeContents(savedInstanceState)
    }

    abstract fun initializeViews(savedInstanceState: Bundle?)
    abstract fun initializeState(savedInstanceState: Bundle?)
    abstract fun initializeContents(savedInstanceState: Bundle?)
}