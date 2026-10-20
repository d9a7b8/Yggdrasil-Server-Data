package fr.traodev.yggdrasil.server.data.world;

import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.TagByte;
import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.TagCompound;

import java.util.Map;

public final class WorldSettings {
    private long randomSeed;
    private String generatorName;
    private Difficulty difficulty;
    private double borderCenterX;
    private double borderCenterZ;
    private double borderDamagePerBlock;
    private double borderWarningBlocks;
    private double borderWarningTime;
    private double borderSize;
    private double borderSizeLerpTarget;
    private double borderSafeZone;
    private long borderSizeLerpTime;
    private int caveMinlengthFactor;
    private boolean raining;
    private long time;
    private long dayTime;
    private int gameType;
    private byte mapFeatures;
    private boolean initialized;
    private boolean allowCommands;
    private long sizeOnDisk;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private int rainTime;
    private int thunderTime;
    private boolean hardcore;
    private boolean difficultyLocked;
    private int clearWeatherTime;
    private boolean thundering;
    private int caveFrequency;
    private int generatorVersion;
    private int version;
    private String levelName;
    private String generatorOptions;
    private long lastPlayed;
    private Map<String, TagObject<?>> gameRules;

    public WorldSettings(TagCompound compound) {
        compound = compound.getCompound("").getCompound("Data");

        this.randomSeed = compound.getLong("RandomSeed");
        this.generatorName = compound.getString("generatorName");
        this.difficulty = Difficulty.fromId(compound.getByte("Difficulty"));
        this.borderCenterX = compound.getDouble("BorderCenterX");
        this.borderCenterZ = compound.getDouble("BorderCenterZ");
        this.borderDamagePerBlock = compound.getDouble("BorderDamagePerBlock");
        this.borderWarningBlocks = compound.getDouble("BorderWarningBlocks");
        this.borderWarningTime = compound.getDouble("BorderWarningTime");
        this.borderSize = compound.getDouble("BorderSize");
        this.borderSizeLerpTarget = compound.getDouble("BorderSizeLerpTarget");
        this.borderSafeZone = compound.getDouble("BorderSafeZone");
        this.borderSizeLerpTime = compound.getLong("BorderSizeLerpTime");
        this.caveMinlengthFactor = compound.getInt("CaveMinLengthFactor");
        this.raining = compound.getByte("raining") == 1;
        this.time = compound.getLong("Time");
        this.dayTime = compound.getLong("DayTime");
        this.gameType = compound.getInt("GameType");
        this.mapFeatures = compound.getByte("MapFeatures");
        this.initialized = compound.getByte("initialized") == 1;
        this.allowCommands = compound.getByte("allowCommands") == 1;
        this.sizeOnDisk = compound.getLong("SizeOnDisk");
        this.spawnX = compound.getInt("SpawnX");
        this.spawnY = compound.getInt("SpawnY");
        this.spawnZ = compound.getInt("SpawnZ");
        this.rainTime = compound.getInt("rainTime");
        this.thunderTime = compound.getInt("thunderTime");
        this.hardcore = compound.getByte("hardcore") == 1;
        this.difficultyLocked = compound.getByte("DifficultyLocked") == 1;
        this.clearWeatherTime = compound.getInt("clearWeatherTime");
        this.thundering = compound.getByte("thundering") == 1;
        this.caveFrequency = compound.getInt("CaveFrequency");
        this.generatorVersion = compound.getInt("generatorVersion");
        this.version = compound.getInt("version");
        this.levelName = compound.getString("LevelName");
        this.generatorOptions = compound.getString("generatorOptions");
        this.lastPlayed = compound.getLong("LastPlayed");
        this.gameRules = compound.getCompound("GameRules").get();
    }

    public boolean isDifficultyLocked() {
        return difficultyLocked;
    }

    public void setDifficultyLocked(boolean difficultyLocked) {
        this.difficultyLocked = difficultyLocked;
    }

    public int getClearWeatherTime() {
        return clearWeatherTime;
    }

    public void setClearWeatherTime(int clearWeatherTime) {
        this.clearWeatherTime = clearWeatherTime;
    }

    public boolean isThundering() {
        return thundering;
    }

    public void setThundering(boolean thundering) {
        this.thundering = thundering;
    }

    public int getCaveFrequency() {
        return caveFrequency;
    }

    public void setCaveFrequency(int caveFrequency) {
        this.caveFrequency = caveFrequency;
    }

    public int getGeneratorVersion() {
        return generatorVersion;
    }

