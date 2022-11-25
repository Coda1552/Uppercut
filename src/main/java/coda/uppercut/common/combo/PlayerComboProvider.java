package coda.uppercut.common.combo;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PlayerComboProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerCombo> PLAYER_COMBO = CapabilityManager.get(new CapabilityToken<PlayerCombo>(){});
    private PlayerCombo combo = null;
    private final LazyOptional<PlayerCombo> optional = LazyOptional.of(this::createPlayerCombo);

    private PlayerCombo createPlayerCombo() {
        if (combo == null) {
            combo = new PlayerCombo();
        }

        return combo;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_COMBO) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCombo().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCombo().loadNBTData(nbt);
    }
}
