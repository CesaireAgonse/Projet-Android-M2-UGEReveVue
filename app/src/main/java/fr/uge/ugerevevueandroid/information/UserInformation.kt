package fr.uge.ugerevevueandroid.information

data class UserInformation(val id:Long, val username:String, val followed: Set<UserInformation>?, val isAdmin:Boolean)
