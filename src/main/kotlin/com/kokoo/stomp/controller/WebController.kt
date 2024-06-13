package com.kokoo.stomp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class WebController {

    @GetMapping("/{roomId}")
    fun getIndex(
        @PathVariable(name = "roomId") roomId: Long,
        modelAndView: ModelAndView
    ): ModelAndView {
        modelAndView.viewName = "index"
        modelAndView.addObject("roomId", roomId)

        return modelAndView
    }
}