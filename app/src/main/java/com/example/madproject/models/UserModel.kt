package com.example.madproject.models

class UserModel {

    private var userName:String = ""
    private var password:String = ""
    private var name:String = ""

    constructor(userN:String, nam:String, passwd:String) {
        userName = userN
        name = nam;
        password = passwd
    }

    constructor(userN:String) {
        userName = userN
    }

    fun setUserName(userNa:String) {
        userName = userNa
    }

    fun setName(Na:String) {
        name = Na
    }

    fun setPassword(passwd:String) {
        password = passwd
    }

    fun getUserName():String {
        return userName
    }

    fun getPassword():String {
        return password
    }

    fun getName():String {
        return name
    }
}