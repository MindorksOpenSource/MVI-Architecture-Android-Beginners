package com.mindorks.framework.mvi.ui.main.dataholder

import com.mindorks.framework.mvi.data.model.User

data class MainDataHolder(

    var users: List<User>? = null

) {
    override fun toString(): String {

        return "MainDataHolder(users=$users)"
    }
}