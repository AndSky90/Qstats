package com.i550.qstats.Model;

public class DataGlobal {
    public DataGlobal() { }

    private static final String TAG = "qS";
    private TotalChampionusage totalChampionusage = new TotalChampionusage();
    private TotalDeaths totalDeaths = new TotalDeaths();
    private ChangeChampionusage changeChampionusage = new ChangeChampionusage();
    private ChangeDeaths changeDeaths = new ChangeDeaths();

    public String getTotalChampionusage() {
        String z;
        if (totalChampionusage != null) {
            z = totalChampionusage.toString();
        } else
            z = "loading..";
        return z;
    }

    public String getTotalDeaths() {
        return totalDeaths.toString();
    }

    public String getChangeChampionusage() {
        return changeChampionusage.toString();
    }

    public ChangeDeaths getChangeDeaths() {
        return changeDeaths;
    }

    public void setTotalChampionusage(TotalChampionusage totalChampionusage) {
        this.totalChampionusage = totalChampionusage;
    }

    public void setTotalDeaths(TotalDeaths totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public void setChangeChampionusage(ChangeChampionusage changeChampionusage) {
        this.changeChampionusage = changeChampionusage;
    }

    public void setChangeDeaths(ChangeDeaths changeDeaths) {
        this.changeDeaths = changeDeaths;
    }

}


class ChangeChampionusage {
    private String KEEL;
    private String DEATH_KNIGHT;
    private String DOOM_SLAYER;
    private String STROGG;
    private String ANARKI;
    private String BJ_BLAZKOWICZ;
    private String CLUTCH;
    private String SCALEBEARER;
    private String SLASH;
    private String NYX;
    private String GALENA;
    private String RANGER;
    private String SORLAG;
    private String VISOR;
    private String ATHENA;


    public String getKEEL() {
        return KEEL;
    }

    public String getDEATH_KNIGHT() {
        return DEATH_KNIGHT;
    }

    public String getDOOM_SLAYER() {
        return DOOM_SLAYER;
    }

    public String getSTROGG() {
        return STROGG;
    }

    public String getANARKI() {
        return ANARKI;
    }

    public String getBJ_BLAZKOWICZ() {
        return BJ_BLAZKOWICZ;
    }

    public String getCLUTCH() {
        return CLUTCH;
    }

    public String getSCALEBEARER() {
        return SCALEBEARER;
    }

    public String getSLASH() {
        return SLASH;
    }

    public String getNYX() {
        return NYX;
    }

    public String getGALENA() {
        return GALENA;
    }

    public String getRANGER() {
        return RANGER;
    }

    public String getSORLAG() {
        return SORLAG;
    }

    public String getVISOR() {
        return VISOR;
    }

    public String getATHENA() {
        return ATHENA;
    }

    ChangeChampionusage() {
    }

    @Override
    public String toString() {
        return "ChangeChampionusage{" +
                "KEEL='" + KEEL + '\'' +
                ", DEATH_KNIGHT='" + DEATH_KNIGHT + '\'' +
                ", DOOM_SLAYER='" + DOOM_SLAYER + '\'' +
                ", STROGG='" + STROGG + '\'' +
                ", ANARKI='" + ANARKI + '\'' +
                ", BJ_BLAZKOWICZ='" + BJ_BLAZKOWICZ + '\'' +
                ", CLUTCH='" + CLUTCH + '\'' +
                ", SCALEBEARER='" + SCALEBEARER + '\'' +
                ", SLASH='" + SLASH + '\'' +
                ", NYX='" + NYX + '\'' +
                ", GALENA='" + GALENA + '\'' +
                ", RANGER='" + RANGER + '\'' +
                ", SORLAG='" + SORLAG + '\'' +
                ", VISOR='" + VISOR + '\'' +
                ", ATHENA='" + ATHENA + '\'' +
                '}';
    }
}

class ChangeDeaths {
    ChangeDeaths() {}
    private String GrenadeSwarm;
    private String DireOrb;
    private String WallOfFire;
    private String SHOTGUN;
    private String Vendetta;
    private String BullRush;
    private String LAGBOLT;
    private String GAUNTLET;
    private String RAILGUN;
    private String Berserk;
    private String PlasmaTrail;
    private String NAILGUN;
    private String DRONE_PLASMA_GUN;
    private String DOMAIN;
    private String MINING_LASER;
    private String MACHINEGUN;
    private String ReconDrone;
    private String AcidSpit;
    private String UnholyTotem;
    private String LIGHTNING_GUN;
    private String ROCKET_LAUNCHER;
    private String GrapplingHook;

    public String getGrenadeSwarm() {
        return GrenadeSwarm;
    }

    public String getDireOrb() {
        return DireOrb;
    }

    public String getWallOfFire() {
        return WallOfFire;
    }

    public String getSHOTGUN() {
        return SHOTGUN;
    }

    public String getVendetta() {
        return Vendetta;
    }

    public String getBullRush() {
        return BullRush;
    }

    public String getLAGBOLT() {
        return LAGBOLT;
    }

    public String getGAUNTLET() {
        return GAUNTLET;
    }

    public String getRAILGUN() {
        return RAILGUN;
    }

    public String getBerserk() {
        return Berserk;
    }

    public String getPlasmaTrail() {
        return PlasmaTrail;
    }

