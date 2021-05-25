package com.catbreeder;

import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Player;

public class Kitten
{
    private Player owner;
    private Actor actor;
    private boolean isActive;

    private final Client client;
    private final CatBreederPlugin plugin;

    public Kitten(Client client, CatBreederPlugin plugin)
    {
        this.client = client;
        this.plugin = plugin;
    }

    public boolean getActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        setActive(active, null, null);
    }

    public void setActive(boolean active, Player owner, Actor actor)
    {
        this.owner = owner;
        this.actor = actor;
        isActive = active;
    }

    public long getTimeRemaining()
    {
        return 0;
    }
}
