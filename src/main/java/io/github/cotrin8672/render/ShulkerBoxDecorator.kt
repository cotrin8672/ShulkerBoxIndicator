package io.github.cotrin8672.render

import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.vertex.PoseStack
import io.github.cotrin8672.util.calcShulkerBoxFillLevel
import io.github.cotrin8672.util.containsItemTypes
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
        when (stack.containsItemTypes().size) {
            1 -> {
                val displayStack = ItemStack(stack.containsItemTypes().first())
                with(pose) {
                    with(minecraft) {
                        with(guiGraphics) {
                            renderItemModel(xOffset + 5f, yOffset + 11f, 9f, displayStack)
                        }
                    }
                }
            }

            2 -> {
                val firstDisplayStack = ItemStack(stack.containsItemTypes().first())
                val secondDisplayStack = ItemStack(stack.containsItemTypes().last())
                with(pose) {
                    with(minecraft) {
                        with(guiGraphics) {
                            renderItemModel(xOffset + 3f, yOffset + 11f, 6f, firstDisplayStack)
                            renderItemModel(xOffset + 9.5f, yOffset + 11f, 6f, secondDisplayStack)
                        }
                    }
                }
            }

            3 -> {
                val firstDisplayStack = ItemStack(stack.containsItemTypes().first())
                val secondDisplayStack = ItemStack(stack.containsItemTypes().elementAt(1))
                val thirdDisplayStack = ItemStack(stack.containsItemTypes().elementAt(2))
                with(pose) {
                    with(minecraft) {
                        with(guiGraphics) {
                            renderItemModel(xOffset + 3f, yOffset + 13f, 6f, firstDisplayStack)
                            renderItemModel(xOffset + 9.5f, yOffset + 13f, 6f, secondDisplayStack)
                            renderItemModel(xOffset + 6.25f, yOffset + 6.5f, 6f, thirdDisplayStack)
                        }
                    }
                }
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

    context(PoseStack, Minecraft, GuiGraphics)
    private fun renderItemModel(
        x: Float,
        y: Float,
        scale: Float,
        stack: ItemStack,
    ) {
        val model = itemRenderer.getModel(stack, level, player, 16777216)
        applyPoseContext {
            translate(x, y, 160f)
            scale(scale, -scale, scale)
            val flag = !model.usesBlockLight()
            if (flag) Lighting.setupForFlatItems()

            itemRenderer.render(
                stack,
                ItemDisplayContext.GUI,
                false,
                this,
                bufferSource(),
                15728880,
                OverlayTexture.NO_OVERLAY,
                model
            )

            flush()
            if (flag) Lighting.setupFor3DItems()
        }
    }
}
