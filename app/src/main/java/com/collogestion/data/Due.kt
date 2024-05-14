package com.collogestion.data

import com.google.gson.annotations.SerializedName

class Due (
    @SerializedName("id") var id : Int,
    @SerializedName("amount") var amount : Double,
    @SerializedName("creditor_id") var creditorId : Int,
    @SerializedName("debtor_id") var debtorId : Int,
    @SerializedName("house_share_id") var houseShareId : Int
)

