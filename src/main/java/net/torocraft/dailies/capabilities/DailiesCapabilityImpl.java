package net.torocraft.dailies.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.torocraft.dailies.quests.DailyQuest;
import net.torocraft.dailies.quests.IDailyQuest;
import net.torocraft.dailies.quests.Reward;
import net.torocraft.dailies.quests.TypedInteger;

public class DailiesCapabilityImpl implements IDailiesCapability {

	private List<IDailyQuest> quests;

	@Override
	public boolean gather(EntityPlayer player, EntityItem item, int count) {

		List<IDailyQuest> completedQuests = new ArrayList<IDailyQuest>();

		boolean hit = false;

		for (IDailyQuest quest : quests) {
			if (quest.gather(player, item, count)) {

				if (quest.isComplete()) {
					quest.reward(player);
					completedQuests.add(quest);
				}

				hit = true;

				break;
			}
		}

		for (IDailyQuest quest : completedQuests) {
			quests.remove(quest);
		}

		return hit;
	}

	@Override
	public boolean hunt(EntityPlayer player, EntityLiving mob, int count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NBTTagCompound writeNBT() {
		NBTTagList list = new NBTTagList();
		for (IDailyQuest quest : quests) {
			list.appendTag(quest.writeNBT());
		}

		NBTTagCompound c = new NBTTagCompound();
		c.setTag("quests", list);

		return c;
	}

	@Override
	public void readNBT(NBTTagCompound b) {
		quests = new ArrayList<IDailyQuest>();

		if (b == null || !(b.getTag("quests") instanceof NBTTagList)) {
			setDefaultQuests();
			return;
		}

		NBTTagList list = (NBTTagList) b.getTag("quests");

		if (list == null) {
			setDefaultQuests();
			return;
		}

		for (int i = 0; i < list.tagCount(); i++) {
			DailyQuest quest = new DailyQuest();
			quest.readNBT(list.getCompoundTagAt(i));
			quests.add(quest);
		}

		if (quests.size() < 1) {
			setDefaultQuests();
		}
	}

	private void setDefaultQuests() {
		quests = new ArrayList<IDailyQuest>();
		DailyQuest quest = new DailyQuest();
		Reward reward = new Reward();
		reward.quantity = 1000;
		reward.type = Reward.XP;
		TypedInteger target = new TypedInteger();
		target.type = 12;
		target.quantity = 10;
		quest.type = "gather";
		quest.reward = reward;
		quest.target = target;
		quests.add(quest);
	}


}