package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import org.bukkit.entity.EntityType;

public class EntityTypeAdaptor implements FlagAdaptor<EntityType> {
    @Override
    public EntityType from(Object object) {
        final com.sk89q.worldedit.world.entity.EntityType entityType = (com.sk89q.worldedit.world.entity.EntityType) object;
        return BukkitAdapter.adapt(entityType);
    }

    @Override
    public Object to(EntityType adapt) {
        return BukkitAdapter.adapt(adapt);
    }
}
