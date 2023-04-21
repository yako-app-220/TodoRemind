package com.yako.todoreminder

import androidx.room.*
import java.util.Date


@Entity(tableName = "todo")
data class ToDoItem (
    @PrimaryKey(autoGenerate = true)val id: Long,
    var name:String="",
    //var tag: ,
    var why:String="",
    var memo:String="",

    //statusはTodoの状況
    //1L:Stock
    //2L:Play
    //3L:Old
    var status:Long=0L,
    var weight:Long=0L,
    //var checkText: ,
    //var checkValue:,
    val finishDate :Date?=null,

    var organizeDate :Date?=null,

    //1L:時間指定
    //2L:ターゲットのdo起因
    //3L:ターゲットのlimit起因
    //4L:ターゲットのdelete起因
    //5L:ターゲットのtime起因
    //6L:ターゲットのplay起因
    //7L:ターゲットのfinish起因
    var doType :Long=0L,
    var doRemind :Long=0L,
    var doDate :Date?,
    var doId :Long=0L,
    var doAdd :Long=0L,

    var limitType :Long=0L,
    var limitRemind :Long=0L,
    var limitDate: Date? = null,
    var limitId :Long=0L,
    var limitAdd :Long=0L,

    var deleteType :Long=0L,
    var deleteRemind :Long=0L,
    var deleteDate :Date?=null,
    var deleteId :Long=0L,
    var deleteAdd:Long=0L,

    var timeRemind :Long=0L,
    var timePlan:Long=0L,
    var timeOld:Long=0L,
    var timeDate :Date?=null,

    //todo maker
    var makerId :Long=0L,
    var makerName:String="",
    var groupId :Long=0L,

    //todo action
    var actionValue:Long=0L,
    var actionText:String="",
    //var actionMakerId:,

    //todo dream
    var dreamId:Long=0L,

    //todo nextPrev
    var playNextId:Long=0L,
    var playPrevId:Long=0L,
    var groupNextId:Long=0L,
    var groupPrevId:Long=0L,

    //todo multiUser
    var type:Long=0L,
    var createDate :Date?=null,
    var createUser:Long=0L,
    //var personnelUser:
    var finishUser:Long=0L,)
    //var editUser:,)
