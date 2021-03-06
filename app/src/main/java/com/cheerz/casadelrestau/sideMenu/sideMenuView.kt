package com.cheerz.casadelrestau.sideMenu

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.cheerz.casadelrestau.R
import com.cheerz.casadelrestau.network.data.MiamzReqPlaceData
import com.cheerz.casadelrestau.places.PlacesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.left_menu
import kotlinx.android.synthetic.main.side_menu.view.restaurantList
import kotlinx.android.synthetic.main.side_menu.view.right_menu_cross
import kotlinx.android.synthetic.main.side_menu.view.right_menu_text

class SideMenuView(context: Context, attrs: AttributeSet? = null) : SideMenu.View, ConstraintLayout(context, attrs) {

    private var isLeftMenuOpen = false
    private var translation = 0F
    private val adapter = RestaurantListAdapter()

    init {
        inflate(context, R.layout.side_menu, this)
        restaurantList.adapter = adapter
        restaurantList.layoutManager = LinearLayoutManager(this.context)
        right_menu_cross.setOnClickListener { onLeftSideMenuClicked() }
        right_menu_text.setOnClickListener { onLeftSideMenuClicked() }
        PlacesRepository.getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ updateAdapter(it) }, {})
    }

    private fun updateAdapter(items: List<MiamzReqPlaceData>) {
        adapter.updateItems(items)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        post {
            val width = this.width
            val xTranslation = (width * 75) / 100
            val xCrossSize = (this.height * 5) / 100
            left_menu.x = xTranslation.toFloat()
            translation = left_menu.x

            right_menu_cross.layoutParams = (right_menu_cross.layoutParams as MarginLayoutParams).apply {
                this.setMargins(25, 25, 0, 0)
                this.width = xCrossSize
                this.height = xCrossSize
            }
            right_menu_cross.height = 10

        }
    }

    private fun onLeftSideMenuClicked() {
        isLeftMenuOpen = if (isLeftMenuOpen) {
            right_menu_cross.visibility = View.VISIBLE
            right_menu_text.visibility = View.GONE
            left_menu.animate().x(0F)
            false
        } else {
            right_menu_cross.visibility = View.GONE
            right_menu_text.visibility = View.VISIBLE
            left_menu.animate().x(translation)
            true
        }
        left_menu.animate().interpolator = LinearInterpolator()
        left_menu.animate().start()
    }
}