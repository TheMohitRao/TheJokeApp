package com.themohitrao.main.impl

import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.core_ui.*
import com.themohitrao.feature_main.databinding.CellUserInfoBinding

class JokeViewHolder(
    private val cellUserInfoBinding: CellUserInfoBinding
) : BaseViewHolder(cellUserInfoBinding.root) {

    fun setValues(data: JokeDataModel) {
        cellUserInfoBinding.jokeContent.text = data.content
        playAnimation()
        if(adapterPosition%3 == 0){
            cellUserInfoBinding.animationView1.playSafeAnimation()
        }
    }

    private fun playAnimation() {
        when(adapterPosition){
            0 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_ONE)
            1 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_TWO)
            2 -> cellUserInfoBinding.animationView1.playViaUrl(URL_FALLING_HEARTS)
            3 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_ONE)
            4 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_TWO)
            5 -> cellUserInfoBinding.animationView1.playViaUrl(URL_FALLING_HEARTS)
            6 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_ONE)
            7 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_TWO)
            8 -> cellUserInfoBinding.animationView1.playViaUrl(URL_FALLING_HEARTS)
            9 -> cellUserInfoBinding.animationView1.playViaUrl(URL_SHOOTING_ROCKETS_TWO)
        }
    }
}