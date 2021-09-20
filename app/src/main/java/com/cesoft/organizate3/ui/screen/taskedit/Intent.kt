package com.cesoft.organizate3.ui.screen.taskedit

enum class Field {
    Name, Description, Type, DueDate, Done, Priority, LatLon, Radius
}

sealed class Intent {
    data class Init(val id: Int?=null): Intent()
    object Save: Intent()
    object Done: Intent()
    data class ChangeField(val field: Field, val value: Any?): Intent()
}
