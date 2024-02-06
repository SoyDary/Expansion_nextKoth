package me.Dary.nextKoth;

import java.time.Instant;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import fr.maxlego08.zscheduler.SchedulerPlugin;
import fr.maxlego08.zscheduler.ZSchedulerManager;
import fr.maxlego08.zscheduler.api.Scheduler;

public class Placeholder extends PlaceholderExpansion {
	
	private SchedulerPlugin scheduler;
	private ZSchedulerManager manager;

	public boolean canRegister() {
		return (Bukkit.getPluginManager().getPlugin("zSchedulers") != null && Bukkit.getPluginManager().getPlugin("zSchedulers").isEnabled());
	}

	public boolean register() {
	    this.scheduler = (SchedulerPlugin)Bukkit.getPluginManager().getPlugin("zSchedulers");
	    if (this.scheduler != null && this.scheduler.isEnabled()) {
	    	manager = scheduler.getManager();
	      return super.register();
	    } 
	    return false;		
	}

	@Override
	public String getAuthor() {
		return "Dary";
	}

	@Override
	public String getIdentifier() {
		return "nextkoth";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public String getRequiredPlugin() {
		return "zSchedulers";
	}
	
	@Override
    public String onPlaceholderRequest(Player p, String id) {
		Instant current = new Date().toInstant();
		int i = 0;
		for(Scheduler scheduler : manager.getSchedulers()) {
			long time = Math.abs(current.getEpochSecond() - scheduler.getNextDate().toInstant().getEpochSecond());
			if(time < i || i == 0) i = (int) time;
		}	
		return formatSeconds(i);
	}
	
	public String formatSeconds(int seconds) {
	    int h = seconds / 3600;
	    int m = (seconds % 3600) / 60;
	    int s = seconds % 60;
	    return h > 0 ? String.format("%02d:%02d:%02d", h, m, s) : String.format("%02d:%02d", m, s);
	}
}