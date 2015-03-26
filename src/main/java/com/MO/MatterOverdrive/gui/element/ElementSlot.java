package com.MO.MatterOverdrive.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import com.MO.MatterOverdrive.Reference;
import com.MO.MatterOverdrive.data.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Simeon on 3/20/2015.
 */
public class ElementSlot extends ElementBase
{
    private boolean big;
    protected static final ResourceLocation slot_big = new ResourceLocation(Reference.PATH_ELEMENTS + "side_slot_bg.png");
    protected static final ResourceLocation slot_small = new ResourceLocation(Reference.PATH_ELEMENTS + "slot_bg.png");

    public ElementSlot(GuiBase gui, int posX, int posY,boolean big) {
        super(gui, posX, posY);
        this.texture = slot_big;
        this.big = big;
    }

    public ElementSlot(GuiBase gui,boolean big, net.minecraft.inventory.Slot containerSlot) {
        super(gui, containerSlot.xDisplayPosition, containerSlot.yDisplayPosition);
        this.texture = slot_big;
        this.big = big;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks)
    {
        if(big)
        {
            gui.bindTexture(slot_big);
            gui.drawSizedTexturedModalRect(this.posX - 3,this.posY - 3,0,0,22,22,22,22);
        }
        else
        {
            gui.bindTexture(slot_small);
            gui.drawSizedTexturedModalRect(this.posX - 2,this.posY - 2,0,0,18,18,18,18);
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    public void drawSlotIcon(Slot slot,int x,int y)
    {
        if(slot != null && slot.getTexture() != null)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
            gui.bindTexture(slot.getTexture());
            gui.drawSizedTexturedModalRect(x, y,0,0,16,16,16,16);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}