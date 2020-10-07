package matteroverdrive.items.weapon.module;

import matteroverdrive.Reference;
import matteroverdrive.api.weapon.IWeaponScope;
import matteroverdrive.items.IAdvancedModelProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class WeaponModuleTestSights extends WeaponModuleTestBase implements IWeaponScope, IAdvancedModelProvider {
    public static final String[] subItemNames = {"normal", "small", "wide"};

    public WeaponModuleTestSights(String name) {
        super(name);
        applySlot(Reference.MODULE_SIGHTS);
        this.setHasSubtypes(true);
    }

    @Override
    public String[] getSubNames() {
        return subItemNames;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs creativeTabs, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(creativeTabs)) {
            for (int i=0; i<subItemNames.length; i++) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Nonnull
    @Override
    public String getTranslationKey(ItemStack stack) {
        int i = MathHelper.clamp(stack.getItemDamage(), 0, 3);
        return super.getTranslationKey() + "." + subItemNames[i];
    }

    @Override
    public String getModelPath() {
        return null;
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack module) {
        return new ResourceLocation(Reference.PATH_ELEMENTS + String.format("holo_sight_%s.png", module.getItemDamage()));
    }

    @Override
    public String getModelName(ItemStack module) {
        return null;
    }

    @Override
    public float getZoomAmount(ItemStack scopeStack, ItemStack weaponStack) {
        return 0.3f;
    }

    @Override
    public float getAccuracyModify(ItemStack scopeStack, ItemStack weaponStack, boolean zoomed, float originalAccuracy) {
        if (zoomed) {
            return originalAccuracy * 0.6f;
        }
        return originalAccuracy * 0.8f;
    }
}
