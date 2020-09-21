package com.app.f49.custom


import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.app.f49.R
import kotlinx.android.synthetic.main.notification_badge.view.*
import me.leolin.shortcutbadger.ShortcutBadger


class BadgedBottomNavigationBar : BottomNavigationView {
    @LayoutRes
    internal var badgeLayoutResId: Int = 0
    val TAB_QRCODE =2
    var TAB_NOTIFICATION =3

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.badgedBottomNavigationBar)
        badgeLayoutResId = a.getResourceId(R.styleable.badgedBottomNavigationBar_badge_layout, -1)
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    /**
     * show the badge on the menu item view.
     *
     * @param menuItemIndex
     */
    fun showBadgeNotification(menuItemIndex: Int, value: Int) {
        if (value <= 0) {
            removeBadge(TAB_NOTIFICATION)
            return
        }
        val bottomNavigationView = getChildAt(0) as BottomNavigationMenuView
        val view = bottomNavigationView.getChildAt(TAB_NOTIFICATION)
        if (view is ViewGroup) {
            //NUMBER_OF_MENU_ITEM_VIEW_CHILDERN_WITHOUT_BADGE
            if (view.childCount > 2) {
                if(view.getChildAt(3) is TextView){
                    (view.getChildAt(3) as TextView).text = "${value}"
                    ShortcutBadger.applyCount(context,value)
//                    BadgeUtils.setBadge(context,value)
                }
                return
            }
        }
        val bottomNavigationItemView = view as android.support.design.internal.BottomNavigationItemView

        var viewBadge = LayoutInflater.from(context).inflate(if (badgeLayoutResId != -1) badgeLayoutResId else R.layout.notification_badge, bottomNavigationItemView,
            true)
        viewBadge.notifications_badge.text = "$value"
        ShortcutBadger.applyCount(context,value)
    }

    fun showBadgeQRCode(menuItemIndex: Int, value: Int) {
        if (value <= 0) {
            removeBadge(TAB_QRCODE)
            return
        }
        val bottomNavigationView = getChildAt(0) as android.support.design.internal.BottomNavigationMenuView
        val view = bottomNavigationView.getChildAt(TAB_QRCODE)
        if (view is ViewGroup) {
            //NUMBER_OF_MENU_ITEM_VIEW_CHILDERN_WITHOUT_BADGE
            if (view.childCount > 2) {
                if(view.getChildAt(2) is TextView){
                    (view.getChildAt(2) as TextView).text = "${value}"
//                    ShortcutBadger.applyCount(context,value)
//                    BadgeUtils.setBadge(context,value)
                }
                return
            }
        }
        val bottomNavigationItemView = view as android.support.design.internal.BottomNavigationItemView

        var viewBadge = LayoutInflater.from(context).inflate(if (badgeLayoutResId != -1) badgeLayoutResId else R.layout.bottom_navigation_custom_item, bottomNavigationItemView,
            true)
//        viewBadge.notifications_badge.text = "$value"
//        ShortcutBadger.applyCount(context,value)
    }

    /**
     * this method to remove dot [badge view] if it's already inflated on the menu item.
     *
     * @param menuItemIndex  the menu item index
     */
    fun removeBadge(menuItemIndex: Int) {
        val bottomNavigationMenuView = getChildAt(0) as android.support.design.internal.BottomNavigationMenuView
        val v = bottomNavigationMenuView.getChildAt(menuItemIndex)
        // check if the badge is already displayed on the icon.
        if (v is ViewGroup) {
            val childCount = v.childCount
            /* this condition to prevent the inflating the badge more than one time on the
             menu item .. because this means that the badge is already inflated before*/
            // 3 is the NUMBER_OF_MENU_ITEM_VIEW_CHILDERN_WITH_BADGE
            if (childCount < 3) return
        }
        val itemView = v as android.support.design.internal.BottomNavigationItemView
        // remove the last child [badge view]
        itemView.removeViewAt(itemView.childCount - 1)
        ShortcutBadger.removeCount(context)
    }

}
