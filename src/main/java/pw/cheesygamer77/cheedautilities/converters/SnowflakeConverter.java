package pw.cheesygamer77.cheedautilities.converters;

import java.util.regex.Pattern;

public abstract class SnowflakeConverter {
    public static final String SNOWFLAKE_REGEX = "([0-9]{15,20})";
    public static final Pattern SNOWFLAKE_PATTERN = Pattern.compile(SNOWFLAKE_REGEX + "$");
}
