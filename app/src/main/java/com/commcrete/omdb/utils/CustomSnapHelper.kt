package com.commcrete.omdb.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CustomSnapHelper : LinearSnapHelper() {

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is LinearLayoutManager) return RecyclerView.NO_POSITION

        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        if (firstVisibleItem == 0 && velocityX < 0) {
            return firstVisibleItem
        }

        if (lastVisibleItem == itemCount - 1 && velocityX > 0) {
            return lastVisibleItem
        }

        return super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager !is LinearLayoutManager) return null

        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        if (firstVisibleItem == 0) {
            return layoutManager.findViewByPosition(firstVisibleItem)
        }

        if (lastVisibleItem == itemCount - 1) {
            return layoutManager.findViewByPosition(lastVisibleItem)
        }

        return super.findSnapView(layoutManager)
    }
}
