package io.github.cotrin8672.render

import com.mojang.blaze3d.vertex.PoseStack

fun <T> PoseStack.applyPoseContext(block: PoseStack.() -> T): T {
    this.pushPose()
    val result = this.block()
    this.popPose()
    return result
}
