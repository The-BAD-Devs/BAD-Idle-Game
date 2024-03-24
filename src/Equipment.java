/*
 * Equipment Class for REALLY (kinda) BAD IDLE GAME (Just Kidding) The Prequel II
 * Adrien Abbey, et al., Mar. 2024
 */

import java.io.Serializable;

/**
 * Implements equipment for the Player Character.
 * NOTE: This needs to become an object that belongs to the Player Character.
 * Currently, save/load functions simply save/load the PlayerCharacter object.
 */
public class Equipment implements Serializable {
    // TODO: Verify that equipment saves and loads properly.

    /* Fields */

    static int weaponLevel;
    static int armourLevel;
    static int hatLevel;
    static String weaponDescription;
    static String armourDescription;
    static String hatDescription;

    /* Constructor */

    /**
     * Creates the Equipment object, which tracks the equipment used by the
     * player. This also initializes the player's equipment for new characters.
     */
    public Equipment() {
        // NOTE: Do NOT set item levels to zero, unless you want to destroy
        // reality by attempting to divide-by-zero. Please don't do this. I
        // happen to live in this reality.
        weaponLevel = 1;
        weaponDescription = "Nothin' but Gumption, and a small twig you found.";
        armourLevel = 1;
        armourDescription = "Your Birthday Suit, which is kinda lacking.";
        hatLevel = 1;
        hatDescription = "Hair, unless you're bald.  Are you bald?";
    }

    /* Methods */

    /**
     * Checks to see if the player managed to find an upgrade for one of their
     * pieces of equipment. This is called every time an enemy is defeated.
     * Only one piece of equipment can be upgraded at a time. The chance of
     * upgrading a piece of equipment depends on the enemy's level and the
     * the current level of existing equipment, with a much higher chance if
     * the equipment's level is lower than the monster's level.
     * 
     * @param MonsterLevel The defeated enemy's level, which is used to
     *                     determine the chance of an equipment upgrade.
     * @param isBoss       Whether the defeated enemy is a boss or not. Bosses have
     *                     much better chances of upgrading a piece of equipment.
     * @return Returns true if a piece of equipment upgraded, false if not.
     */
    public static boolean doUpgrade(int MonsterLevel, boolean isBoss) {
        // TODO: Balance these numbers!
        float weaponUpgradeChance = (float) ((MonsterLevel - weaponLevel) * 0.1);
        float armourUpgradeChance = (float) ((MonsterLevel - armourLevel) * 0.1);
        float hatUpgradeChance = (float) ((MonsterLevel - hatLevel) * 0.1);

        // TODO: Consider including weights for the player's initial stats in 
        // these chances.  For example, a starting character with really high
        // brains has a much higher chance of upgrading their hat, which can go
        // higher than the monster level.  This lets players specialize.
    }
}