package com.example.googlebookscleanarchitecture.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout

object ViewUtils {

    lateinit var emptyLayoutView : View
    lateinit var shimmerLayoutView : ShimmerFrameLayout
    lateinit var recyclerView : RecyclerView

    fun viewVisibilityControl(emptyLayout : Boolean  , shimmerLayout : Boolean , normalLayout : Boolean){
        if (emptyLayout) emptyLayoutView.visibility = View.VISIBLE else emptyLayoutView.visibility = View.INVISIBLE
        if (shimmerLayout) { shimmerLayoutView.visibility = View.VISIBLE
            shimmerLayoutView.startShimmer()} else { shimmerLayoutView.stopShimmer()
            shimmerLayoutView.visibility = View.INVISIBLE}
        if (normalLayout) recyclerView.visibility = View.VISIBLE else recyclerView.visibility = View.INVISIBLE
    }

}