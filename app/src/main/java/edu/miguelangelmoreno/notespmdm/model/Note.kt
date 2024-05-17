package edu.miguelangelmoreno.notespmdm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val idNote:Int = 0,
    var title:String? = null,
    var description:String? = null,
    var date:String? = null
)
