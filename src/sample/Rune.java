package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Rune {
    private final SimpleStringProperty set;
    private final SimpleIntegerProperty slot;
    private final SimpleStringProperty stars;
    private final SimpleStringProperty rarity;
    private final SimpleIntegerProperty level;
    private final SimpleStringProperty ancient;
    private final SimpleDoubleProperty effCur;
    private final SimpleDoubleProperty effMax;
    private final SimpleStringProperty mainStat;
    private final SimpleIntegerProperty mainStatVal;
    private final SimpleStringProperty innateStat;
    private final SimpleIntegerProperty innateStatVal;
    private final SimpleStringProperty sub1Stat;
    private final SimpleIntegerProperty sub1StatVal;
    private final SimpleStringProperty sub2Stat;
    private final SimpleIntegerProperty sub2StatVal;
    private final SimpleStringProperty sub3Stat;
    private final SimpleIntegerProperty sub3StatVal;
    private final SimpleStringProperty sub4Stat;
    private final SimpleIntegerProperty sub4StatVal;

    public Rune(String set, Integer slot, String stars, String rarity, Integer level, String ancient, double effCur,
                double effMax, String mainStat, Integer mainStatVal, String innateStat, Integer innateStatVal,
                String sub1Stat, Integer sub1StatVal, String sub2Stat, Integer sub2StatVal, String sub3Stat,
                Integer sub3StatVal, String sub4Stat, Integer sub4StatVal) {
        this.set = new SimpleStringProperty(set);
        this.slot = new SimpleIntegerProperty(slot);
        this.stars = new SimpleStringProperty(stars);
        this.rarity = new SimpleStringProperty(rarity);
        this.level = new SimpleIntegerProperty(level);
        this.ancient = new SimpleStringProperty(ancient);
        this.effCur = new SimpleDoubleProperty(effCur);
        this.effMax = new SimpleDoubleProperty(effMax);
        this.mainStat = new SimpleStringProperty(mainStat);
        this.mainStatVal = new SimpleIntegerProperty(mainStatVal);
        this.innateStat = new SimpleStringProperty(innateStat);
        this.innateStatVal = new SimpleIntegerProperty(innateStatVal);
        this.sub1Stat = new SimpleStringProperty(sub1Stat);
        this.sub1StatVal = new SimpleIntegerProperty(sub1StatVal);
        this.sub2Stat = new SimpleStringProperty(sub2Stat);
        this.sub2StatVal = new SimpleIntegerProperty(sub2StatVal);
        this.sub3Stat = new SimpleStringProperty(sub3Stat);
        this.sub3StatVal = new SimpleIntegerProperty(sub3StatVal);
        this.sub4Stat = new SimpleStringProperty(sub4Stat);
        this.sub4StatVal = new SimpleIntegerProperty(sub4StatVal);
    }

    public String getSet() {

        return set.get();
    }

    public void setSet(String s) {

        set.set(s);
    }

    public Integer getSlot() {

        return slot.get();
    }

    public void setSlot(Integer i) {

        slot.set(i);
    }

    public String getStars() {

        return stars.get();
    }

    public void setStars(String s) {

        stars.set(s);
    }

    public String getRarity() {

        return rarity.get();
    }

    public void setRarity(String s) {

        rarity.set(s);
    }

    public Integer getLevel() {

        return level.get();
    }

    public void setLevel(Integer i) {

        level.set(i);
    }

    public String getAncient() {

        return ancient.get();
    }

    public void setEffCur(double d) {

        effCur.set(d);
    }

    public double getEffCur() {

        return effCur.get();
    }

    public void setEffMax(double d) {

        effMax.set(d);
    }

    public double getEffMax() {

        return effMax.get();
    }

    public void setAncient(String s) {

        ancient.set(s);
    }

    public String getMainStat() {

        return mainStat.get();
    }

    public void setMainStat(String s) {

        mainStat.set(s);
    }

    public Integer getMainStatVal() {

        return mainStatVal.get();
    }

    public void setMainStatVal(Integer i) {

        mainStatVal.set(i);
    }

    public String getInnateStat() {

        return innateStat.get();
    }

    public void setInnateStat(String s) {

        innateStat.set(s);
    }

    public Integer getInnateStatVal() {

        return innateStatVal.get();
    }

    public void setInnateStatVal(Integer i) {

        innateStatVal.set(i);
    }

    public String getSub1Stat() {

        return sub1Stat.get();
    }

    public void setSub1Stat(String s) {

        sub1Stat.set(s);
    }

    public Integer getSub1StatVal() {

        return sub1StatVal.get();
    }

    public void setSub1StatVal(Integer i) {

        sub1StatVal.set(i);
    }

    public String getSub2Stat() {

        return sub2Stat.get();
    }

    public void setSub2Stat(String s) {

        sub2Stat.set(s);
    }

    public Integer getSub2StatVal() {

        return sub2StatVal.get();
    }

    public void setSub2StatVal(Integer i) {

        sub2StatVal.set(i);
    }

    public String getSub3Stat() {

        return sub3Stat.get();
    }

    public void setSub3Stat(String s) {

        sub3Stat.set(s);
    }

    public Integer getSub3StatVal() {

        return sub3StatVal.get();
    }

    public void setSub3StatVal(Integer i) {

        sub3StatVal.set(i);
    }

    public String getSub4Stat() {

        return sub4Stat.get();
    }

    public void setSub4Stat(String s) {

        sub4Stat.set(s);
    }

    public Integer getSub4StatVal() {

        return sub4StatVal.get();
    }

    public void setSub4StatVal(Integer i) {

        sub4StatVal.set(i);
    }


}
