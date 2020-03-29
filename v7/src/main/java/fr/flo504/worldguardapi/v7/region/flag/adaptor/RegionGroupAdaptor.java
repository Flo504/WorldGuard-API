package fr.flo504.worldguardapi.v7.region.flag.adaptor;

import fr.flo504.worldguardapi.api.region.flag.adaptor.FlagAdaptor;
import fr.flo504.worldguardapi.api.region.flag.type.RegionGroup;

public class RegionGroupAdaptor implements FlagAdaptor<RegionGroup> {
    @Override
    public RegionGroup from(Object object) {
        final com.sk89q.worldguard.protection.flags.RegionGroup wgRegionGroup = (com.sk89q.worldguard.protection.flags.RegionGroup) object;
        switch (wgRegionGroup){
            case MEMBERS:
                return RegionGroup.MEMBERS;
            case OWNERS:
                return RegionGroup.OWNERS;
            case NON_MEMBERS:
                return RegionGroup.NON_MEMBERS;
            case NON_OWNERS:
                return RegionGroup.NON_OWNERS;
            case NONE:
                return RegionGroup.NONE;
            default:
                return RegionGroup.ALL;
        }
    }

    @Override
    public com.sk89q.worldguard.protection.flags.RegionGroup to(RegionGroup adapt) {
        switch (adapt){
            case MEMBERS:
                return com.sk89q.worldguard.protection.flags.RegionGroup.MEMBERS;
            case OWNERS:
                return com.sk89q.worldguard.protection.flags.RegionGroup.OWNERS;
            case NON_MEMBERS:
                return com.sk89q.worldguard.protection.flags.RegionGroup.NON_MEMBERS;
            case NON_OWNERS:
                return com.sk89q.worldguard.protection.flags.RegionGroup.NON_OWNERS;
            case NONE:
                return com.sk89q.worldguard.protection.flags.RegionGroup.NONE;
            default:
                return com.sk89q.worldguard.protection.flags.RegionGroup.ALL;
        }
    }
}
