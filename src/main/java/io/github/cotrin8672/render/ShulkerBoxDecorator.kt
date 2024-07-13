package io.github.cotrin8672.render

import com.mojang.blaze3d.platform.Lighting
import io.github.cotrin8672.util.calcShulkerBoxFillLevel
import io.github.cotrin8672.util.containsItemTypes
import io.github.cotrin8672.util.containsOnlyOneItemType
import io.github.cotrin8672.util.isShulkerBox
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.IItemDecorator

class ShulkerBoxDecorator : IItemDecorator {
    override fun render(guiGraphics: GuiGraphics, font: Font, stack: ItemStack, xOffset: Int, yOffset: Int): Boolean {
        val pose = guiGraphics.pose()
        val minecraft = Minecraft.getInstance()
        if (stack.containsOnlyOneItemType()) {
            val displayStack = ItemStack(stack.containsItemTypes().first())
            val model = minecraft.itemRenderer.getModel(
                displayStack,
                minecraft.level,
                minecraft.player,
                16777216
            )

            pose.applyPoseContext {
                translate(xOffset + 5f, yOffset + 11f, 160f)
                scale(9f, -9f, 9f)
                val flag = !model.usesBlockLight()
                if (flag) Lighting.setupForFlatItems()

                minecraft.itemRenderer.render(
                    displayStack,
                    ItemDisplayContext.GUI,
                    false,
                    pose,
                    guiGraphics.bufferSource(),
                    15728880,
                    OverlayTexture.NO_OVERLAY,
                    model
                )

                guiGraphics.flush()
                if (flag) Lighting.setupFor3DItems()
            }
        }
        if (stack.isShulkerBox() && stack.calcShulkerBoxFillLevel() != 0.0) {
            pose.applyPoseContext {
                val barXStart = xOffset + 13
                val barYStart = yOffset + 15
                val barXEnd = barXStart + 2
                val barYEnd = barYStart - 12
                guiGraphics.fill(RenderType.guiOverlay(), barXStart, barYStart, barXEnd, barYEnd, -16777216)
                guiGraphics.fill(
                    RenderType.guiOverlay(),
                    barXStart,
                    barYStart,
                    barXEnd - 1,
                    barYStart - (12 * stack.calcShulkerBoxFillLevel()).toInt(),
                    (0xFF06b9bcL and 0xFFFFFFFFL).toInt()
                )
            }
        }
        return true
    }
}
