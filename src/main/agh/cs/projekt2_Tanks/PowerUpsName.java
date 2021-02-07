package agh.cs.projekt2_Tanks;

public enum PowerUpsName {
    DoubleMove,
    DoubleSpeedShell,
    ArmourPiercingShell,
    BouncingShell,
    Immortality,
    BonusHealth;

    @Override
    public String toString() {
        switch (this){
            case DoubleMove: return "DM";
            case DoubleSpeedShell: return "DSS";
            case ArmourPiercingShell: return "APS";
            case BouncingShell: return "BS";
            case Immortality: return "I";
            case BonusHealth: return "BH";
            default: return"";
        }
    }
}
