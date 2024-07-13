package io.github.cotrin8672.util

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.neoforge.capabilities.Capabilities

fun ItemStack.containsOnlyOneItemType(): Boolean {
    return this.containsItemTypes().size == 1
}

fun ItemStack.containsItemTypes(): Set<Item> {
    if (!this.isShulkerBox()) return setOf()
    val cap = this.getCapability(Capabilities.ItemHandler.ITEM) ?: return setOf()

    val containsItemSet = mutableSetOf<Item>()
    for (index in 0 until cap.slots) {
        if (!cap.getStackInSlot(index).isEmpty) containsItemSet.add(cap.getStackInSlot(index).item)
    }
    return containsItemSet.toSet()
}

fun ItemStack.calcShulkerBoxFillLevel(): Double {
    if (!this.isShulkerBox()) return 0.0
    val cap = this.getCapability(Capabilities.ItemHandler.ITEM) ?: return 0.0
    val maxItemSize = 64 * cap.slots
    var containsItemSize = 0
    for (index in 0 until cap.slots) {
        val stack = cap.getStackInSlot(index)
        containsItemSize += (64 / stack.maxStackSize) * stack.count
    }
    return (containsItemSize.toDouble() / maxItemSize.toDouble())
}

fun ItemStack.isShulkerBox(): Boolean {
    return shulkerBoxItemInstanceList.contains(this.item)
}

val shulkerBoxItemInstanceList = listOf(
    Items.SHULKER_BOX,
    Items.WHITE_SHULKER_BOX,
    Items.ORANGE_SHULKER_BOX,
    Items.MAGENTA_SHULKER_BOX,
    Items.LIGHT_BLUE_SHULKER_BOX,
    Items.YELLOW_SHULKER_BOX,
    Items.LIME_SHULKER_BOX,
    Items.PINK_SHULKER_BOX,
    Items.GRAY_SHULKER_BOX,
    Items.LIGHT_GRAY_SHULKER_BOX,
    Items.CYAN_SHULKER_BOX,
    Items.PURPLE_SHULKER_BOX,
    Items.BLUE_SHULKER_BOX,
    Items.BROWN_SHULKER_BOX,
    Items.GREEN_SHULKER_BOX,
    Items.RED_SHULKER_BOX,
    Items.BLACK_SHULKER_BOX
)
