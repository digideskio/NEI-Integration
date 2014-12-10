package tonius.neiintegration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tonius.neiintegration.config.Config;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemTooltipHandler {
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent evt) {
        if (Config.internalName && (!Config.internalNameShift || Utils.isShiftKeyDown()) && (!Config.internalNameAdvanced || evt.showAdvancedItemTooltips)) {
            evt.toolTip.add(EnumChatFormatting.DARK_GRAY + Item.itemRegistry.getNameForObject(evt.itemStack.getItem()));
        }
        
        if (Config.burnTime && (!Config.burnTimeShift || Utils.isShiftKeyDown()) && (!Config.burnTimeAdvanced || evt.showAdvancedItemTooltips)) {
            int burnTime = TileEntityFurnace.getItemBurnTime(evt.itemStack);
            if (burnTime > 0) {
                evt.toolTip.add(Utils.translate("tooltip.burntime") + " " + burnTime + " " + Utils.translate("ticks"));
            }
        }
        
        if (Config.oreDictNames && (!Config.oreDictNamesShift || Utils.isShiftKeyDown()) && (!Config.oreDictNamesAdvanced || evt.showAdvancedItemTooltips)) {
            List<String> names = new ArrayList<String>();
            for (int id : OreDictionary.getOreIDs(evt.itemStack)) {
                String name = OreDictionary.getOreName(id);
                if (!names.contains(name)) {
                    names.add("  " + EnumChatFormatting.GRAY + name);
                } else {
                    names.add("  " + EnumChatFormatting.DARK_GRAY + name);
                }
            }
            Collections.sort(names);
            if (!names.isEmpty()) {
                evt.toolTip.add(EnumChatFormatting.GRAY + Utils.translate("tooltip.oredict"));
                evt.toolTip.addAll(names);
            }
        }
        
        if (Config.fluidRegInfo && (!Config.fluidRegInfoShift || Utils.isShiftKeyDown()) && (!Config.fluidRegInfoAdvanced || evt.showAdvancedItemTooltips)) {
            List<String> names = new ArrayList<String>();
            if (FluidContainerRegistry.isEmptyContainer(evt.itemStack)) {
                names.add("  " + Utils.translate("tooltip.fluidreg.empty"));
            } else {
                FluidStack fluid = Utils.getFluidStack(evt.itemStack);
                if (fluid != null) {
                    names.add("  " + fluid.getLocalizedName());
                    names.add("  " + fluid.amount + " mB");
                }
            }
            if (!names.isEmpty()) {
                evt.toolTip.add(EnumChatFormatting.GRAY + Utils.translate("tooltip.fluidreg"));
                evt.toolTip.addAll(names);
            }
        }
    }
    
}
