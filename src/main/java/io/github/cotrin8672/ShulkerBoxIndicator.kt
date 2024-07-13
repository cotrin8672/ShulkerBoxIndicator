package io.github.cotrin8672

import io.github.cotrin8672.render.ShulkerBoxDecorator
import io.github.cotrin8672.util.shulkerBoxItemInstanceList
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(ShulkerBoxIndicator.MOD_ID)
class ShulkerBoxIndicator {
    companion object {
        const val MOD_ID = "shulkerboxindicator"
    }

    init {
        MOD_BUS.addListener(this::onRegisterItemDecorationsEvent)
    }

    @SubscribeEvent
    fun onRegisterItemDecorationsEvent(event: RegisterItemDecorationsEvent) {
        for (shulkerBox in shulkerBoxItemInstanceList) {
            event.register(shulkerBox, ShulkerBoxDecorator())
        }
    }
}
