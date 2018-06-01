package com.jeebley.task.api.responce

import java.io.Serializable

object Response {


    data class GenericResponse(val errorCode: Int, val errorMessage: String?)
    data class StoreDetails(val responseCode: Int?, val restaurantAreaInfo: RestaurantAreaInfo?) : Serializable


    data class RestaurantAreaInfo(val restaurantCurrentStatus: String?,
                                  val rId: String?,
                                  val rInfoId: String?,
                                  val rDeliveryCharge: String?,
                                  val rMinOrderAmt: String?,
                                  val rDeliveryTime: String?,
                                  val jDeliveryCharge: String?,
                                  val jDeliveryTime: String?,
                                  val rImage: String?,
                                  val rPriCuisineId: String?,
                                  val rCuisine2: String?,
                                  val rCuisine3: String?,
                                  val delvType: String?,
                                  val typeId: String?,
                                  val rName: String?,
                                  val rhaveDelivery: String?,
                                  val rPaymentId: String?,
                                  val branchStatus: String?,
                                  val areaName: String?,
                                  val workingHour: String?,
                                  val cntrCurrency: String?,
                                  val cuisineTitle1: String?,
                                  val cuisineTitle2: Any?,
                                  val cuisineTitle3: Any?,
                                  val orgImage: String?,
                                  val currStatus: Int?,
                                  val paymentType: List<PaymentType>?,
                                  val hasOffer: Int?,
                                  val openingStatus: String?) : Serializable

    data class PaymentType(val paymentMethod: String?, val image: String?) : Serializable

    data class ListCategory(
            val categoryArray: List<CategoryArray>
    )



    data class CategoryArray(
            val menuName_eng: String,
            val menuName_arb: String,
            val menuCatId: String,
            val mastMId: String,
            val isOffer: Boolean,
            val menuArray: List<MenuArray>,
            var isSelected: Boolean = false
    )

    data class MenuArray(
            val itemId: String,
            val itemResId: String,
            val itemFoodType: String,
            val itemCatId: String,
            val itemName_eng: String,
            val itemName_arb: String,
            val itemDesc_eng: String,
            val itemDesc_arb: String,
            val itemDet_eng: String,
            val itemDet_arb: String,
            val itemMinQty: String,
            val itemProt: String,
            val itemCarb: String,
            val itemFat: String,
            val calories: Int,
            val itemImage: String,
            val logoImage: String,
            val logoImage1: String,
            val logoImage2: String,
            val isType: String,
            val itemPrice: String,
            val isPromo: String,
            val isRecomm: String,
            val isMostSell: String,
            val status: String,
            val isSoldout: String,
            val orgitemImage: String,
            val itemImage_2: String,
            val orgitemImage_2: String,
            val itemImage_3: String,
            val orgitemImage_3: String,
            val choiceArray: List<Any>
    )

}