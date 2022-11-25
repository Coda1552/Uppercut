package coda.uppercut.common.combo;

import net.minecraft.nbt.CompoundTag;

public class PlayerCombo {
    private final int MAX_COMBO = 10;
    private int combo;
    private boolean wasPrevClickRight;

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public void resetCombo() {
        this.combo = 0;
    }

    public boolean getWasPrevClickRight() {
        return wasPrevClickRight;
    }

    public void setPrevClickRight(boolean wasPrevClickRight) {
        this.wasPrevClickRight = wasPrevClickRight;
    }

    public void copyFrom(PlayerCombo source) {
        this.combo = source.combo;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("combo", combo);
        nbt.putBoolean("wasPrevClickRight", wasPrevClickRight);
    }

    public void loadNBTData(CompoundTag nbt) {
        nbt.getInt("Combo");
        nbt.getBoolean("wasPrevClickRight");
    }

}
