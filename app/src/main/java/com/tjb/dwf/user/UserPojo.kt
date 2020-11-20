package com.tjb.dwf.user

import java.io.Serializable

data class UserPojo(val username: String, val token: String, val id: Int) : Serializable