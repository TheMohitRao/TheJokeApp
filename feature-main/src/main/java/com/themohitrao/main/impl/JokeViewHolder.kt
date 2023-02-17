package com.themohitrao.main.impl

import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.core_ui.BaseViewHolder
import com.themohitrao.core_ui.animationList
import com.themohitrao.core_ui.playViaUrl
import com.themohitrao.feature_main.databinding.CellUserInfoBinding

class JokeViewHolder(
    private val cellUserInfoBinding: CellUserInfoBinding
) : BaseViewHolder(cellUserInfoBinding.root) {

    fun setValues(data: JokeDataModel) {
        setData(data)
        playAnimation()
    }

    private fun setData(data: JokeDataModel) {
        cellUserInfoBinding.jokeContent.text = data.content
    }

    private fun playAnimation() {
        cellUserInfoBinding.animationView1.playViaUrl(animationList.random())
    }
}