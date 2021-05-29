package com.kittentimer;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Kitten
{
    public enum KittenID
    {
        // Black - Gray - White
        KITTEN_1555(1555, ItemID.PET_KITTEN),
        CAT_1561(1561, ItemID.PET_CAT),

        // White
        KITTEN_1556(1556, ItemID.PET_KITTEN_1556),
        CAT_1562(1562, ItemID.PET_CAT_1562),

        // Yellow
        KITTEN_1557(1557, ItemID.PET_KITTEN_1557),
        CAT_1563(1563, ItemID.PET_CAT_1563),

        // Black
        KITTEN_1558(1558, ItemID.PET_KITTEN_1558),
        CAT_1564(1564, ItemID.PET_CAT_1565),

        // Brown
        KITTEN_1559(1559, ItemID.PET_KITTEN_1559),
        CAT_1565(1565, ItemID.PET_CAT_1565),

        // Gray
        KITTEN_1560(1560, ItemID.PET_KITTEN_1560),
        CAT_1566(1566, ItemID.PET_CAT_1566);

        private static final Map<Integer, KittenID> kittens = new HashMap<>();
        private final int id;
        private final int itemSpriteId;

        static
        {
            for (KittenID kitten : values())
            {
                kittens.put(kitten.getId(), kitten);
            }
        }

        KittenID(int id, int itemSpriteId)
        {
            this.id = id;
            this.itemSpriteId = itemSpriteId;
        }

        public int getItemSpriteId()
        {
            return itemSpriteId;
        }

        public int getId()
        {
            return id;
        }

        public static KittenID find(int id)
        {
            return kittens.get(id);
        }
    }

    public static final int HUNGER_TIME = 1440;
    public static final int HUNGER_WARN_TIME = 180;
    public static final int ATTENTION_TIME_DEFAULT = 1500;
    public static final int ATTENTION_WARN_TIME = 420;
    public static final int ATTENTION_STROKE_TIME = 1080;
    public static final int ATTENTION_2ND_STROKE_TIME = 1500;
    public static final int ATTENTION_WOOL_TIME = 3060;
    public static final int GROW_UP_TIME = 10800;

    private NPC npcKitten;
    private KittenID kittenID;
    private boolean isActive;
    private KittenGrowthTimer growthTimer;

    @Inject
    private ItemManager itemManager;

    private final Client client;
    private final KittenTimerPlugin plugin;

    public Kitten(Client client, KittenTimerPlugin plugin)
    {
        this.client = client;
        this.plugin = plugin;
        isActive = false;
    }

    public boolean getActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        setActive(active, null);
    }

    public void setActive(boolean active, NPC npcKitten)
    {
        this.npcKitten = npcKitten;
        isActive = active;
    }

    private void createTimer(Duration duration)
    {
        growthTimer = new KittenGrowthTimer(duration, itemManager.getImage(kittenID.itemSpriteId), plugin, isActive && plugin.getConfig().displayGrowUpTimer());
    }

    public KittenID getId()
    {
        return kittenID;
    }

    public long getTimeRemaining()
    {
        return Duration.between(growthTimer.getStartTime(), growthTimer.getEndTime()).getSeconds();
    }
}
