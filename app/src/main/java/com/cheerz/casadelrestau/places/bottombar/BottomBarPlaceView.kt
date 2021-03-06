package com.cheerz.casadelrestau.places.bottombar

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.cheerz.casadelrestau.R
import com.cheerz.casadelrestau.events.Event
import com.cheerz.casadelrestau.hide
import com.cheerz.casadelrestau.network.data.MiamzReqPlaceData
import com.cheerz.casadelrestau.show
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_address
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_already_booked
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_arrow
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_create_your_event
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_name
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_tag
import kotlinx.android.synthetic.main.bottom_bar_place_view.view.bottom_bar_top

class BottomBarPlaceView(context: Context, attrs: AttributeSet) : BottomBarPlaceMvp.View, LinearLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.bottom_bar_place_view, this)
        setBackgroundColor(ContextCompat.getColor(context, R.color.bottom_bar_background))
        bottom_bar_arrow.setOnClickListener {
            val parent = parent as View
            val yOpened = (parent.height - height).toFloat()
            y = if (y == yOpened) (parent.height - bottom_bar_top.height).toFloat() else yOpened
        }
        val listener = context as Event.CreateEventDisplayer
        bottom_bar_create_your_event.setOnClickListener {
            listener.showCreateEvent(bottom_bar_name.text.toString(), tag as Int)
        }
    }

    override fun fillFields(place: MiamzReqPlaceData) {
        tag = place.id
        bottom_bar_name.text = place.name
        bottom_bar_address.text = place.address
        place.tags.firstOrNull()?.let { bottom_bar_tag.text = it }
        if (place.events.isNotEmpty())
            bottom_bar_already_booked.show()
        else
            bottom_bar_already_booked.hide()
    }
}
