package com.bizpro.commondialoglibrary

data class MessageData(val title: String, val message: String, val positiveTitle: String = "OK", val negativeTitle: String = "CANCEL", val isCancelable: Boolean = true) {
    constructor(title: String, message: String) : this(title, message, "OK")
    constructor(title: String, message: String, positiveTitle: String) : this(title, message, positiveTitle, "CANCEL", true)
    constructor(title: String, message: String, isCancelable: Boolean) : this(title, message, "OK", "CANCEL", isCancelable)
    constructor(title: String, message: String, positiveTitle: String, negativeTitle: String) : this(title, message, positiveTitle, negativeTitle, true)
}