    public void setGeneratorVersion(int generatorVersion) {
        this.generatorVersion = generatorVersion;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getGeneratorOptions() {
        return generatorOptions;
    }

    public void setGeneratorOptions(String generatorOptions) {
        this.generatorOptions = generatorOptions;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public Map<String, TagObject<?>> getGameRules() {
        return gameRules;
    }

    public void setGameRules(Map<String, TagObject<?>> gameRules) {
        this.gameRules = gameRules;
    }

    public void setGameRule(final String gameRule, final boolean value) {
        final var tag = new TagByte();
        tag.set((byte) (value ? 1 : 0));

        this.gameRules.put(gameRule, tag);
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public int getThunderTime() {
        return thunderTime;
    }

    public void setThunderTime(int thunderTime) {
        this.thunderTime = thunderTime;
    }

    public int getRainTime() {
        return rainTime;
    }

    public void setRainTime(int rainTime) {
        this.rainTime = rainTime;
    }

    public int getSpawnZ() {
        return spawnZ;
    }

    public void setSpawnZ(int spawnZ) {
        this.spawnZ = spawnZ;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public long getSizeOnDisk() {
        return sizeOnDisk;
    }

    public void setSizeOnDisk(long sizeOnDisk) {
        this.sizeOnDisk = sizeOnDisk;
    }

    public boolean isAllowCommands() {
        return allowCommands;
    }

    public void setAllowCommands(boolean allowCommands) {
        this.allowCommands = allowCommands;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public byte getMapFeatures() {
        return mapFeatures;
    }

    public void setMapFeatures(byte mapFeatures) {
        this.mapFeatures = mapFeatures;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public long getDayTime() {
        return dayTime;
    }

    public void setDayTime(long dayTime) {
        this.dayTime = dayTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public int getCaveMinlengthFactor() {
        return caveMinlengthFactor;
    }

    public void setCaveMinlengthFactor(int caveMinlengthFactor) {
        this.caveMinlengthFactor = caveMinlengthFactor;
    }

    public long getBorderSizeLerpTime() {
        return borderSizeLerpTime;
    }

    public void setBorderSizeLerpTime(long borderSizeLerpTime) {
        this.borderSizeLerpTime = borderSizeLerpTime;
    }

    public double getBorderSafeZone() {
        return borderSafeZone;
    }

    public void setBorderSafeZone(double borderSafeZone) {
        this.borderSafeZone = borderSafeZone;
    }

    public double getBorderSizeLerpTarget() {
        return borderSizeLerpTarget;
    }

    public void setBorderSizeLerpTarget(double borderSizeLerpTarget) {
        this.borderSizeLerpTarget = borderSizeLerpTarget;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(double borderSize) {
        this.borderSize = borderSize;
    }

    public double getBorderWarningTime() {
        return borderWarningTime;
    }

    public void setBorderWarningTime(double borderWarningTime) {
        this.borderWarningTime = borderWarningTime;
    }

    public double getBorderWarningBlocks() {
        return borderWarningBlocks;
    }

    public void setBorderWarningBlocks(double borderWarningBlocks) {
        this.borderWarningBlocks = borderWarningBlocks;
    }

    public double getBorderDamagePerBlock() {
        return borderDamagePerBlock;
    }

    public void setBorderDamagePerBlock(double borderDamagePerBlock) {
        this.borderDamagePerBlock = borderDamagePerBlock;
    }

    public double getBorderCenterZ() {
        return borderCenterZ;
    }

    public void setBorderCenterZ(double borderCenterZ) {
        this.borderCenterZ = borderCenterZ;
    }

    public double getBorderCenterX() {
        return borderCenterX;
    }

    public void setBorderCenterX(double borderCenterX) {
        this.borderCenterX = borderCenterX;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public TagCompound save() {
        final var compound = new TagCompound();
        compound.setLong("RandomSeed", randomSeed);
        compound.setString("generatorName", generatorName);
        compound.setByte("Difficulty", (byte) difficulty.id());
        compound.setDouble("BorderCenterX", borderCenterX);
        compound.setDouble("BorderCenterZ", borderCenterZ);
        compound.setDouble("BorderDamagePerBlock", borderDamagePerBlock);
        compound.setDouble("BorderWarningBlocks", borderWarningBlocks);
        compound.setDouble("BorderWarningTime", borderWarningTime);
        compound.setDouble("BorderSize", borderSize);
        compound.setDouble("BorderSizeLerpTarget", borderSizeLerpTarget);
        compound.setDouble("BorderSafeZone", borderSafeZone);
        compound.setLong("BorderSizeLerpTime", borderSizeLerpTime);
        compound.setInt("CaveMinLengthFactor", caveMinlengthFactor);
        compound.setByte("raining", (byte) (raining ? 1 : 0));
        compound.setLong("Time", time);
        compound.setLong("DayTime", dayTime);
        compound.setInt("GameType", gameType);
        compound.setByte("MapFeatures", mapFeatures);
        compound.setByte("initialized", (byte) (initialized ? 1 : 0));
        compound.setByte("allowCommands", (byte) (allowCommands ? 1 : 0));
        compound.setLong("SizeOnDisk", sizeOnDisk);
        compound.setInt("SpawnX", spawnX);
        compound.setInt("SpawnY", spawnY);
        compound.setInt("SpawnZ", spawnZ);
        compound.setInt("rainTime", rainTime);
        compound.setInt("thunderTime", thunderTime);
        compound.setByte("hardcore", (byte) (hardcore ? 1 : 0));
        compound.setByte("DifficultyLocked", (byte) (difficultyLocked ? 1 : 0));
        compound.setInt("clearWeatherTime", clearWeatherTime);
        compound.setByte("thundering", (byte) (thundering ? 1 : 0));
        compound.setInt("CaveFrequency", caveFrequency);
        compound.setInt("generatorVersion", generatorVersion);
        compound.setInt("version", version);
        compound.setString("LevelName", levelName);
        compound.setString("generatorOptions", generatorOptions);
        compound.setLong("LastPlayed", lastPlayed);

        final var tagGameRules = new TagCompound();
        tagGameRules.set(gameRules);

        compound.setCompound("GameRules", tagGameRules);

        final var dataCompound = new TagCompound();
        dataCompound.setCompound("Data", compound);

        final var voidCompound = new TagCompound();
        voidCompound.setCompound("", dataCompound);

        return voidCompound;
    }

}