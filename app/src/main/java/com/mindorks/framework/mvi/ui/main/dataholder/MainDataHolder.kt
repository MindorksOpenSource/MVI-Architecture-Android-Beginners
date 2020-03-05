package com.mindorks.framework.mvi.ui.main.dataholder

import com.mindorks.framework.mvi.data.model.User

data class MainDataHolder(val users: List<User>) {

    override fun toString(): String {
        return "MainDataHolder(users=$users)"
    }

}