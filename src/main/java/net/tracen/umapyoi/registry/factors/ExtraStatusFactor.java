package net.tracen.umapyoi.registry.factors;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class ExtraStatusFactor extends UmaFactor {

    private final int statusType;

    public ExtraStatusFactor(int type) {
        super(FactorType.EXTRASTATUS);
        this.statusType = type;
    }

    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        int level = stack.getLevel();
        // 0:physique, 1:talent, 2:memory, 3:ExtraAP

        if (level != 0) {
            switch (this.statusType) {
            case 0 -> {
                int newPhysique = UmaSoulUtils.getPhysique(soul) + level;
                if (newPhysique >= 4) {
                    UmaSoulUtils.setPhysique(soul, 5);
                } else {
                    UmaSoulUtils.setPhysique(soul, newPhysique);
                }
            }
            case 1 -> UmaSoulUtils.setLearningTimes(soul, UmaSoulUtils.getLearningTimes(soul) + level);
            case 2 -> UmaSoulUtils.setSkillSlots(soul, Math.min(18, UmaSoulUtils.getSkillSlots(soul) + level));
            case 3 -> UmaSoulUtils.setExtraActionPoint(soul, UmaSoulUtils.getExtraActionPoint(soul) + level * 100);
            default ->
                throw new IllegalArgumentException("Unexpected value: " + this.statusType);
            }
        }
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
