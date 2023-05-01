package com.example.madproject.models

import com.example.madproject.Validations.ValidationResult

class UserData {

    private var userName = ""
    private var password = ""
    private var name = ""
    private var rePassword = ""

    constructor(userN:String) {
        userName = userN
    }

    constructor(userN:String, passwd:String) {
        userName = userN
        password = passwd
    }

    constructor(nam:String, passwd:String, rePass:String) {
        name = nam;
        password = passwd
        rePassword = rePass
    }

    constructor(userN:String, nam:String, passwd:String, rePass:String) {
        userName = userN
        name = nam;
        password = passwd
        rePassword = rePass
    }

    fun validateUserName():ValidationResult {
        return if(userName.isEmpty())
            ValidationResult.Empty("User Name can not be empty")
        else
            if(userName.length != 10)
                ValidationResult.Invalid("Number length should be 10")
        else {
                try {
                    userName.toDouble()
                    ValidationResult.Valid
                }
                catch (e: java.lang.Exception) {
                    ValidationResult.Invalid("Number should be digit")
                }
        }
    }

    fun validatePassword():ValidationResult {
        return if(password.isEmpty())
            ValidationResult.Empty("Password can not be empty")
        else
            if(password.length < 10)
                ValidationResult.Invalid("Password minimum length should be 10")
            else
            ValidationResult.Valid
    }

    fun validateName():ValidationResult {
        return if(name.isEmpty())
            ValidationResult.Empty("Name can not be empty")
        else
            ValidationResult.Valid
    }

    fun validateRePassword():ValidationResult {
        return if(rePassword.isEmpty())
            ValidationResult.Empty("Re password can not be empty")
        else
            if(password != rePassword)
                ValidationResult.Invalid("Password and Re type password are miss matched")
            else
                ValidationResult.Valid
    }
}