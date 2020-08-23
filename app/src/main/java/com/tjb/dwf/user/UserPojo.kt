package com.tjb.dwf.user

import java.io.Serializable

data class UserPojo(val firstName: String, val lastName: String, val token: String, val id: Int) : Serializable