    public String getNAILGUN() {
        return NAILGUN;
    }

    public String getDRONE_PLASMA_GUN() {
        return DRONE_PLASMA_GUN;
    }

    public String getDOMAIN() {
        return DOMAIN;
    }

    public String getMINING_LASER() {
        return MINING_LASER;
    }

    public String getMACHINEGUN() {
        return MACHINEGUN;
    }

    public String getReconDrone() {
        return ReconDrone;
    }

    public String getAcidSpit() {
        return AcidSpit;
    }

    public String getUnholyTotem() {
        return UnholyTotem;
    }

    public String getLIGHTNING_GUN() {
        return LIGHTNING_GUN;
    }

    public String getROCKET_LAUNCHER() {
        return ROCKET_LAUNCHER;
    }

    public String getGrapplingHook() {
        return GrapplingHook;
    }
}

class TotalChampionusage {
    TotalChampionusage() {}

    private String GALENA;
    private String RANGER;
    private String DEATH_KNIGHT;
    private String VISOR;
    private String DOOM_SLAYER;
    private String KEEL;
    private String ANARKI;
    private String SLASH;
    private String NYX;
    private String STROGG;
    private String SORLAG;
    private String SCALEBEARER;
    private String BJ_BLAZKOWICZ;
    private String CLUTCH;
    private String ATHENA;

}

class TotalDeaths {

    private String ROCKET_LAUNCHER;
    private String GrenadeSwarm;
    private String AcidSpit;
    private String GAUNTLET;
    private String DireOrb;
    private String WallOfFire;
    private String SHOTGUN;
    private String BullRush;
    private String ReconDrone;
    private String PlasmaTrail;
    private String RAILGUN;
    private String Berserk;
    private String UnholyTotem;
    private String MINING_LASER;
    private String LIGHTNING_GUN;
    private String MACHINEGUN;
    private String Vendetta;
    private String LAGBOLT;
    private String NAILGUN;
    private String DRONE_PLASMA_GUN;
    private String DOMAIN;
    private String GrapplingHook;

    TotalDeaths() {
    }

    public TotalDeaths(String ROCKET_LAUNCHER, String grenadeSwarm, String acidSpit, String GAUNTLET, String direOrb, String wallOfFire, String SHOTGUN, String bullRush, String reconDrone, String plasmaTrail, String RAILGUN, String berserk, String unholyTotem, String MINING_LASER, String LIGHTNING_GUN, String MACHINEGUN, String vendetta, String LAGBOLT, String NAILGUN, String DRONE_PLASMA_GUN, String DOMAIN, String grapplingHook) {
        this.ROCKET_LAUNCHER = ROCKET_LAUNCHER;
        GrenadeSwarm = grenadeSwarm;
        AcidSpit = acidSpit;
        this.GAUNTLET = GAUNTLET;
        DireOrb = direOrb;
        WallOfFire = wallOfFire;
        this.SHOTGUN = SHOTGUN;
        BullRush = bullRush;
        ReconDrone = reconDrone;
        PlasmaTrail = plasmaTrail;
        this.RAILGUN = RAILGUN;
        Berserk = berserk;
        UnholyTotem = unholyTotem;
        this.MINING_LASER = MINING_LASER;
        this.LIGHTNING_GUN = LIGHTNING_GUN;
        this.MACHINEGUN = MACHINEGUN;
        Vendetta = vendetta;
        this.LAGBOLT = LAGBOLT;
        this.NAILGUN = NAILGUN;
        this.DRONE_PLASMA_GUN = DRONE_PLASMA_GUN;
        this.DOMAIN = DOMAIN;
        GrapplingHook = grapplingHook;
    }

    @Override
    public String toString() {
        return "TotalDeaths{" +
                "ROCKET_LAUNCHER='" + ROCKET_LAUNCHER + '\'' +
                ", GrenadeSwarm='" + GrenadeSwarm + '\'' +
                ", AcidSpit='" + AcidSpit + '\'' +
                ", GAUNTLET='" + GAUNTLET + '\'' +
                ", DireOrb='" + DireOrb + '\'' +
                ", WallOfFire='" + WallOfFire + '\'' +
                ", SHOTGUN='" + SHOTGUN + '\'' +
                ", BullRush='" + BullRush + '\'' +
                ", ReconDrone='" + ReconDrone + '\'' +
                ", PlasmaTrail='" + PlasmaTrail + '\'' +
                ", RAILGUN='" + RAILGUN + '\'' +
                ", Berserk='" + Berserk + '\'' +
                ", UnholyTotem='" + UnholyTotem + '\'' +
                ", MINING_LASER='" + MINING_LASER + '\'' +
                ", LIGHTNING_GUN='" + LIGHTNING_GUN + '\'' +
                ", MACHINEGUN='" + MACHINEGUN + '\'' +
                ", Vendetta='" + Vendetta + '\'' +
                ", LAGBOLT='" + LAGBOLT + '\'' +
                ", NAILGUN='" + NAILGUN + '\'' +
                ", DRONE_PLASMA_GUN='" + DRONE_PLASMA_GUN + '\'' +
                ", DOMAIN='" + DOMAIN + '\'' +
                ", GrapplingHook='" + GrapplingHook + '\'' +
                '}';
    }
}
