package com.i550.qstats.Model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class DataGlobal extends BaseObservable {
    public DataGlobal(){}
    private static final String TAG = "qS";
    private TotalChampionusage total_championusage = new TotalChampionusage();
    private TotalDeaths total_deaths = new TotalDeaths();
    private ChangeChampionusage change_championusage = new ChangeChampionusage();
    private ChangeDeaths change_deaths = new ChangeDeaths();


/*    public void NotifyDataGlobalChanged(){
        Log.i(TAG, "notify Model");
        notifyChange();
    }*/

    @Bindable
    public String getTotal_championusage() {
        String z;
        if (total_championusage !=null){
        z = total_championusage.toString();} else
          z = "loading.."  ;
        return z;
    }
 /*   public TotalChampionusage getTotalChampionusage() {
        return total_championusage;
    }*/


    public String  getTotal_deaths() {
        return total_deaths.toString();
    }

    public String getChange_championusage() {
        return change_championusage.toString();
    }

    public String  getChange_deaths() {
        return change_deaths.toString();
    }

    public void setTotal_championusage(TotalChampionusage total_championusage) {
        this.total_championusage = total_championusage;
    }

    public void setTotal_deaths(TotalDeaths total_deaths) {
        this.total_deaths = total_deaths;
    }

    public void setChange_championusage(ChangeChampionusage change_championusage) {
        this.change_championusage = change_championusage;
    }

    public void setChange_deaths(ChangeDeaths change_deaths) {
        this.change_deaths = change_deaths;
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

        ChangeDeaths() {}

        public ChangeDeaths(String grenadeSwarm, String direOrb, String wallOfFire, String SHOTGUN,
                            String vendetta, String bullRush, String LAGBOLT, String GAUNTLET, String RAILGUN,
                            String berserk, String plasmaTrail, String NAILGUN, String DRONE_PLASMA_GUN, String DOMAIN,
                            String MINING_LASER, String MACHINEGUN, String reconDrone, String acidSpit, String unholyTotem,
                            String LIGHTNING_GUN, String ROCKET_LAUNCHER, String grapplingHook) {
            GrenadeSwarm = grenadeSwarm;
            DireOrb = direOrb;
            WallOfFire = wallOfFire;
            this.SHOTGUN = SHOTGUN;
            Vendetta = vendetta;
            BullRush = bullRush;
            this.LAGBOLT = LAGBOLT;
            this.GAUNTLET = GAUNTLET;
            this.RAILGUN = RAILGUN;
            Berserk = berserk;
            PlasmaTrail = plasmaTrail;
            this.NAILGUN = NAILGUN;
            this.DRONE_PLASMA_GUN = DRONE_PLASMA_GUN;
            this.DOMAIN = DOMAIN;
            this.MINING_LASER = MINING_LASER;
            this.MACHINEGUN = MACHINEGUN;
            ReconDrone = reconDrone;
            AcidSpit = acidSpit;
            UnholyTotem = unholyTotem;
            this.LIGHTNING_GUN = LIGHTNING_GUN;
            this.ROCKET_LAUNCHER = ROCKET_LAUNCHER;
            GrapplingHook = grapplingHook;
        }

        @Override
        public String toString() {
            return "ChangeDeaths{" +
                    "GrenadeSwarm='" + GrenadeSwarm + '\'' +
                    ", DireOrb='" + DireOrb + '\'' +
                    ", WallOfFire='" + WallOfFire + '\'' +
                    ", SHOTGUN='" + SHOTGUN + '\'' +
                    ", Vendetta='" + Vendetta + '\'' +
                    ", BullRush='" + BullRush + '\'' +
                    ", LAGBOLT='" + LAGBOLT + '\'' +
                    ", GAUNTLET='" + GAUNTLET + '\'' +
                    ", RAILGUN='" + RAILGUN + '\'' +
                    ", Berserk='" + Berserk + '\'' +
                    ", PlasmaTrail='" + PlasmaTrail + '\'' +
                    ", NAILGUN='" + NAILGUN + '\'' +
                    ", DRONE_PLASMA_GUN='" + DRONE_PLASMA_GUN + '\'' +
                    ", DOMAIN='" + DOMAIN + '\'' +
                    ", MINING_LASER='" + MINING_LASER + '\'' +
                    ", MACHINEGUN='" + MACHINEGUN + '\'' +
                    ", ReconDrone='" + ReconDrone + '\'' +
                    ", AcidSpit='" + AcidSpit + '\'' +
                    ", UnholyTotem='" + UnholyTotem + '\'' +
                    ", LIGHTNING_GUN='" + LIGHTNING_GUN + '\'' +
                    ", ROCKET_LAUNCHER='" + ROCKET_LAUNCHER + '\'' +
                    ", GrapplingHook='" + GrapplingHook + '\'' +
                    '}';
        }
    }

    class TotalChampionusage {

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

        TotalChampionusage() {
        }

        public TotalChampionusage(String GALENA, String RANGER, String DEATH_KNIGHT, String VISOR, String DOOM_SLAYER, String KEEL, String ANARKI, String SLASH, String NYX, String STROGG, String SORLAG, String SCALEBEARER, String BJ_BLAZKOWICZ, String CLUTCH, String ATHENA) {
            this.GALENA = GALENA;
            this.RANGER = RANGER;
            this.DEATH_KNIGHT = DEATH_KNIGHT;
            this.VISOR = VISOR;
            this.DOOM_SLAYER = DOOM_SLAYER;
            this.KEEL = KEEL;
            this.ANARKI = ANARKI;
            this.SLASH = SLASH;
            this.NYX = NYX;
            this.STROGG = STROGG;
            this.SORLAG = SORLAG;
            this.SCALEBEARER = SCALEBEARER;
            this.BJ_BLAZKOWICZ = BJ_BLAZKOWICZ;
            this.CLUTCH = CLUTCH;
            this.ATHENA = ATHENA;
        }

        @Override
        public String toString() {
            return "TotalChampionusage{" +
                    "GALENA='" + GALENA + '\'' +
                    ", RANGER='" + RANGER + '\'' +
                    ", DEATHKNIGHT='" + DEATH_KNIGHT + '\'' +
                    ", VISOR='" + VISOR + '\'' +
                    ", DOOMSLAYER='" + DOOM_SLAYER + '\'' +
                    ", KEEL='" + KEEL + '\'' +
                    ", ANARKI='" + ANARKI + '\'' +
                    ", SLASH='" + SLASH + '\'' +
                    ", NYX='" + NYX + '\'' +
                    ", STROGG='" + STROGG + '\'' +
                    ", SORLAG='" + SORLAG + '\'' +
                    ", SCALEBEARER='" + SCALEBEARER + '\'' +
                    ", BJBLAZKOWICZ='" + BJ_BLAZKOWICZ + '\'' +
                    ", CLUTCH='" + CLUTCH + '\'' +
                    ", ATHENA='" + ATHENA + '\'' +
                    '}';
        }
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
